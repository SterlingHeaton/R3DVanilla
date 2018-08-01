package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class SleepingEvent implements Listener
{
    private Vanilla plugin;

    public SleepingEvent(Vanilla plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerSleep(PlayerBedEnterEvent event)
    {
        final World WORLD = event.getPlayer().getWorld();

        Player player = event.getPlayer();

        if(WORLD.getEnvironment() != World.Environment.NORMAL)
        {
            return;
        }

        plugin.getConfigSettings().addSleepingPlayer(player);

        if(plugin.getSleepingCooldown())
        {
            int percentage = calculatePercentage();

            if(percentage >= plugin.getConfigSettings().getSleepPercent())
            {
                plugin.getServer().broadcastMessage(Utils.color(player.getName() + " &7&ois now sleeping. &8[&a" + percentage + "%&7/&a" + plugin.getConfigSettings().getSleepPercent() + "%&8]"));

                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                {
                    setDay(WORLD);
                }, 20 * 5);
            }
            else
            {
                plugin.getServer().broadcastMessage(Utils.color(player.getName() + " &7&ois now sleeping. &8[&c" + percentage + "%&7/&a" + plugin.getConfigSettings().getSleepPercent() + "%&8]"));

            }
            return;
        }

        plugin.getServer().spigot().broadcast(buildInteractiveMessage(player.getName()));

        plugin.setSleeping(plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
        {
            setDay(WORLD);
        }, 20 * 10));
    }

    @EventHandler
    private void onPlayerExitBed(PlayerBedLeaveEvent event)
    {
        Player player = event.getPlayer();

        plugin.getConfigSettings().getSleepingPlayers().remove(player);
    }

    private void setDay(World world)
    {
        world.setFullTime(world.getFullTime() + (24000 - world.getTime()));

        if(world.isThundering())
        {
            world.setThundering(false);
        }

        if(world.hasStorm())
        {
            world.setStorm(false);
        }
    }

    private int calculatePercentage()
    {
        int total = plugin.getServer().getOnlinePlayers().size() - plugin.getConfigSettings().getAfkPlayers().size();
        int playersSleeping = plugin.getConfigSettings().getSleepingPlayers().size();

        return (playersSleeping * 100) / total;
    }

    private TextComponent buildInteractiveMessage(String playerName)
    {
        TextComponent message = new TextComponent(Utils.color(playerName + " &7&ois now sleeping. " + "&c[CANCEL]"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click me to keep it night time and enable majority vote.").color(ChatColor.RED).create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cancelsleep"));

        return message;
    }
}

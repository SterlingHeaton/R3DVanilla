package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import static org.bukkit.ChatColor.*;

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
        World world = event.getPlayer().getWorld();

        if(world.getEnvironment() != World.Environment.NORMAL)
        {
            return;
        }

        plugin.getConfigSettings().addSleepingPlayer(event.getPlayer());

        for(Player player : plugin.getServer().getOnlinePlayers())
        {
            player.spigot().sendMessage(buildInteractiveMessage(event.getPlayer().getName()));
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
           public void run()
           {
               if(world.isThundering())
               {
                   world.setStorm(false);
               }

               if(world.hasStorm())
               {
                   world.setStorm(false);
               }

               world.setFullTime(world.getFullTime() + (24000 - world.getTime()));
           }
        }, 20 * 7);
    }

    @EventHandler
    private void onPlayerExitBed(PlayerBedLeaveEvent event)
    {
        World world = event.getPlayer().getWorld();

        if(world.getEnvironment() != World.Environment.NORMAL)
        {
            return;
        }

        plugin.getConfigSettings().getSleepingPlayers().remove(event.getPlayer());

        if(plugin.getConfigSettings().getSleepingPlayers().isEmpty())
        {
            Bukkit.getScheduler().cancelAllTasks();
        }
    }

    private TextComponent buildInteractiveMessage(String playerName)
    {
        TextComponent message = new TextComponent(Utils.color(playerName + " &7&ois now sleeping. " + "&c[CANCEL]"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click me to keep the kingdom from sleeping.").color(ChatColor.RED).create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cancelsleep"));

        return message;
    }
}

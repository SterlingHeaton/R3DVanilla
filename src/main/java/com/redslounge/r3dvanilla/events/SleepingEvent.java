package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.DataManager;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.models.RedPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class SleepingEvent implements Listener
{
    private Plugin plugin;

    public SleepingEvent(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerSleep(PlayerBedEnterEvent event)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(event.getPlayer().getUniqueId());

        if(!event.getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL))
        {
            return;
        }

        if(!event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK))
        {
            return;
        }

        dataManager.getSleepingPlayers().add(event.getPlayer());

        if(dataManager.isSleepVote())
        {
            int validPlayers = (plugin.getServer().getOnlinePlayers().size() - dataManager.getAfkPlayers().size());
            double percentage = (double) dataManager.getSleepPercentage() / 100;
            int neededPlayers = (int) Math.round(validPlayers * percentage);

            if(dataManager.getSleepingPlayers().size() >= neededPlayers)
            {
                plugin.getServer().broadcastMessage(Utils.color(Utils.getTeamColor(event.getPlayer()) + event.getPlayer().getName() + " &7&ois now sleeping&6. &8[&a" + dataManager.getSleepingPlayers().size() + "&7/&a" + neededPlayers + "&8]"));
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> setDay(event.getPlayer().getWorld()), 20 * 7);
            }
            else
            {
                plugin.getServer().broadcastMessage(Utils.color(Utils.getTeamColor(event.getPlayer()) + event.getPlayer().getName() + " &7&ois now sleeping&6. &8[&c" + dataManager.getSleepingPlayers().size() + "&7/&a" + neededPlayers + "&8]"));
            }
        }
        else
        {
            plugin.getServer().spigot().broadcast(buildInteractiveMessage(event.getPlayer()));

            if(!plugin.getServer().getScheduler().isQueued(dataManager.getSleepingID()))
            {
                dataManager.setSleepingID(plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> setDay(event.getPlayer().getWorld()), 20 * 7));
            }
        }
    }

    @EventHandler
    private void onPlayerExitBed(PlayerBedLeaveEvent event)
    {
        DataManager dataManager = DataManager.getInstance();

        dataManager.getSleepingPlayers().remove(event.getPlayer());

        if(dataManager.getSleepingPlayers().isEmpty())
        {
            plugin.getServer().getScheduler().cancelTask(dataManager.getSleepingID());
        }
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

    private TextComponent buildInteractiveMessage(Player player)
    {
        TextComponent message = new TextComponent(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&ois now sleeping. " + "&c[CLICK TO CANCEL]"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cancelsleep"));

        return message;
    }
}

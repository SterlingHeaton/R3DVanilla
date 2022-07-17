package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
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
    private final Plugin plugin;
    private final String tag = "&8[&6Sleep&8]";

    public SleepingEvent(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerSleep(PlayerBedEnterEvent event)
    {
        DataManager dataManager = DataManager.getInstance();

        if(!event.getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL))
        {
            return;
        }

        if(!event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK))
        {
            return;
        }

        dataManager.getSleepingPlayers().add(event.getPlayer());

        if(dataManager.isSleepVote() && dataManager.getSleepCanceler().equals(event.getPlayer().getUniqueId()))
        {
            dataManager.setSleepVote(false);
            Utils.broadcastMessage(Utils.color(tag + " &aVote canceled. &7&oOriginal canceler is now sleeping."));
            dataManager.setSleepCanceler(null);
        }

        if(dataManager.isSleepVote())
        {
            int validPlayers = (plugin.getServer().getOnlinePlayers().size() - dataManager.getAfkPlayers().size());
            double percentage = (double) dataManager.getSleepPercentage() / 100;
            int neededPlayers = (int) Math.round(validPlayers * percentage);

            if(dataManager.getSleepingPlayers().size() >= neededPlayers)
            {
                plugin.getServer().broadcastMessage(Utils.color(Utils.getChatColor(event.getPlayer().getUniqueId()) + event.getPlayer().getName() + " &7&ois now sleeping&6. &8[&a" + dataManager.getSleepingPlayers().size() + "&7/&a" + neededPlayers + "&8]"));
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> setDay(event.getPlayer().getWorld()), 20 * 7);
            }
            else
            {
                plugin.getServer().broadcastMessage(Utils.color(Utils.getChatColor(event.getPlayer().getUniqueId()) + event.getPlayer().getName() + " &7&ois now sleeping&6. &8[&c" + dataManager.getSleepingPlayers().size() + "&7/&a" + neededPlayers + "&8]"));
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
        TextComponent message = new TextComponent(Utils.color(Utils.getChatColor(player.getUniqueId()) + player.getName() + " &7&ois now sleeping. " + "&c[CLICK TO CANCEL]"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cancelsleep"));

        return message;
    }
}

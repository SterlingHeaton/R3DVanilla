package com.redslounge.r3dvanilla.managers;

import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * This class is used for maintaining and detecting afk states.
 *
 * @author Sterling (@sterlingheaton)
 */
public class AfkManager
{
    // Global variable to access bukkit runnable
    private final Plugin plugin;


    /**
     * Main constructor for this class that starts a repeating task that goes off every 1 second.
     *
     * @param plugin Input for the main plugin instance
     */
    public AfkManager(Plugin plugin)
    {
        this.plugin = plugin;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::pulseTasks, 20, 20);
    }

    /**
     * This method is called every 1 second to manage afk players state.
     */
    public void pulseTasks()
    {
        // Main loop to go through all online players
        for(Player player : plugin.getServer().getOnlinePlayers())
        {
            DataManager dataManager = DataManager.getInstance();
            RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

            // If any player is already afk or in the process of going afk, skip them
            if(redPlayer.isAfk())
            {
                continue;
            }

            if(plugin.getServer().getScheduler().isCurrentlyRunning(redPlayer.getAfkId()))
            {
                continue;
            }

            if(plugin.getServer().getScheduler().isQueued(redPlayer.getAfkId()))
            {
                continue;
            }

            // As soon as the player is detected as not moving or interacting with the game, schedule this task to go off in the future.
            redPlayer.setAfkId(plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
            {
                setAfk(player, redPlayer, plugin.getServer().getScheduler());
                dataManager.getAfkPlayers().add(player);
            }, (20L * dataManager.getAfkTimer())));
        }
    }

    /**
     * This static method is used by this class, AfkEvents, and AfkCommand to centralize how players go afk.
     *
     * @param player        Input for the player object to set the scoreboard
     * @param redPlayer     Input for the redPlayer object that stores various afk variables
     * @param scheduledTask Input for the scheduled task
     */
    public static void setAfk(Player player, RedPlayer redPlayer, BukkitScheduler scheduledTask)
    {
        Utils.broadcastMessage(Utils.getTeamColor(player) + player.getName() + " &7&ois now AFK!");
        player.setPlayerListName(Utils.color("&7" + player.getName() + " (afk)"));
        redPlayer.setAfk(true);
        scheduledTask.cancelTask(redPlayer.getAfkId());
        redPlayer.setAfkId(-1);
    }

    /**
     * This static method is used by this class, AfkEvents, and AfkCommand to centralize how players come back from being afk.
     *
     * @param player        Input for the player object to set the scoreboard
     * @param redPlayer     Input for the redPlayer object that stores various afk variables
     * @param scheduledTask Input for the scheduled task
     */
    public static void setUnafk(Player player, RedPlayer redPlayer, BukkitScheduler scheduledTask)
    {
        Utils.broadcastMessage(Utils.getTeamColor(player) + player.getName() + " &7&ois back from being AFK!");
        player.setPlayerListName(player.getName());
        redPlayer.setAfk(false);
        scheduledTask.cancelTask(redPlayer.getAfkId());
        redPlayer.setAfkId(-1);
    }
}

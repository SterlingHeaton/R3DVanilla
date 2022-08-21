package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.managers.AfkManager;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This class defines are adds functionality to event listeners used for afk.
 */
public class AfkEvents implements Listener
{
    // Global variable to access bukkit runnable
    private final Plugin plugin;

    public AfkEvents(Plugin plugin)
    {
        this.plugin = plugin;
    }

    /**
     * This event is used to track when a player moves.
     *
     * @param event Automatic input from the event listener
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        // Variables to access player data.
        Player player = event.getPlayer();
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // If the player is ghost afk, then ignore the rest of this method.
        if(redPlayer.isGhostAfk())
        {
            return;
        }

        // If the player is afk or the task is running, then set them as unafk and ignore the rest of this method.
        if(redPlayer.isAfk() || plugin.getServer().getScheduler().isCurrentlyRunning(redPlayer.getAfkId()))
        {
            AfkManager.setUnafk(player, redPlayer, plugin.getServer().getScheduler());
            dataManager.getAfkPlayers().remove(player);
            return;
        }

        // If the player is not afk currently, but their task is queued to go off, then cancel the task.
        if(plugin.getServer().getScheduler().isQueued(redPlayer.getAfkId()))
        {
            plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
            redPlayer.setAfkId(-1);
        }
    }

    /**
     * This event is used to track when the player leaves the game.
     *
     * @param event Automatic input from the event listener
     */
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        // Variables to access player data.
        Player player = event.getPlayer();
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Whether the player is or isn't afk (including ghost), will mark them as unafk but wont notify the server.
        player.setPlayerListName(player.getName());
        redPlayer.setAfk(false);
        redPlayer.setGhostAfk(false);
        plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
        redPlayer.setAfkId(-1);
        dataManager.getAfkPlayers().remove(player);
    }
}

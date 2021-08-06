package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AfkEvents implements Listener
{
    private final Plugin plugin;

    public AfkEvents(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.isAfk() || plugin.getServer().getScheduler().isCurrentlyRunning(redPlayer.getAfkId()))
        {
            Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&ois back from being AFK"));
            player.setPlayerListName(player.getName());
            redPlayer.setAfk(false);
            dataManager.getAfkPlayers().remove(player);
        }

        if(plugin.getServer().getScheduler().isQueued(redPlayer.getAfkId()))
        {
            plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
            redPlayer.setAfkId(-1);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.isAfk() || plugin.getServer().getScheduler().isCurrentlyRunning(redPlayer.getAfkId()))
        {
            Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&ois back from being AFK"));
            player.setPlayerListName(player.getName());
            redPlayer.setAfk(false);
            dataManager.getAfkPlayers().remove(player);
        }

        if(plugin.getServer().getScheduler().isQueued(redPlayer.getAfkId()))
        {
            plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
            redPlayer.setAfkId(-1);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.isAfk() || plugin.getServer().getScheduler().isCurrentlyRunning(redPlayer.getAfkId()))
        {
            player.setPlayerListName(player.getName());
            redPlayer.setAfk(false);
            dataManager.getAfkPlayers().remove(player);
        }

        if(plugin.getServer().getScheduler().isQueued(redPlayer.getAfkId()))
        {
            plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
            redPlayer.setAfkId(-1);
        }
    }
}

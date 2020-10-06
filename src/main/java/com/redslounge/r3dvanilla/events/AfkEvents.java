package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.DataManager;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AfkEvents implements Listener
{
    private Plugin plugin;

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
            Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + " &7&ois no longer AFK"));
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
            Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + " &7&ois no longer AFK"));
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

//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event)
//    {
//        Player player = event.getPlayer();
//        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());
//
//        handlePlyaer(player, playerInfo, true);
//    }
//
//    @EventHandler
//    public void onPlayerChat(AsyncPlayerChatEvent event)
//    {
//        Player player = event.getPlayer();
//        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());
//
//        handlePlyaer(player, playerInfo, true);
//    }
//
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event)
//    {
//        Player player = event.getPlayer();
//        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());
//
//        if(player.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD)
//        {
//            return;
//        }
//
//        handlePlyaer(player, playerInfo, true);
//    }
//
//    @EventHandler
//    public void onPlayerLeave(PlayerQuitEvent event)
//    {
//        Player player = event.getPlayer();
//        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());
//
//        handlePlyaer(player, playerInfo, false);
//    }
//
//    public void handlePlyaer(Player player, RedPlayer playerInfo, boolean leaveMessage)
//    {
//        boolean queued = plugin.getServer().getScheduler().isQueued(playerInfo.getAfkId());
//        boolean running = plugin.getServer().getScheduler().isCurrentlyRunning(playerInfo.getAfkId());
//        boolean isAfk = playerInfo.isAfk();
//
//        if(isAfk || running)
//        {
//            plugin.getAfkTasks().setPlayerUnafk(player, playerInfo, leaveMessage);
//        }
//
//        if(queued)
//        {
//            plugin.getServer().getScheduler().cancelTask(playerInfo.getAfkId());
//            playerInfo.setAfkId(-1);
//        }
//    }
}

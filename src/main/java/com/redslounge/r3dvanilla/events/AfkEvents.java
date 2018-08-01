package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class AfkEvents implements Listener
{
    private Vanilla plugin;

    public AfkEvents(Vanilla pl)
    {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        handlePlyaer(player, playerInfo, true);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        handlePlyaer(player, playerInfo, true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        handlePlyaer(player, playerInfo, true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        handlePlyaer(player, playerInfo, false);
    }

    public void handlePlyaer(Player player, RedPlayer playerInfo, boolean leavueMessage)
    {
        if(playerInfo.getAfkId() == -1)
        {
            return;
        }

        if(plugin.getServer().getScheduler().isQueued(playerInfo.getAfkId()))
        {
            plugin.getServer().getScheduler().cancelTask(playerInfo.getAfkId());
            playerInfo.setAfkId(-1);
            return;
        }

        if(plugin.getServer().getScheduler().isCurrentlyRunning(playerInfo.getAfkId()))
        {
            plugin.getAfkTasks().setPlayerUnafk(player, playerInfo, leavueMessage);
            return;
        }

        if(playerInfo.isAfk())
        {
            plugin.getAfkTasks().setPlayerUnafk(player, playerInfo, leavueMessage);
            return;
        }
    }
}

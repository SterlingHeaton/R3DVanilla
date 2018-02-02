package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(playerID);

        if(playerInformation.isAfk())
        {
            plugin.getConfigSettings().getPlayer(playerID).setAfk(false);
            Bukkit.broadcastMessage(Utils.color(player.getName() + " &7&ois no longer AFK"));
            player.setPlayerListName(player.getName());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(playerID);

        if(playerInformation.isAfk())
        {
            plugin.getConfigSettings().getPlayer(playerID).setAfk(false);
        }
    }
}

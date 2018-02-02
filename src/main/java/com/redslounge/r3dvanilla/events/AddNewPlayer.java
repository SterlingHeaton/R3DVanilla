package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedConfig;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddNewPlayer implements Listener
{
    private Vanilla plugin;

    public AddNewPlayer(Vanilla pl)
    {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        UUID playerID = event.getPlayer().getUniqueId();

        if(plugin.getConfigSettings().getPlayer(playerID) != null)
        {
            return;
        }

        RedPlayer player = new RedPlayer(playerID, event.getPlayer().getName(), plugin.getConfigSettings().isMessagePing(), new ArrayList<String>());

        plugin.getConfigSettings().addPlayer(playerID, player);
    }
}

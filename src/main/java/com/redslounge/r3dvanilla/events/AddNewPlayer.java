package com.redslounge.r3dvanilla.events;

import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

public class AddNewPlayer implements Listener
{
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        DataManager dataManager = DataManager.getInstance();

        if(dataManager.getPlayers().containsKey(event.getPlayer().getUniqueId()))
        {
            return;
        }

        RedPlayer redPlayer = new RedPlayer(dataManager.isDefaultMessagePing(), new ArrayList<>());
        dataManager.getPlayers().put(event.getPlayer().getUniqueId(), redPlayer);
    }
}

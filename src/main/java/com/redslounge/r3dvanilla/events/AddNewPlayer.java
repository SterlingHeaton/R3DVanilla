package com.redslounge.r3dvanilla.events;

import co.aikar.idb.DB;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.SQLException;
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

        RedPlayer redPlayer = new RedPlayer(event.getPlayer().getUniqueId(),
                dataManager.isDefaultMessagePing(),
                dataManager.getDefaultMessageSound(),
                dataManager.getDefaultMessageSoundPitch(),
                new ArrayList<>());
        dataManager.getPlayers().put(event.getPlayer().getUniqueId(), redPlayer);

        try
        {
            DB.executeInsert("INSERT INTO players (playerID, playerName, messagePing, messageSound, messageSoundPitch) VALUES (?, ?, ?, ?, ?)",
                    redPlayer.getPlayerUUID().toString(),
                    event.getPlayer().getName(),
                    redPlayer.hasMessagePing(),
                    redPlayer.getMessageSound().name(),
                    redPlayer.getMessageSoundPitch());
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
}
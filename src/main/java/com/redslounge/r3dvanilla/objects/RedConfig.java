package com.redslounge.r3dvanilla.objects;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class RedConfig
{
    private int noteLimit;
    private boolean messagePing;
    private HashMap<UUID, RedPlayer> players = new HashMap<UUID, RedPlayer>();
    private ArrayList<Player> sleepingPlayers = new ArrayList<Player>();

    public RedConfig(int noteLimit, boolean messagePing)
    {
        this.noteLimit = noteLimit;
        this.messagePing = messagePing;
    }

    public int getNoteLimit()
    {
        return noteLimit;
    }

    public boolean isMessagePing()
    {
        return messagePing;
    }

    public Set<UUID> getPlayers()
    {
        return players.keySet();
    }

    public RedPlayer getPlayer(UUID playerID)
    {
        return players.get(playerID);
    }

    public void addPlayer(UUID playerID, RedPlayer player)
    {
        players.put(playerID, player);
    }

    public ArrayList<Player> getSleepingPlayers()
    {
        return sleepingPlayers;
    }

    public void addSleepingPlayer(Player player)
    {
        sleepingPlayers.add(player);
    }
}

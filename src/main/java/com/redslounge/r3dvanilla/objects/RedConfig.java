package com.redslounge.r3dvanilla.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class RedConfig
{
    private int noteLimit;
    private boolean messagePing;
    private HashMap<UUID, RedPlayer> players = new HashMap<UUID, RedPlayer>();

    public RedConfig(int noteLimit, boolean messagePing)
    {
        this.noteLimit = noteLimit;
        this.messagePing = messagePing;
    }

    public void setNoteLimit(int noteLimit)
    {
        this.noteLimit = noteLimit;
    }

    public int getNoteLimit()
    {
        return noteLimit;
    }

    public void setMessagePing(boolean messagePing)
    {
        this.messagePing = messagePing;
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
}

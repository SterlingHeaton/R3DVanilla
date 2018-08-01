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
    private int sleepCooldown;
    private int sleepPercent;
    private int afkTimer;
    private HashMap<UUID, RedPlayer> players = new HashMap<>();
    private ArrayList<Player> sleepingPlayers = new ArrayList<>();
    private ArrayList<Player> afkPlayers = new ArrayList<>();

    public RedConfig(int noteLimit, boolean messagePing, int sleepCooldown, int sleepPercent, int afkTimer)
    {
        this.noteLimit = noteLimit;
        this.messagePing = messagePing;
        this.sleepCooldown = sleepCooldown;
        this.sleepPercent = sleepPercent;
        this.afkTimer = afkTimer;
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

    public void addAfkPlayer(Player player)
    {
        afkPlayers.add(player);
    }

    public ArrayList<Player> getAfkPlayers()
    {
        return afkPlayers;
    }

    public int getSleepCooldown()
    {
        return sleepCooldown;
    }

    public int getSleepPercent()
    {
        return sleepPercent;
    }

    public int getAfkTimer()
    {
        return afkTimer;
    }

}

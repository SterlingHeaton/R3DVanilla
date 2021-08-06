package com.redslounge.r3dvanilla.managers;

import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DataManager
{
    private static DataManager dataManager;
    private DataManager() {}
    public static DataManager getInstance()
    {
        if(dataManager == null)
        {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    private final HashMap<UUID, RedPlayer> players = new HashMap<>();
    private final ArrayList<Player> afkPlayers = new ArrayList<>();
    private final ArrayList<Player> sleepingPlayers = new ArrayList<>();
    private int afkTimer;
    private int noteLimit;
    private int sleepCooldown;
    private int sleepingID;
    private int sleepPercentage;
    private boolean defaultMessagePing;
    private boolean sleepVote;

    public HashMap<UUID, RedPlayer> getPlayers()
    {
        return players;
    }

    public ArrayList<Player> getAfkPlayers()
    {
        return afkPlayers;
    }

    public ArrayList<Player> getSleepingPlayers()
    {
        return sleepingPlayers;
    }

    public int getAfkTimer()
    {
        return afkTimer;
    }

    public void setAfkTimer(int afkTimer)
    {
        this.afkTimer = afkTimer;
    }

    public int getNoteLimit()
    {
        return noteLimit;
    }

    public void setNoteLimit(int noteLimit)
    {
        this.noteLimit = noteLimit;
    }

    public int getSleepCooldown()
    {
        return sleepCooldown;
    }

    public void setSleepCooldown(int sleepCooldown)
    {
        this.sleepCooldown = sleepCooldown;
    }

    public int getSleepingID()
    {
        return sleepingID;
    }

    public void setSleepingID(int sleepingID)
    {
        this.sleepingID = sleepingID;
    }

    public int getSleepPercentage()
    {
        return sleepPercentage;
    }

    public void setSleepPercentage(int sleepPercentage)
    {
        this.sleepPercentage = sleepPercentage;
    }

    public boolean isDefaultMessagePing()
    {
        return defaultMessagePing;
    }

    public void setDefaultMessagePing(boolean defaultMessagePing)
    {
        this.defaultMessagePing = defaultMessagePing;
    }

    public boolean isSleepVote()
    {
        return sleepVote;
    }

    public void setSleepVote(boolean sleepVote)
    {
        this.sleepVote = sleepVote;
    }
}

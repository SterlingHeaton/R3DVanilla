package com.redslounge.r3dvanilla;

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

    private int noteLimit;
    private HashMap<UUID, RedPlayer> players = new HashMap<>();
    private int afkTimer;
    private ArrayList<Player> afkPlayers = new ArrayList<>();
    private ArrayList<Player> sleepingPlayers = new ArrayList<>();
    private int sleepingID;
    private boolean sleepVote;
    private int sleepPercentage;
    private int sleepCooldown;
    private boolean defaultMessagePing;

    public ArrayList<Player> getAfkPlayers()
    {
        return afkPlayers;
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

    public HashMap<UUID, RedPlayer> getPlayers()
    {
        return players;
    }

    public void setPlayers(HashMap<UUID, RedPlayer> players)
    {
        this.players = players;
    }

    public ArrayList<Player> getSleepingPlayers()
    {
        return sleepingPlayers;
    }

    public void setSleepingPlayers(ArrayList<Player> sleepingPlayers)
    {
        this.sleepingPlayers = sleepingPlayers;
    }

    public int getSleepingID()
    {
        return sleepingID;
    }

    public void setSleepingID(int sleepingID)
    {
        this.sleepingID = sleepingID;
    }

    public boolean isSleepVote()
    {
        return sleepVote;
    }

    public void setSleepVote(boolean sleepVote)
    {
        this.sleepVote = sleepVote;
    }

    public int getSleepPercentage()
    {
        return sleepPercentage;
    }

    public void setSleepPercentage(int sleepPercentage)
    {
        this.sleepPercentage = sleepPercentage;
    }

    public int getSleepCooldown()
    {
        return sleepCooldown;
    }

    public void setSleepCooldown(int sleepCooldown)
    {
        this.sleepCooldown = sleepCooldown;
    }

    public boolean isDefaultMessagePing()
    {
        return defaultMessagePing;
    }

    public void setDefaultMessagePing(boolean defaultMessagePing)
    {
        this.defaultMessagePing = defaultMessagePing;
    }
}

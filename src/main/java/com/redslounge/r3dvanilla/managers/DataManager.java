package com.redslounge.r3dvanilla.managers;

import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Sound;
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

    private String messageTag;
    private String replyTag;
    private String messagePingTag;
    private Sound defaultMessageSound;
    private float defaultMessageSoundPitch;

    private String portalTag;

    public String getPortalTag()
    {
        return portalTag;
    }

    public void setPortalTag(String portalTag)
    {
        this.portalTag = portalTag;
    }

    public void setMessageTag(String messageTag)
    {
        this.messageTag = messageTag;
    }

    public void setReplyTag(String replyTag)
    {
        this.replyTag = replyTag;
    }

    public void setMessagePingTag(String messagePingTag)
    {
        this.messagePingTag = messagePingTag;
    }

    public Sound getDefaultMessageSound()
    {
        return defaultMessageSound;
    }

    public void setDefaultMessageSound(Sound defaultMessageSound)
    {
        this.defaultMessageSound = defaultMessageSound;
    }

    public float getDefaultMessageSoundPitch()
    {
        return defaultMessageSoundPitch;
    }

    public void setDefaultMessageSoundPitch(float defaultMessageSoundPitch)
    {
        this.defaultMessageSoundPitch = defaultMessageSoundPitch;
    }

    public String getReplyTag()
    {
        return replyTag;
    }

    public String getMessagePingTag()
    {
        return messagePingTag;
    }

    public String getMessageTag()
    {
        return messageTag;
    }

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

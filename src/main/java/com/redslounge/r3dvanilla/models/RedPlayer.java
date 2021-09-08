package com.redslounge.r3dvanilla.models;

import org.bukkit.Sound;

import java.util.List;
import java.util.UUID;

public class RedPlayer
{
    private final List<String> notes;
    private int afkId;
    private boolean afk;
    private boolean messagePing;

    private RedPlayer replyTo;
    private Sound messageSound;
    private float messageSoundPitch;
    private final UUID playerUUID;

    private boolean ghostAfk;

    public RedPlayer(UUID playerUUID, boolean messagePing, Sound messageSound, float messageSoundPitch, List<String> notes)
    {
        this.playerUUID = playerUUID;
        this.messagePing = messagePing;
        this.messageSound = messageSound;
        this.messageSoundPitch = messageSoundPitch;
        this.notes = notes;
        afk = false;
    }

    public boolean isGhostAfk()
    {
        return ghostAfk;
    }

    public void setGhostAfk(boolean ghostAfk)
    {
        this.ghostAfk = ghostAfk;
    }

    public UUID getPlayerUUID()
    {
        return playerUUID;
    }

    public Sound getMessageSound()
    {
        return messageSound;
    }

    public void setMessageSound(Sound messageSound)
    {
        this.messageSound = messageSound;
    }

    public float getMessageSoundPitch()
    {
        return messageSoundPitch;
    }

    public void setMessageSoundPitch(float messageSoundPitch)
    {
        this.messageSoundPitch = messageSoundPitch;
    }

    public RedPlayer getReplyTo()
    {
        return replyTo;
    }

    public void setReplyTo(RedPlayer replyTo)
    {
        this.replyTo = replyTo;
    }

    public List<String> getNotes()
    {
        return notes;
    }

    public int getAfkId()
    {
        return afkId;
    }

    public void setAfkId(int afkId)
    {
        this.afkId = afkId;
    }

    public boolean isAfk()
    {
        return afk;
    }

    public void setAfk(boolean afk)
    {
        this.afk = afk;
    }

    public boolean hasMessagePing()
    {
        return messagePing;
    }

    public void setMessagePing(boolean messagePing)
    {
        this.messagePing = messagePing;
    }
}

package com.redslounge.r3dvanilla.models;

import java.util.List;
import java.util.UUID;

public class RedPlayer
{
    private final List<String> notes;
    private UUID uuidLastMessage;
    private int afkId;
    private boolean afk;
    private boolean messagePing;

    public RedPlayer(boolean messagePing, List<String> notes)
    {
        this.messagePing = messagePing;
        this.notes = notes;
        afk = false;
    }

    public List<String> getNotes()
    {
        return notes;
    }

    public UUID getUuidLastMessage()
    {
        return uuidLastMessage;
    }

    public void setUuidLastMessage(UUID uuidLastMessage)
    {
        this.uuidLastMessage = uuidLastMessage;
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

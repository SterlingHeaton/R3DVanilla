package com.redslounge.r3dvanilla.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RedPlayer
{
    private boolean afk;
    private boolean messagePing;
    private UUID uuidLastMessage;
    private List<String> notes;

    public RedPlayer(boolean messagePing, List<String> notes)
    {
        this.messagePing = messagePing;
        this.notes = notes;
    }

    public boolean isAfk()
    {
        return afk;
    }

    public void setAfk(boolean afk)
    {
        this.afk = afk;
    }

    public boolean isMessagePing()
    {
        return messagePing;
    }

    public void setMessagePing(boolean messagePing)
    {
        this.messagePing = messagePing;
    }

    public UUID getUuidLastMessage()
    {
        return uuidLastMessage;
    }

    public void setUuidLastMessage(UUID uuidLastMessage)
    {
        this.uuidLastMessage = uuidLastMessage;
    }

    public List<String> getNotes()
    {
        return notes;
    }

    public void addNote(String note)
    {
        notes.add(note);
    }
}

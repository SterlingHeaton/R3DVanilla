package com.redslounge.r3dvanilla.models;

import java.util.UUID;

public class Note
{
    long noteID;
    UUID playerID;
    String note;

    public Note(long noteID, UUID playerID, String note)
    {
        this.noteID = noteID;
        this.playerID = playerID;
        this.note = note;
    }

    public long getNoteID()
    {
        return noteID;
    }

    public UUID getPlayerID()
    {
        return playerID;
    }

    public String getNote()
    {
        return note;
    }
}

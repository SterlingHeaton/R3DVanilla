package com.redslounge.r3dvanilla;

import com.redslounge.r3dvanilla.models.RedPlayer;

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
}

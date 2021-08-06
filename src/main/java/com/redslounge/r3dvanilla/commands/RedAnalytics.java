package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@CommandAlias("redanalytics")
public class RedAnalytics extends BaseCommand
{
    @Default
    public void onCommand(Player player)
    {
        if(!player.getName().equals("Redstonehax"))
        {
            player.sendMessage(Utils.color("&cGo away."));
            return;
        }

        DataManager dataManager = DataManager.getInstance();

        player.sendMessage("");
        player.sendMessage("SLEEPING: " + getNames(dataManager.getSleepingPlayers()));
        player.sendMessage("AFK: " + getNames(dataManager.getAfkPlayers()));
    }

    private ArrayList<String> getNames(ArrayList<Player> players)
    {
        ArrayList<String> names = new ArrayList<>();

        for(Player player : players)
        {
            names.add(player.getName());
        }

        return names;
    }
}
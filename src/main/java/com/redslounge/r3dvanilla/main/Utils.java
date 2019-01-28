package com.redslounge.r3dvanilla.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils
{
    public static String inGame = "&cThis command is for in-game use&6.";
    public static String syntax = "&cIncorrect Syntax&6: &7&o";

    public static String color(String color)
    {
        return ChatColor.translateAlternateColorCodes('&', color);
    }

    public static void bugTest(String message)
    {
        Bukkit.broadcastMessage(Utils.color("&8[&4BugTest&8]" + "&7 " + message));
    }

    public static void bugTest(int message)
    {
        Bukkit.broadcastMessage(Utils.color("&8[&4BugTest&8]" + "&7 " + message));
    }

    public static String buildMessage(String[] parts, int start)
    {
        StringBuilder note = new StringBuilder();
        for(int count = start; count < parts.length; count++)
        {
            note.append(parts[count] + " ");
        }
        return note.toString();
    }

    public static ChatColor getTeamColor(Player player)
    {
        try
        {
            return player.getScoreboard().getEntryTeam(player.getName()).getColor();
        }
        catch(NullPointerException e)
        {
            return ChatColor.GREEN;
        }
    }
}

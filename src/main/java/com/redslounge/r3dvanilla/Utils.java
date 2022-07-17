package com.redslounge.r3dvanilla;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public class Utils
{
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
            note.append(parts[count]).append(" ");
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

    public static String getChatColor(UUID playerUUID)
    {
        LuckPerms api = LuckPermsProvider.get();

        User user = api.getUserManager().getUser(playerUUID);
        Group group = api.getGroupManager().getGroup(user.getPrimaryGroup());
        String nodeKeys = group.getNodes(NodeType.PREFIX).toArray()[0].toString();
        String prefixSection = nodeKeys.split("prefix")[1];
        String prefixColor = prefixSection.split("&")[1];

        return "&" + prefixColor.substring(0, 1);
    }

    public static void broadcastMessage(String message)
    {
        Bukkit.broadcastMessage(color(message));
    }
}

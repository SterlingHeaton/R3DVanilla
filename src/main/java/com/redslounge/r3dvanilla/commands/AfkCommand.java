package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AfkCommand implements CommandExecutor, TabCompleter
{
    private Vanilla plugin;

    public AfkCommand(Vanilla pl)
    {
        plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("afk"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.inGame));
            }

            Player player = (Player) sender;
            RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());

            if(playerInfo.isAfk())
            {
                plugin.getAfkTasks().setPlayerUnafk(player, playerInfo, true);
            }
            else
            {
                plugin.getAfkTasks().setPlayerAFK(player, playerInfo);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings)
    {
        return new ArrayList<>();
    }
}

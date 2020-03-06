package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AfkCommand implements CommandExecutor, TabCompleter
{
    private Plugin plugin;

    public AfkCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("afk"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.inGame));
                return false;
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

                if(playerInfo.getAfkId() != -1)
                {
                    plugin.getServer().getScheduler().cancelTask(playerInfo.getAfkId());
                }
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

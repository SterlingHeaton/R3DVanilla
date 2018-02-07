package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AfkCommand implements CommandExecutor
{
    Vanilla plugin;

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
            RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

            if(playerInformation.isAfk())
            {
                plugin.getConfigSettings().getPlayer(player.getUniqueId()).setAfk(false);
                Bukkit.broadcastMessage(Utils.color(player.getName() + " &7&ois no longer AFK"));
                player.setPlayerListName(player.getName());
            }
            else
            {
                plugin.getConfigSettings().getPlayer(player.getUniqueId()).setAfk(true);
                Bukkit.broadcastMessage(Utils.color(player.getName() + " &7&ois now AFK"));
                player.setPlayerListName(Utils.color("&7(afk) &o" + player.getName()));
            }
        }
        return false;
    }
}

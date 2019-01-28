package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PortalCalculatorCommand implements CommandExecutor
{
    private Vanilla plugin;

    public PortalCalculatorCommand(Vanilla plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(Utils.color(Utils.inGame));
            return false;
        }

        Player player = (Player) sender;

        if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL))
        {
            player.sendMessage(Utils.color("&2Nether &aportal location&6: &7&o" + formatLocation(player.getLocation(), true) + "&6."));
        }
        else if(player.getWorld().getEnvironment().equals(World.Environment.NETHER))
        {
            player.sendMessage(Utils.color("&2Overworld &aportal location&6: &7&o" + formatLocation(player.getLocation(), false) + "&6."));
        }
        else
        {
            player.sendMessage(Utils.color("&cThis command can only be used in the overworld or nether&6."));
        }
        return false;
    }

    private String formatLocation(Location location, boolean isOverworld)
    {
        if(isOverworld)
        {
            location.setX(location.getX()/8);
            location.setZ(location.getZ()/8);
        }
        else
        {
            location.setX(location.getX()*8);
            location.setZ(location.getZ()*8);
        }
        return "x: " + location.getBlockX() + " z: " + location.getBlockZ();
    }
}

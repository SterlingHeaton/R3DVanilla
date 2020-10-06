package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("portal")
public class PortalCalculatorCommand extends BaseCommand
{
    private Plugin plugin;
    private String tag = "&8[&6Portal&8]";

    public PortalCalculatorCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Default
    public void onPortal(Player player)
    {
        if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL))
        {
            player.sendMessage(Utils.color(tag + " &4Nether &aportal location: &7&o" + formatLocation(player.getLocation(), true)));
        }
        else if(player.getWorld().getEnvironment().equals(World.Environment.NETHER))
        {
            player.sendMessage(Utils.color(tag + " &2Overworld &aportal location: &7&o" + formatLocation(player.getLocation(), false)));
        }
        else
        {
            player.sendMessage(Utils.color(tag + " &cCan't link nether portal from the End."));
        }
    }

//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
//    {
//        if(!(sender instanceof Player))
//        {
//            sender.sendMessage(Utils.color(Utils.inGame));
//            return false;
//        }
//
//        Player player = (Player) sender;
//
//        if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL))
//        {
//            player.sendMessage(Utils.color("&2Nether &aportal location&6: &7&o" + formatLocation(player.getLocation(), true) + "&6."));
//        }
//        else if(player.getWorld().getEnvironment().equals(World.Environment.NETHER))
//        {
//            player.sendMessage(Utils.color("&2Overworld &aportal location&6: &7&o" + formatLocation(player.getLocation(), false) + "&6."));
//        }
//        else
//        {
//            player.sendMessage(Utils.color("&cThis command can only be used in the overworld or nether&6."));
//        }
//        return false;
//    }
//
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

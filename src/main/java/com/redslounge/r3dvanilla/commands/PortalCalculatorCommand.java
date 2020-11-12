package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("portal")
public class PortalCalculatorCommand extends BaseCommand
{
    private final String tag = "&8[&6Portal&8]";

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

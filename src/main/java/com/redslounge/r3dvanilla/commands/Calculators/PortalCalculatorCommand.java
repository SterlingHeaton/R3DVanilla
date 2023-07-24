package com.redslounge.r3dvanilla.commands.Calculators;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.enums.ChatTags;
import com.redslounge.r3dvanilla.models.enums.RedMessages;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * This class defines and adds functionality to the portal calculator command.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("portal")
public class PortalCalculatorCommand extends BaseCommand
{
    /**
     * This command is used by players to calculate the portal location based on where they're standing.
     *
     * @param player Automatic input from the player who executed the command
     */
    @Default
    @CommandCompletion("@nothing")
    public void onPortal(Player player)
    {
        if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL))
        {
            player.sendMessage(Utils.getCommandReply(
                    ChatTags.PORTAL_CALCULATOR,
                    RedMessages.PORTAL_NETHER_SUCCESS,
                    "&7" + formatLocation(player.getLocation(), true)));
            player.sendMessage(Utils.getCommandReply(
                    ChatTags.PORTAL_CALCULATOR,
                    RedMessages.PORTAL_NETHER_HUB,
                    "&7" + getNetherHubSide(player.getLocation())));
        }
        else if(player.getWorld().getEnvironment().equals(World.Environment.NETHER))
        {
            player.sendMessage(Utils.getCommandReply(
                    ChatTags.PORTAL_CALCULATOR,
                    RedMessages.PORTAL_OVERWORLD_SUCCESS,
                    "&7" + formatLocation(player.getLocation(), false)));
        }
        else
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.PORTAL_CALCULATOR, RedMessages.PORTAL_WRONG_DIMENSION_ERROR, ""));
        }
    }

    /**
     * This command is used by players to calculate the portal location based on x and z input, dimension is based on where the player is.
     *
     * @param player Automatic input from the player who executed the command
     * @param x      Input for the x coordinate
     * @param z      Input for the z coordinate
     */
    @Subcommand("location")
    @Syntax("<X> <Z>")
    @CommandCompletion("@nothing")
    public void onLocationPortal(Player player, int x, int z)
    {
        Location location = player.getLocation();

        location.setX(x);
        location.setZ(z);

        if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL))
        {
            player.sendMessage(Utils.getCommandReply(
                    ChatTags.PORTAL_CALCULATOR,
                    RedMessages.PORTAL_NETHER_SUCCESS,
                    "&7" + formatLocation(location, true)));
            player.sendMessage(Utils.getCommandReply(
                    ChatTags.PORTAL_CALCULATOR,
                    RedMessages.PORTAL_NETHER_HUB,
                    "&7" + getNetherHubSide(location)));
        }
        else if(player.getWorld().getEnvironment().equals(World.Environment.NETHER))
        {
            player.sendMessage(Utils.getCommandReply(
                    ChatTags.PORTAL_CALCULATOR,
                    RedMessages.PORTAL_OVERWORLD_SUCCESS,
                    "&7" + formatLocation(location, false)));
        }
        else
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.PORTAL_CALCULATOR, RedMessages.PORTAL_WRONG_DIMENSION_ERROR, ""));
        }
    }

    /**
     * Class method to generate a string for the correct coordinates.
     *
     * @param location    Input for the location of where the base portal is
     * @param isOverworld Input for whether or not the player is in the overworld or not
     *
     * @return Returns a string that shows the x and z coordinate
     */
    private String formatLocation(Location location, boolean isOverworld)
    {
        if(isOverworld)
        {
            location.setX(location.getX() / 8);
            location.setZ(location.getZ() / 8);
        }
        else
        {
            location.setX(location.getX() * 8);
            location.setZ(location.getZ() * 8);
        }
        return location.getBlockX() + "x " + location.getBlockZ() + "z";
    }

    /**
     * Method is being used to determin the correct side of the netherhub someone should build.
     *
     * @param location The location of the nether portal in question.
     *
     * @return Returns a string of which cardinal direction they should build.
     */
    private String getNetherHubSide(Location location)
    {
        if(Math.abs(location.getX()) > Math.abs(location.getZ()))
        {
            if(location.getX() > 0)
            {
                if(location.getZ() > 0)
                {
                    return "East Wing, South Side";
                }
                else
                {
                    return "East Wing, North Side";
                }
            }
            else
            {
                if(location.getZ() > 0)
                {
                    return "West Wing, South Side";
                }
                else
                {
                    return "West Wing, North Side";
                }
            }
        }
        else
        {
            if(location.getZ() > 0)
            {
                if(location.getX() > 0)
                {
                    return "South Wing, East Side";
                }
                else
                {
                    return "South Wing, West Side";
                }
            }
            else
            {
                if(location.getX() > 0)
                {
                    return "North Wing, East Side";
                }
                else
                {
                    return "North Wing, West Side";
                }
            }
        }
    }
}

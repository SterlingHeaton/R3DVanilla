package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import org.bukkit.entity.Player;

/**
 * Simple command that kicks the user when they want to logout. Convienent if they have a bad connection.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("logout")
public class LogoutCommand extends BaseCommand
{
    /**
     * Base command that lets the user quit the server with a command instead of the GUI.
     *
     * @param player Player that inputed the command.
     */
    @Default
    @CommandCompletion("@nothing")
    public void onLogout(Player player)
    {
        player.kickPlayer(Utils.color("&aSuccessfully logged out."));
    }
}

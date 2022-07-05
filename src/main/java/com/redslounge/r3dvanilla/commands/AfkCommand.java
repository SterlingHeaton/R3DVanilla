package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.managers.AfkManager;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.entity.Player;

/**
 * This class defines and adds functionality to the afk commands.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("afk")
public class AfkCommand extends BaseCommand
{
    // Global variable to access bukkit runnable
    private final Plugin plugin;

    public AfkCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    /**
     * This command is used by players to mark themselves as afk or unafk.
     *
     * @param player Automatic input from the player who executed the command
     */
    @Default
    @CommandCompletion("@nothing")
    public void onAfk(Player player)
    {
        // Variables to access player data.
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Sets the player as afk or unafk depending on their current state.
        if(redPlayer.isAfk())
        {
            AfkManager.setUnafk(player, redPlayer, plugin.getServer().getScheduler());
            dataManager.getAfkPlayers().remove(player);
            redPlayer.setGhostAfk(false);
        }
        else
        {
            AfkManager.setAfk(player, redPlayer, plugin.getServer().getScheduler());
            dataManager.getAfkPlayers().add(player);
        }
    }

    /**
     * This command is used by staff to set themselves as ghost afk, this type of afk ignores events that auto set you back to unafk.
     *
     * @param player Automatic input from the player who executed the command
     */
    @Subcommand("ghost")
    @CommandCompletion("@nothing")
    @CommandPermission("r3dvanilla.commands.afk.ghost")
    public void onGhostAfk(Player player)
    {
        // Variables to access player data.
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Sets the player as afk or unafk depending on their current state.
        if(redPlayer.isAfk())
        {
            AfkManager.setUnafk(player, redPlayer, plugin.getServer().getScheduler());
            dataManager.getAfkPlayers().remove(player);
            redPlayer.setGhostAfk(false);
        }
        else
        {
            AfkManager.setAfk(player, redPlayer, plugin.getServer().getScheduler());
            dataManager.getAfkPlayers().add(player);
            redPlayer.setGhostAfk(true);
        }
    }
}

package com.redslounge.r3dvanilla.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * This class defines and adds functionaltiy to private message commands.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("message|msg|m|whisper|w|tell")
public class PrivateMessageCommand extends BaseCommand
{
    /**
     * This command is used to privately message someone currently in game.
     *
     * @param player           Automatic input from the player who executed the command
     * @param targetPlayerName Input for the target player name
     * @param args             Input for the message the player wants to send
     */
    @Default
    @Syntax("<Player> <Message>")
    @CommandCompletion("@players @nothing")
    private void onMessagePlayer(Player player, String targetPlayerName, String[] args)
    {
        // Variables used to access player data.
        DataManager dataManager = DataManager.getInstance();
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        // Test to see if inputs are correct.
        if(targetPlayer == null)
        {
            player.sendMessage(Utils.color(dataManager.getMessageTag() + "&cPlayer offline or misspelled!"));
            return;
        }

        String message = Utils.buildMessage(args, 0);

        if(player.equals(targetPlayer))
        {
            player.sendMessage(Utils.color("&8[&2From &6Me&8]&7&o " + message));
            return;
        }

        // Update variables, send both players a message, and play a sound for the targetplayer if they have it enabled.
        RedPlayer targetRedPlayer = dataManager.getPlayers().get(targetPlayer.getUniqueId());
        targetRedPlayer.setReplyTo(dataManager.getPlayers().get(player.getUniqueId()));

        player.sendMessage(Utils.color("&8[&2To&6 " + targetPlayer.getName() + "&8]&7&o " + message));
        targetPlayer.sendMessage(Utils.color("&8[&2From&6 " + player.getName() + "&8]&7&o " + message));

        if(targetRedPlayer.hasMessagePing())
        {
            targetPlayer.playSound(targetPlayer.getLocation(), targetRedPlayer.getMessageSound(), 2, targetRedPlayer.getMessageSoundPitch());
        }
    }
}
package com.redslounge.r3dvanilla.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * This class defines and adds functionality to reply command.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("reply|r")
public class ReplyCommand extends BaseCommand
{
    /**
     * This command is used to reply to the last person who messaged you.
     *
     * @param player Automatic input from the player who executed the command
     * @param args   Input for the message the player wants to send
     */
    @Default
    @CommandCompletion("@nothing")
    public void onReply(Player player, String[] args)
    {
        // Variables used to access player data.
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Test to see if another player messaged this player and if they're online.
        if(redPlayer.getReplyTo() == null)
        {
            player.sendMessage(Utils.color(dataManager.getReplyTag() + "&cNo one has messaged you!"));
            return;
        }

        RedPlayer targetRedPlayer = redPlayer.getReplyTo();
        Player targetPlayer = Bukkit.getPlayer(targetRedPlayer.getPlayerUUID());

        if(targetPlayer == null)
        {
            player.sendMessage(Utils.color(dataManager.getReplyTag() + "&cPlayer isn't online!"));
            return;
        }

        // Update variables, send both players a message, and play a sound for the targetplayer if they have it enabled.
        String message = Utils.buildMessage(args, 0);
        targetRedPlayer.setReplyTo(redPlayer);

        player.sendMessage(Utils.color("&8[&2To&6 " + targetPlayer.getName() + "&8]&7&o " + message));
        targetPlayer.sendMessage(Utils.color("&8[&2From&6 " + player.getName() + "&8]&7&o " + message));

        if(targetRedPlayer.hasMessagePing())
        {
            targetPlayer.playSound(targetPlayer.getLocation(), targetRedPlayer.getMessageSound(), 2, targetRedPlayer.getMessageSoundPitch());
        }
    }
}
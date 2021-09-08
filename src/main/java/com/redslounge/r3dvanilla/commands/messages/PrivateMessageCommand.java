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

@CommandAlias("message|msg|m|whisper|w|tell")
public class PrivateMessageCommand extends BaseCommand
{
    @Default
    @Syntax("<Player> <Message>")
    @CommandCompletion("@players @nothing")
    private void onMessagePlayer(Player player, String targetPlayerName, String[] args)
    {
        DataManager dataManager = DataManager.getInstance();
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

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
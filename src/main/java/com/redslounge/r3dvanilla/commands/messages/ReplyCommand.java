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

@CommandAlias("reply|r")
public class ReplyCommand extends BaseCommand
{
    @Default
    @CommandCompletion("@nothing")
    public void onReply(Player player, String[] args)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

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
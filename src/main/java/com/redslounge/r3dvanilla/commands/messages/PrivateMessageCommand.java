package com.redslounge.r3dvanilla.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("message|msg|m|whisper|w|tell")
public class PrivateMessageCommand extends BaseCommand
{
    private final Plugin plugin;

    public PrivateMessageCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Default
    @CommandCompletion("@players")
    private void onMessagePlayer(Player player, String targetPlayerString, String[] args)
    {
        Player targetPlayer = plugin.getServer().getPlayer(targetPlayerString);

        if(targetPlayer == null)
        {
            player.sendMessage(Utils.color("&cPlayer offline or misspelled."));
            return;
        }

        String message = Utils.buildMessage(args, 0);

        if(player.equals(targetPlayer))
        {
            player.sendMessage(Utils.color("&aSelf Note: &7&o" + message));
            return;
        }

        DataManager dataManager = DataManager.getInstance();
        RedPlayer targetRedPlayer = dataManager.getPlayers().get(targetPlayer.getUniqueId());

        targetRedPlayer.setUuidLastMessage(player.getUniqueId());

        player.sendMessage(Utils.color("&8[&2To &6" + targetPlayer.getName() + "&8] &7&o" + message));
        targetPlayer.sendMessage(Utils.color("&8[&2From &6" + player.getName() + "&8] &7&o" + message));

        if(targetRedPlayer.hasMessagePing())
        {
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2, 2);
        }
    }
}

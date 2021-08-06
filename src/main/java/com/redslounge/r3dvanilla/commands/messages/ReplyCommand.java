package com.redslounge.r3dvanilla.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("reply|r")
public class ReplyCommand extends BaseCommand
{
    private final Plugin plugin;

    public ReplyCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Default
    public void onReply(Player player, String[] args)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.getUuidLastMessage() == null)
        {
            player.sendMessage(Utils.color("&cNo one has messaged you."));
            return;
        }

        Player targetPlayer = plugin.getServer().getPlayer(redPlayer.getUuidLastMessage());

        if(targetPlayer == null)
        {
            player.sendMessage(Utils.color("&cPlayer isn't online."));
            return;
        }

        String message = Utils.buildMessage(args, 0);

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

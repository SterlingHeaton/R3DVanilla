package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrivateMessageCommand implements CommandExecutor
{
    private Vanilla plugin;

    public PrivateMessageCommand(Vanilla pl)
    {
        plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("message"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.inGame));
                return false;
            }

            Player player = (Player) sender;

            if(args.length < 1)
            {
                player.sendMessage(Utils.color(Utils.syntax + "/msg <player> <message>"));
                return false;
            }

            Player targetPlayer = plugin.getServer().getPlayer(args[0]);

            if(targetPlayer == null)
            {
                player.sendMessage(Utils.color("&cPlayer isn't online or spelled incorrectly"));
                return false;
            }

            String message = Utils.buildMessage(args, 1);

            if(player == targetPlayer)
            {
                player.sendMessage(Utils.color("&aSelf Note&6: &7&o" + message));
                player.sendMessage(Utils.color("&aUse &7&o/note add <note> &afor long term storage."));
            }

            sendMessages(player, targetPlayer, message);
        }

        if(command.getName().equalsIgnoreCase("reply"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.inGame));
                return false;
            }

            Player player = (Player) sender;

            if(args.length == 0)
            {
                player.sendMessage(Utils.color(Utils.syntax + "/reply <message>"));
                return false;
            }

            RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

            if(playerInformation.getUuidLastMessage() == null)
            {
                player.sendMessage(Utils.color("&cNo one has messaged you."));
                return false;
            }

            Player targetPlayer = plugin.getServer().getPlayer(playerInformation.getUuidLastMessage());

            if(targetPlayer == null)
            {
                player.sendMessage(Utils.color("&cPlayer isn't online"));
                return false;
            }

            String message = Utils.buildMessage(args, 0);

            sendMessages(player, targetPlayer, message);
        }
        return false;
    }

    private void sendMessages(Player player, Player targetPlayer, String message)
    {
        RedPlayer targetPlayerInformation = plugin.getConfigSettings().getPlayer(targetPlayer.getUniqueId());
        targetPlayerInformation.setUuidLastMessage(player.getUniqueId());

        player.sendMessage(Utils.color("&8[&2To &6" + targetPlayer.getName() + "&8] &7&o" + message));
        targetPlayer.sendMessage(Utils.color("&8[&2From &6" + player.getName() + "&8] &7&o" + message));

        if(targetPlayerInformation.isMessagePing())
        {
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
        }
    }
}

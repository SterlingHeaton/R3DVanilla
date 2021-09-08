package com.redslounge.r3dvanilla.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("messageping")
public class MessagePingCommand extends BaseCommand
{
    @Default
    @CommandCompletion("@nothing")
    public void onMessagePing(Player player)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.hasMessagePing())
        {
            redPlayer.setMessagePing(false);
            player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&cDisabled!"));
        }
        else
        {
            redPlayer.setMessagePing(true);
            player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&aEnabled!"));
        }
    }

    @Subcommand("set")
    @Syntax("<Sound> <Pitch>")
    @CommandCompletion("@sounds @nothing")
    public void onSetSound(Player player, String sound, float pitch)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());
        Sound pingSound;

        try
        {
            pingSound = Sound.valueOf(sound.toUpperCase());
        }
        catch(IllegalArgumentException e)
        {
            player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&cThat isn't a valid sound name!"));
            return;
        }

        if(pitch < 0 || pitch > 2)
        {
            player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&cPitch must be between 0-2!"));
            return;
        }

        redPlayer.setMessageSound(pingSound);
        redPlayer.setMessageSoundPitch(pitch);

        player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&aUpdated sound!"));
    }
}

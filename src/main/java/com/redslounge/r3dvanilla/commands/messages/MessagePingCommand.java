package com.redslounge.r3dvanilla.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.idb.DB;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.SQLException;

/**
 * This class defines and adds functionality to messageping and it's subcommand to change the sound.
 *
 * @author Sterling (@sterlingheaton)
 */
@CommandAlias("messageping")
public class MessagePingCommand extends BaseCommand
{
    /**
     * This command is used to toggle the sound that plays when you get a message.
     *
     * @param player Automatic input from the player who executed the command
     */
    @Default
    @CommandCompletion("@nothing")
    public void onMessagePing(Player player)
    {
        // Variables used to access player data.
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Changes boolean and sends player a message.
        if(redPlayer.hasMessagePing())
        {
            try
            {
                DB.executeUpdate("UPDATE players SET messagePing = ? WHERE playerID = ?", false, redPlayer.getPlayerUUID().toString());
                redPlayer.setMessagePing(false);
                player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&cDisabled!"));
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
        }
        else
        {
            try
            {
                DB.executeUpdate("UPDATE players SET messagePing = ? WHERE playerID = ?", true, redPlayer.getPlayerUUID().toString());
                redPlayer.setMessagePing(true);
                player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&aEnabled!"));
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
        }
    }

    /**
     * This command is used to select a different sound and pitch that you hear when someone messages you.
     *
     * @param player Automatic input from the player who executed the command
     * @param sound  Input for specific sound
     * @param pitch  Input for specific pitch
     */
    @Subcommand("set")
    @Syntax("<Sound> <Pitch>")
    @CommandCompletion("@sounds @nothing")
    public void onSetSound(Player player, String sound, float pitch)
    {
        // Variables used to access player data.
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());
        Sound pingSound;

        // Test to see if inputs are correct.
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

        // Update variables and send player a message.
        try
        {
            DB.executeUpdate("UPDATE players SET messageSound = ?, messageSoundPitch = ?", pingSound.name(), pitch);

            redPlayer.setMessageSound(pingSound);
            redPlayer.setMessageSoundPitch(pitch);

            player.sendMessage(Utils.color(dataManager.getMessagePingTag() + "&aUpdated sound!"));
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
}

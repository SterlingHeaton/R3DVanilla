package com.redslounge.r3dvanilla.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.entity.Player;

@CommandAlias("messageping")
public class MessagePingCommand extends BaseCommand
{
    private final String tag = "&8[&6Message Ping&8]";

    @Default
    public void onMessagePing(Player player)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.hasMessagePing())
        {
            redPlayer.setMessagePing(false);
            player.sendMessage(Utils.color(tag + " &4Disabled!"));
        }
        else
        {
            redPlayer.setMessagePing(true);
            player.sendMessage(Utils.color(tag + " &2Enabled!"));
        }
    }
}

package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Private;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.enums.ChatTags;
import com.redslounge.r3dvanilla.models.enums.RedMessages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@CommandAlias("cancelsleep")
public class SleepCommand extends BaseCommand
{
    private final Plugin plugin;

    public SleepCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Private
    @Default
    public void onSleepCommand(Player player)
    {
        DataManager dataManager = DataManager.getInstance();

        if(dataManager.getSleepingPlayers().isEmpty())
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.SLEEP, RedMessages.SLEEP_NONE_ERROR, ""));
            return;
        }

        if(dataManager.isSleepVote())
        {
            player.sendMessage(Utils.getCommandReply(ChatTags.SLEEP, RedMessages.SLEEP_MAJORITY_VOTE, ""));
            return;
        }

        dataManager.setSleepCanceler(player.getUniqueId());
        dataManager.setSleepVote(true);
        plugin.getServer().getScheduler().cancelTask(dataManager.getSleepingID());

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> dataManager.setSleepVote(false), 20 * dataManager.getSleepCooldown());

        ArrayList<Player> tempSleepingPlayers = new ArrayList<>(dataManager.getSleepingPlayers());

        for(Player sleepingPlayer : tempSleepingPlayers)
        {
            GameMode originalGameMode = sleepingPlayer.getGameMode();
            sleepingPlayer.setGameMode(GameMode.SURVIVAL);
            sleepingPlayer.damage(0);
            sleepingPlayer.setGameMode(originalGameMode);
            Bukkit.broadcastMessage(Utils.color(String.format("%s %s &cDoesn't want people to sleep, enabling majority vote.",
                    ChatTags.SLEEP.getTag(),
                    Utils.getChatColor(player.getUniqueId()) + player.getName())));
        }
    }
}

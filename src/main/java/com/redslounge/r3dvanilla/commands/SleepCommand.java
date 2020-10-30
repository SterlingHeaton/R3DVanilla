package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Private;
import com.redslounge.r3dvanilla.DataManager;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("cancelsleep")
public class SleepCommand extends BaseCommand
{
    private Plugin plugin;
    private String tag = "&8[&6Sleep&8]";

    public SleepCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Private
    @Default
    public void onSleepCommand(Player player)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(dataManager.getSleepingPlayers().isEmpty())
        {
            player.sendMessage(Utils.color(tag + " &cNo one is sleeping you silly boi."));
            return;
        }

        if(dataManager.isSleepVote())
        {
            player.sendMessage(Utils.color(tag + " &cMajority vote enabled, can't kick people out of bed."));
            return;
        }

        dataManager.setSleepVote(true);
        plugin.getServer().getScheduler().cancelTask(dataManager.getSleepingID());

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> dataManager.setSleepVote(false), 20 * dataManager.getSleepCooldown());

        for(Player sleepingPlayer : dataManager.getSleepingPlayers())
        {
            GameMode originalGameMode = sleepingPlayer.getGameMode();
            sleepingPlayer.setGameMode(GameMode.SURVIVAL);
            sleepingPlayer.damage(0);
            sleepingPlayer.setGameMode(originalGameMode);
            sleepingPlayer.sendMessage(Utils.color(tag + " " + Utils.getTeamColor(player) + player.getName() + " &cdoesn't want you to sleep, enabling majority vote."));
        }
    }

//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
//    {
//        if(command.getName().equalsIgnoreCase("cancelsleep"))
//        {
//            if(!(sender instanceof Player))
//            {
//                sender.sendMessage(Utils.color(Utils.inGame));
//                return false;
//            }
//
//            Player player = (Player) sender;
//
//            if(plugin.getConfigSettings().getSleepingPlayers().isEmpty())
//            {
//                player.sendMessage(Utils.color("&cNo one is sleeping, silly boi&6."));
//
//                return true;
//            }
//
//            if(plugin.getSleepingCooldown())
//            {
//                player.sendMessage(Utils.color("&cMajority vote is enabled, can't kick people out of bed&6."));
//                return false;
//            }
//
//            plugin.setSleepingCooldown(true);
//            plugin.getServer().getScheduler().cancelTask(plugin.getSleeping());
//
//            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
//            {
//                plugin.setSleepingCooldown(false);
//            }, 20 * plugin.getConfigSettings().getSleepCooldown());
//
//            for(int count = 0; count < plugin.getConfigSettings().getSleepingPlayers().size(); count++)
//            {
//                Player sleepingPlayer = plugin.getConfigSettings().getSleepingPlayers().get(0);
//
//                GameMode originalGamemode = sleepingPlayer.getGameMode();
//                sleepingPlayer.setGameMode(GameMode.SURVIVAL);
//                sleepingPlayer.damage(0);
//                sleepingPlayer.setGameMode(originalGamemode);
//
//                sleepingPlayer.sendMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&odoesn't want you sleeping, enabling majority vote&6."));
//            }
//        }
//        return false;
//    }
}

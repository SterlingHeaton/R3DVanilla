package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.DataManager;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("afk")
public class AfkCommand extends BaseCommand
{
    private Plugin plugin;

    public AfkCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Default
    public void onAfk(Player player)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.isAfk())
        {
            if(redPlayer.isAfk() || plugin.getServer().getScheduler().isCurrentlyRunning(redPlayer.getAfkId()))
            {
                Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + " &7&ois no longer AFK"));
                player.setPlayerListName(player.getName());
                redPlayer.setAfk(false);
                dataManager.getAfkPlayers().remove(player);
            }

            if(plugin.getServer().getScheduler().isQueued(redPlayer.getAfkId()))
            {
                plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
                redPlayer.setAfkId(-1);
            }
        }
        else
        {
            Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&ois now AFK"));
            player.setPlayerListName(Utils.color("&7afk -> " + player.getName()));
            redPlayer.setAfk(true);
            dataManager.getAfkPlayers().add(player);

            if(redPlayer.getAfkId() != -1)
            {
                plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
                redPlayer.setAfkId(-1);
            }
        }
    }
//
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
//    {
//        if(command.getName().equalsIgnoreCase("afk"))
//        {
//            if(!(sender instanceof Player))
//            {
//                sender.sendMessage(Utils.color(Utils.inGame));
//                return false;
//            }
//
//            Player player = (Player) sender;
//            RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());
//
//            if(playerInfo.isAfk())
//            {
//                plugin.getAfkTasks().setPlayerUnafk(player, playerInfo, true);
//            }
//            else
//            {
//                plugin.getAfkTasks().setPlayerAFK(player, playerInfo);
//
//                if(playerInfo.getAfkId() != -1)
//                {
//                    plugin.getServer().getScheduler().cancelTask(playerInfo.getAfkId());
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings)
//    {
//        return new ArrayList<>();
//    }
}

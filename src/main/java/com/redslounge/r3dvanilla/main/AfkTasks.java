package com.redslounge.r3dvanilla.main;

import com.redslounge.r3dvanilla.DataManager;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AfkTasks
{
    private Plugin plugin;

    public AfkTasks(Plugin plugin)
    {
        this.plugin = plugin;
        startTasks();
    }

    public void startTasks()
    {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () ->
        {
            for(Player player : plugin.getServer().getOnlinePlayers())
            {
                DataManager dataManager = DataManager.getInstance();
                RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

                if(redPlayer.isAfk())
                {
                    continue;
                }

                if(plugin.getServer().getScheduler().isCurrentlyRunning(redPlayer.getAfkId()))
                {
                    continue;
                }

                if(plugin.getServer().getScheduler().isQueued(redPlayer.getAfkId()))
                {
                    continue;
                }

                redPlayer.setAfkId(plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                {
                    Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&ois now AFK"));
                    player.setPlayerListName(Utils.color("&7" + player.getName() + " (afk)"));
                    redPlayer.setAfk(true);
                    dataManager.getAfkPlayers().add(player);
                }, 20 * dataManager.getAfkTimer()));
            }
        }, 20, 20);
    }

//    public void startTasks()
//    {
//        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () ->
//        {
//            for(Player player : plugin.getServer().getOnlinePlayers())
//            {
//                RedPlayer playerInfo = plugin.getConfigSettings().getPlayer(player.getUniqueId());
//
//                if(playerInfo.isAfk())
//                {
//                    continue;
//                }
//
//                if(plugin.getServer().getScheduler().isCurrentlyRunning(playerInfo.getAfkId()))
//                {
//                    continue;
//                }
//
//                if(plugin.getServer().getScheduler().isQueued(playerInfo.getAfkId()))
//                {
//                    continue;
//                }
//
//               playerInfo.setAfkId(plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
//               {
//                   setPlayerAFK(player, playerInfo);
//               }, 20 * plugin.getConfigSettings().getAfkTimer()));
//            }
//        }, 20, 20);
//    }
//
//    public void setPlayerAFK(Player player, RedPlayer playerInfo)
//    {
//        Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&ois now AFK&6."));
//        player.setPlayerListName(Utils.color("&7(afk) &o" + player.getName()));
//        playerInfo.setAfk(true);
//        plugin.getConfigSettings().addAfkPlayer(player);
//    }
//
//    public void setPlayerUnafk(Player player, RedPlayer playerInfo, boolean displayMessage)
//    {
//        if(displayMessage)
//        {
//            Bukkit.broadcastMessage(Utils.getTeamColor(player) + Utils.color(player.getName() + " &7&ois no longer AFK&6."));
//        }
//
//        player.setPlayerListName(player.getName());
//        playerInfo.setAfk(false);
//        plugin.getConfigSettings().getAfkPlayers().remove(player);
//    }
}

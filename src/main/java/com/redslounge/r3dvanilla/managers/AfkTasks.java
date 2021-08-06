package com.redslounge.r3dvanilla.managers;

import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AfkTasks
{
    private final Plugin plugin;

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
}

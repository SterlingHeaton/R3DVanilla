package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("afk")
public class AfkCommand extends BaseCommand
{
    private final Plugin plugin;

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
                Bukkit.broadcastMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&ois back from being AFK"));
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
            player.setPlayerListName(Utils.color("&7" + player.getName() + " (afk)"));
            redPlayer.setAfk(true);
            dataManager.getAfkPlayers().add(player);

            if(redPlayer.getAfkId() != -1)
            {
                plugin.getServer().getScheduler().cancelTask(redPlayer.getAfkId());
                redPlayer.setAfkId(-1);
            }
        }
    }
}

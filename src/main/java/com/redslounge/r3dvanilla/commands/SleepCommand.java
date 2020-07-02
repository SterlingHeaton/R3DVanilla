package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SleepCommand implements CommandExecutor, TabCompleter
{
    private Plugin plugin;

    public SleepCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("cancelsleep"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.inGame));
                return false;
            }

            Player player = (Player) sender;

            if(plugin.getConfigSettings().getSleepingPlayers().isEmpty())
            {
                player.sendMessage(Utils.color("&cNo one is sleeping, silly boi&6."));

                return true;
            }

            if(plugin.getSleepingCooldown())
            {
                player.sendMessage(Utils.color("&cMajority vote is enabled, can't kick people out of bed&6."));
                return false;
            }

            plugin.setSleepingCooldown(true);
            plugin.getServer().getScheduler().cancelTask(plugin.getSleeping());

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
            {
                plugin.setSleepingCooldown(false);
            }, 20 * plugin.getConfigSettings().getSleepCooldown());

            for(int count = 0; count < plugin.getConfigSettings().getSleepingPlayers().size(); count++)
            {
                Player sleepingPlayer = plugin.getConfigSettings().getSleepingPlayers().get(0);

                GameMode originalGamemode = sleepingPlayer.getGameMode();
                sleepingPlayer.setGameMode(GameMode.SURVIVAL);
                sleepingPlayer.damage(0);
                sleepingPlayer.setGameMode(originalGamemode);

                sleepingPlayer.sendMessage(Utils.color(Utils.getTeamColor(player) + player.getName() + " &7&odoesn't want you sleeping, enabling majority vote&6."));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings)
    {
        return new ArrayList<>();
    }
}

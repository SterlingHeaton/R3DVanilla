package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
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
    private Vanilla plugin;

    public SleepCommand(Vanilla plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("cancelsleep"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.syntax));
                return false;
            }

            Player player = (Player) sender;

            if(plugin.getSleepingCooldown())
            {
                player.sendMessage(Utils.color("&cMajority vote is enabled, cant kick people out of bed."));
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

                sleepingPlayer.sendMessage(Utils.color(player.getName() + " &7&odoesn't want you sleeping, enabling majority vote."));
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

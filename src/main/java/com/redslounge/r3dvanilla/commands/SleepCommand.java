package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SleepCommand implements CommandExecutor
{
    private Vanilla plugin;

    public SleepCommand(Vanilla pl)
    {
        plugin = pl;
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

            if(plugin.getConfigSettings().getSleepingPlayers().isEmpty())
            {
                player.sendMessage(Utils.color("&cNo one is currently sleeping."));
                return false;
            }

            for(Player sleepingPlayer : new ArrayList<Player>(plugin.getConfigSettings().getSleepingPlayers()))
            {
                if(sleepingPlayer.getGameMode() != GameMode.SURVIVAL)
                {
                    GameMode originalGamemode = sleepingPlayer.getGameMode();

                    sleepingPlayer.setGameMode(GameMode.SURVIVAL);
                    sleepingPlayer.damage(0);
                    sleepingPlayer.setGameMode(originalGamemode);
                }
                else
                {
                    sleepingPlayer.damage(0);
                }

                sleepingPlayer.sendMessage(Utils.color(player.getName() + " &7&owanted to pull an all nighter, so wake up!"));
            }

            plugin.getConfigSettings().getSleepingPlayers().clear();
        }
        return false;
    }
}

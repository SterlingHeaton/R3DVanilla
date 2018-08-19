package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RedAnalytics implements CommandExecutor
{
    Vanilla plugin;

    public RedAnalytics(Vanilla plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("redanalytics"))
        {
            if(!(commandSender instanceof Player))
            {
                commandSender.sendMessage(Utils.color("&cNo go away."));
                return false;
            }

            Player player = (Player) commandSender;

            if(!player.getName().equalsIgnoreCase("redstonehax"))
            {
                player.sendMessage(Utils.color("&cYou're not my creator..."));
                return false;
            }

            player.sendMessage("");
            player.sendMessage(Utils.color("&aNote Limit: &6" + plugin.getConfigSettings().getNoteLimit()));
            player.sendMessage(Utils.color("&aSleep Cooldown: &6" + plugin.getConfigSettings().getSleepCooldown()));
            player.sendMessage(Utils.color("&aSleep Percentage: &6" + plugin.getConfigSettings().getSleepPercent() + "%"));
            player.sendMessage(Utils.color("&aAfk Timer: &6" + plugin.getConfigSettings().getAfkTimer()));
            player.sendMessage(Utils.color("&aAfk Players: &6" + getNames(plugin.getConfigSettings().getAfkPlayers())));
            player.sendMessage(Utils.color("&aSleeping Players: &6" + getNames(plugin.getConfigSettings().getSleepingPlayers())));

            int validPlayers = (plugin.getServer().getOnlinePlayers().size() - plugin.getConfigSettings().getAfkPlayers().size());
            double percentage = (double) plugin.getConfigSettings().getSleepPercent() / 100;
            int neededPlayers = (int) Math.round(validPlayers * percentage);

            player.sendMessage(Utils.color("&aSleeping Calculation: &6" + neededPlayers));
            player.sendMessage("");

        }

        return false;
    }

    private ArrayList<String> getNames(ArrayList<Player> players)
    {
        ArrayList<String> names = new ArrayList<>();

        for(Player player : players)
        {
            names.add(player.getName());
        }

        return names;
    }
}

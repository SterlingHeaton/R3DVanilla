package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BugReport implements CommandExecutor, TabCompleter
{
    private Vanilla plugin;

    public BugReport(Vanilla plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("bugreport"))
        {
            if(!(commandSender instanceof Player))
            {
                return false;
            }

            Player player = (Player) commandSender;

            player.sendMessage(Utils.color("&cSend your bug reports or feature requests here: &ahttps://goo.gl/forms/6X4OXw6oZYNlwCp82"));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings)
    {
        return new ArrayList<>();
    }
}

package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Vanilla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NotesCommand implements CommandExecutor
{
    private Vanilla plugin;

    public NotesCommand(Vanilla pl)
    {
        plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {


        return false;
    }
}

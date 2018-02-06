package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NotesCommand implements CommandExecutor
{
    private Vanilla plugin;

    public NotesCommand(Vanilla pl)
    {
        plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("note"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.inGame));
                return false;
            }

            Player player = (Player) sender;

            if(args.length == 0)
            {
                player.sendMessage(Utils.color(Utils.syntax + "/note <add|del|replace|last|view>"));
                return false;
            }

            if(args[0].equalsIgnoreCase("add"))
            {
                addNote(player, args);
            }
            else if(args[0].equalsIgnoreCase("del"))
            {
                deleteNote(player, args);
            }
            else if(args[0].equalsIgnoreCase("replace"))
            {
                replaceNote(player, args);
            }
            else if(args[0].equalsIgnoreCase("last"))
            {
                replaceLastNote(player, args);
            }
            else if(args[0].equalsIgnoreCase("view"))
            {
                viewNotes(player, args);
            }
            else
            {
                sender.sendMessage(Utils.color(Utils.syntax + "/note <add|del|replace|last|view>"));
            }
        }
        return false;
    }

    private void addNote(Player player, String[] args)
    {
        //  /note add This is a test note  --> "This is a test note"

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(playerInformation.getNotes().size() > plugin.getConfigSettings().getNoteLimit())
        {
            player.sendMessage(Utils.color("&c You have reached the note limit of &7" + plugin.getConfigSettings().getNoteLimit() + "&c."));
            return;
        }

        playerInformation.addNote(Utils.buildMessage(args, 1));
    }

    private void deleteNote(Player player, String[] args)
    {
        //  /note del 1    --> Deletes the first note in the arrayList
        //  /note del all  --> Deletes all the notes for the player

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(!checkIfNumber(args[1]) || !args[1].equalsIgnoreCase("all"))
        {
            player.sendMessage(Utils.color(Utils.syntax + "/note del <number of note|all>"));
            return;
        }
    }

    private void replaceNote(Player player, String[] args)
    {
        //  /note replace 3  --> Replaces the note with a new one
    }

    private void replaceLastNote(Player player, String[] args)
    {
        // /note last  --> Replaces the last note in the arrayList
    }

    private void viewNotes(Player player, String[] args)
    {
        // /note view  --> Views all notes for the given player
    }

    private boolean checkIfNumber(String potentialNumber)
    {
        try
        {
            Integer.parseInt(potentialNumber);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}

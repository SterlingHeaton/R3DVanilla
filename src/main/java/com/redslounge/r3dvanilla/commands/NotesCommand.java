package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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

        if(playerInformation.getNotes().size() >= plugin.getConfigSettings().getNoteLimit())
        {
            player.sendMessage(Utils.color("&c You have reached the note limit of &7" + plugin.getConfigSettings().getNoteLimit() + "&c."));
            return;
        }

        playerInformation.addNote(Utils.buildMessage(args, 1));
        player.sendMessage(Utils.color("&aSuccessfully added note!"));
    }

    private void deleteNote(Player player, String[] args)
    {
        //  /note del 1    --> Deletes the first note in the arrayList
        //  /note del all  --> Deletes all the notes for the player

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(args[1].equalsIgnoreCase("all"))
        {
            playerInformation.getNotes().clear();
            player.sendMessage(Utils.color("&aAll of your notes have been &cdeleted&a."));
            return;
        }

        if(checkIfNumber(args[1]))
        {
            int noteNumber = Integer.parseInt(args[1]);

            if((!checkViableNumber(playerInformation.getNotes().size(), noteNumber)) || noteNumber <= 0)
            {
                player.sendMessage(Utils.color("&cNote number isn't associated with one of your notes."));
                return;
            }

            playerInformation.getNotes().remove(noteNumber -1);
            player.sendMessage(Utils.color("&aYou have removed the note successfully!"));
            return;
        }

        player.sendMessage(Utils.color(Utils.syntax + "/note del <number of note|all>"));
    }

    private void replaceNote(Player player, String[] args)
    {
        //  /note replace 3  --> Replaces the note with a new one

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(!checkIfNumber(args[1]))
        {
            player.sendMessage(Utils.color(Utils.syntax + "/note del <number of note|all>"));
            return;
        }

        if(!checkViableNumber(plugin.getConfigSettings().getNoteLimit(), Integer.parseInt(args[1])))
        {
            player.sendMessage(Utils.color("&cNote number isn't associated with one of your notes. "));
            return;
        }

        playerInformation.getNotes().set(Integer.parseInt(args[1])-1, Utils.buildMessage(args, 2));
        player.sendMessage(Utils.color("&aSuccessfully replaced the note!"));
    }

    private void replaceLastNote(Player player, String[] args)
    {
        // /note last  --> Replaces the last note in the arrayList

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        int lastIndex = playerInformation.getNotes().size()-1;

        playerInformation.getNotes().set(lastIndex, Utils.buildMessage(args, 1));
        player.sendMessage(Utils.color("&aSuccessfully replaced your last note!"));
    }

    private void viewNotes(Player player, String[] args)
    {
        // /note view  --> Views all notes for the given player

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(playerInformation.getNotes().size() == 0)
        {
            player.sendMessage(Utils.color("&cYou don't have any notes! &7&o/note add <note>"));
        }

        for(int count = 0; count < playerInformation.getNotes().size(); count++)
        {
            player.sendMessage(Utils.color("&a" + (count+1) + "&6. &7&o" + playerInformation.getNotes().get(count)));
        }
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

    private boolean checkViableNumber(int limit, int input)
    {
        if(input <= limit)
        {
            return true;
        }
        return false;
    }
}

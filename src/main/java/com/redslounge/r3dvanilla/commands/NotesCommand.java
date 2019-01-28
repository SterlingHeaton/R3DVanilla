package com.redslounge.r3dvanilla.commands;

import com.redslounge.r3dvanilla.main.Utils;
import com.redslounge.r3dvanilla.main.Vanilla;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NotesCommand implements CommandExecutor, TabCompleter
{
    private Vanilla plugin;

    public NotesCommand(Vanilla plugin)
    {
        this.plugin = plugin;
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
                viewNotes(player);
            }
            else
            {
                sender.sendMessage(Utils.color(Utils.syntax + "/note <add|del|replace|last|view>"));
            }
        }

        if(command.getName().equalsIgnoreCase("notes"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Utils.color(Utils.inGame));
                return false;
            }

            Player player = (Player) sender;

            viewNotes(player);
        }
        return false;
    }

    private void addNote(Player player, String[] args)
    {
        //  /note add This is a test note  --> "This is a test note"

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(playerInformation.getNotes().size() >= plugin.getConfigSettings().getNoteLimit())
        {
            player.sendMessage(Utils.color("&cYou have reached the note limit of &7" + plugin.getConfigSettings().getNoteLimit() + "&6."));
            return;
        }

        playerInformation.addNote(Utils.buildMessage(args, 1));
        player.sendMessage(Utils.color("&aSuccessfully added note&6!"));
    }

    private void deleteNote(Player player, String[] args)
    {
        //  /note del 1    --> Deletes the first note in the arrayList
        //  /note del all  --> Deletes all the notes for the player

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(args.length < 2)
        {
            player.sendMessage(Utils.color(Utils.syntax + "/note del <number of note|all>"));
            return;
        }

        if(args[1].equalsIgnoreCase("all"))
        {
            playerInformation.getNotes().clear();
            player.sendMessage(Utils.color("&aAll of your notes have been &cdeleted&6."));
            return;
        }

        if(checkIfNumber(args[1]))
        {
            int noteNumber = Integer.parseInt(args[1]);

            if((!checkViableNumber(playerInformation.getNotes().size(), noteNumber)) || noteNumber <= 0)
            {
                player.sendMessage(Utils.color("&cNote number isn't associated with one of your notes&6."));
                return;
            }

            playerInformation.getNotes().remove(noteNumber -1);
            player.sendMessage(Utils.color("&aYou have removed the note successfully&6!"));
            return;
        }

        player.sendMessage(Utils.color(Utils.syntax + "/note del <number of note|all>"));
    }

    private void replaceNote(Player player, String[] args)
    {
        //  /note replace 3  --> Replaces the note with a new one

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(args.length < 2)
        {
            player.sendMessage(Utils.color(Utils.syntax + "/note replace <number of note> <note>"));
            return;
        }

        if(!checkIfNumber(args[1]))
        {
            player.sendMessage(Utils.color(Utils.syntax + "/note replace <number of note> <note>"));
            return;
        }

        if(!checkViableNumber(plugin.getConfigSettings().getNoteLimit(), Integer.parseInt(args[1])))
        {
            player.sendMessage(Utils.color("&cNote number isn't associated with one of your notes&6."));
            return;
        }

        playerInformation.getNotes().set(Integer.parseInt(args[1])-1, Utils.buildMessage(args, 2));
        player.sendMessage(Utils.color("&aSuccessfully replaced the note&6!"));
    }

    private void replaceLastNote(Player player, String[] args)
    {
        // /note last  --> Replaces the last note in the arrayList

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        int lastIndex = playerInformation.getNotes().size()-1;

        playerInformation.getNotes().set(lastIndex, Utils.buildMessage(args, 1));
        player.sendMessage(Utils.color("&aSuccessfully replaced your last note&6!"));
    }

    private void viewNotes(Player player)
    {
        // /note view  --> Views all notes for the given player

        RedPlayer playerInformation = plugin.getConfigSettings().getPlayer(player.getUniqueId());

        if(playerInformation.getNotes().size() == 0)
        {
            player.sendMessage(Utils.color("&cYou don't have any notes&6! &7&o/note add <note>"));
            return;
        }

        player.sendMessage(Utils.color("&aPersonal Notes&6:"));
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

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(!(commandSender instanceof  Player))
        {
            return null;
        }

        RedPlayer player = plugin.getConfigSettings().getPlayer(((Player) commandSender).getUniqueId());

        List<String> list = new ArrayList<String>();

        if(command.getName().equalsIgnoreCase("note"))
        {
            if(args.length == 1)
            {
                for(String item : getSubCommands())
                {
                    if(item.toLowerCase().startsWith(args[0].toLowerCase()))
                    {
                        list.add(item);
                    }
                }

                return list;
            }

            if(args.length == 2)
            {
                switch(args[0])
                {
                    case "add": return list;
                    case "del": return generateNumberList(player);
                    case "replace": return generateNumberList(player);
                    case "last": return list;
                    case "view": return list;
                }
            }
        }

        return new ArrayList<>();
    }

    public List<String> generateNumberList(RedPlayer player)
    {
        List<String> list = new ArrayList<String>();

        for(int count = 0; count < player.getNotes().size(); count++)
        {
            String noteNumber = String.valueOf(count+1);

            list.add(String.valueOf(noteNumber));
        }
        list.add("all");

        return list;
    }

    private ArrayList<String> getSubCommands()
    {
        ArrayList<String> list = new ArrayList<>();

        list.add("add");
        list.add("del");
        list.add("replace");
        list.add("last");
        list.add("view");

        return list;
    }
}

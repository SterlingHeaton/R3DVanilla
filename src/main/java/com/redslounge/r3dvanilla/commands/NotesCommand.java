package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.redslounge.r3dvanilla.DataManager;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.Plugin;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.entity.Player;

@CommandAlias("note")
public class NotesCommand extends BaseCommand
{
    private Plugin plugin;
    private String tag = "&8[&6Notes&8]";

    public NotesCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Default
    public void onMainCommand(Player player)
    {
        player.sendMessage(Utils.color(tag + " &cInvalid syntax: &7&o/note <add|delete|view>"));
    }

    @Subcommand("add")
    public void onNoteAdd(Player player, String[] args)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.getNotes().size() >= dataManager.getNoteLimit())
        {
            player.sendMessage(Utils.color(tag + " &cYou have run out of room for more notes."));
            return;
        }

        redPlayer.getNotes().add(Utils.buildMessage(args, 0));
        player.sendMessage(Utils.color(tag + " &aSuccessfully added your note!"));
    }

    @Subcommand("delete")
    public void onNoteDelete(Player player, int noteNumber)
    {
        DataManager dataManager =  DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.getNotes().size() < noteNumber || noteNumber < 0)
        {
            player.sendMessage(Utils.color(tag + " &cThat note number isn't associated with any of your notes."));
            return;
        }

        redPlayer.getNotes().remove(noteNumber-1);
        player.sendMessage(Utils.color(tag + " &aSuccessfully deleted your note!"));
    }

    @Subcommand("view")
    public void onViewNotes(Player player)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.getNotes().isEmpty())
        {
            player.sendMessage(Utils.color(tag + " &cNo notes to display."));
            return;
        }

        player.sendMessage(Utils.color(tag + " &aPersonal Notes:"));
        for(int i = 0; i < redPlayer.getNotes().size(); i++)
        {
            player.sendMessage(Utils.color("&a" + (i+1) + "&6. &7&o" + redPlayer.getNotes().get(i)));
        }
    }
}

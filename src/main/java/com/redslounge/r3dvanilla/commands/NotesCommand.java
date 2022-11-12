package com.redslounge.r3dvanilla.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.idb.DB;
import com.redslounge.r3dvanilla.Utils;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.Note;
import com.redslounge.r3dvanilla.models.RedPlayer;
import com.redslounge.r3dvanilla.models.enums.ChatTags;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("note")
public class NotesCommand extends BaseCommand
{
    @Default
    public void onMainCommand(Player player)
    {
        player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &cInvalid syntax: &7&o/note <add | delete | view>"));
    }

    @Subcommand("add")
    @CommandCompletion("@nothing")
    public void onNoteAdd(Player player, String[] args)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.getNotes().size() >= dataManager.getNoteLimit())
        {
            player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &cYou have run out of room for more notes."));
            return;
        }

        try
        {
            String noteString = Utils.buildMessage(args, 0);
            long noteID = DB.executeInsert("INSERT INTO notes (playerID, note) VALUES (?, ?)", player.getUniqueId().toString(), noteString);

            redPlayer.getNotes().add(new Note(noteID, player.getUniqueId(), noteString));
            player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &aSuccessfully added your note!"));
        }
        catch(SQLException e)
        {
            player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &cFailed to add your note, try again and if it still fails let @Sterling#9999 know."));
            System.out.println(e);
        }
    }

    @Subcommand("delete")
    @CommandCompletion("@notes @nothing")
    public void onNoteDelete(Player player, int noteNumber)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.getNotes().size() < noteNumber || noteNumber < 0)
        {
            player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &cThat note number isn't associated with any of your notes."));
            return;
        }

        Note note = redPlayer.getNotes().get(noteNumber - 1);

        try
        {
            DB.executeUpdate("DELETE FROM notes WHERE noteID = ?", note.getNoteID());
            redPlayer.getNotes().remove(noteNumber - 1);
            player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &aSuccessfully deleted your note!"));
        }
        catch(SQLException e)
        {
            player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &cFailed to delete your note, try again and if it still fails let @Sterling#9999 know."));
            System.out.println(e);
        }
    }

    @Subcommand("view")
    @CommandCompletion("@nothing")
    public void onViewNotes(Player player)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.getNotes().isEmpty())
        {
            player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &cNo notes to display."));
            return;
        }

        player.sendMessage(Utils.color(ChatTags.NOTES.getTag() + " &aPersonal Notes:"));
        for(int i = 0; i < redPlayer.getNotes().size(); i++)
        {
            player.sendMessage(Utils.color("&a" + (i + 1) + "&6. &7&o" + redPlayer.getNotes().get(i).getNote()));
        }
    }
}

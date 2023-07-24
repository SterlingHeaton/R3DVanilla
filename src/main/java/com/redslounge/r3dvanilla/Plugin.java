package com.redslounge.r3dvanilla;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.idb.*;
import com.redslounge.r3dvanilla.commands.*;
import com.redslounge.r3dvanilla.commands.Calculators.ItemCalculatorCommand;
import com.redslounge.r3dvanilla.commands.Calculators.PortalCalculatorCommand;
import com.redslounge.r3dvanilla.commands.Calculators.StackCalculatorCommand;
import com.redslounge.r3dvanilla.commands.messages.MessagePingCommand;
import com.redslounge.r3dvanilla.commands.messages.PrivateMessageCommand;
import com.redslounge.r3dvanilla.commands.messages.ReplyCommand;
import com.redslounge.r3dvanilla.events.AddNewPlayer;
import com.redslounge.r3dvanilla.events.AfkEvents;
import com.redslounge.r3dvanilla.events.EndermanEvent;
import com.redslounge.r3dvanilla.events.SleepingEvent;
import com.redslounge.r3dvanilla.managers.AfkManager;
import com.redslounge.r3dvanilla.managers.DataManager;
import com.redslounge.r3dvanilla.models.Note;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class Plugin extends JavaPlugin
{
    private FileConfiguration config;
    private final File configFile = new File(getDataFolder(), "config.yml");
    private BukkitCommandManager commandManager;

    @Override
    public void onEnable()
    {
        if(!configFile.exists())
        {
            this.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        DatabaseOptions options = DatabaseOptions.builder().mysql(
                config.getString("databaseUser"),
                config.getString("databasePass"),
                config.getString("databaseName"),
                config.getString("databaseHost")).build();
        Database database = PooledDatabaseOptions.builder().options(options).createHikariDatabase();
        DB.setGlobalDatabase(database);

        setupCommands();
        setupCommandCompletions();
        setupEvents();
        loadConfig();
        loadPlayerData();

        new AfkManager(this);
    }

    private void setupCommands()
    {
        commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new NotesCommand());
        commandManager.registerCommand(new PortalCalculatorCommand());
        commandManager.registerCommand(new PrivateMessageCommand());
        commandManager.registerCommand(new ReplyCommand());
        commandManager.registerCommand(new MessagePingCommand());
        commandManager.registerCommand(new AfkCommand(this));
        commandManager.registerCommand(new SleepCommand(this));
        commandManager.registerCommand(new RedAnalytics());
        commandManager.registerCommand(new ItemCalculatorCommand());
        commandManager.registerCommand(new StackCalculatorCommand());
        commandManager.registerCommand(new LogoutCommand());
        commandManager.registerCommand(new WikiCommand());
        commandManager.registerCommand(new SlimeChunkCommand());
    }

    private void setupCommandCompletions()
    {
        commandManager.getCommandCompletions().registerCompletion("sounds", c ->
        {
            ArrayList<String> list = new ArrayList<>();

            for(Sound sound : Sound.values())
            {
                list.add(sound.name());
            }
            return list;
        });

        commandManager.getCommandCompletions().registerCompletion("notes", c ->
        {
            ArrayList<String> list = new ArrayList<>();
            RedPlayer redPlayer = DataManager.getInstance().getPlayers().get(c.getPlayer().getUniqueId());

            for(int i = 0; i < redPlayer.getNotes().size(); i++)
            {
                list.add(String.valueOf(i + 1));
            }

            if(!redPlayer.getNotes().isEmpty())
            {
                list.add("all");
            }

            return list;
        });
    }

    private void setupEvents()
    {
        getServer().getPluginManager().registerEvents(new AddNewPlayer(), this);
        getServer().getPluginManager().registerEvents(new AfkEvents(this), this);
        getServer().getPluginManager().registerEvents(new EndermanEvent(), this);
        getServer().getPluginManager().registerEvents(new SleepingEvent(this), this);
    }

    private void loadConfig()
    {
        DataManager dataManager = DataManager.getInstance();
        dataManager.setNoteLimit(config.getInt("noteLimit"));
        dataManager.setDefaultMessagePing(config.getBoolean("defaultMessagePing"));
        dataManager.setSleepCooldown(config.getInt("sleepCooldown"));
        dataManager.setSleepPercentage(config.getInt("sleepPercent"));
        dataManager.setAfkTimer(config.getInt("afkTimer"));
        dataManager.setDefaultMessageSound(Sound.valueOf(config.getString("defaultMessageSound")));
        dataManager.setDefaultMessageSoundPitch((float) config.getDouble("defaultMessageSoundPitch"));
    }

    private void loadPlayerData()
    {
        DataManager dataManager = DataManager.getInstance();

        ArrayList<DbRow> players;
        ArrayList<DbRow> notes;

        try
        {
            players = new ArrayList<>(DB.getResults("SELECT * FROM players"));
            notes = new ArrayList<>(DB.getResults("SELECT * FROM notes"));
        }
        catch(SQLException e)
        {
            this.getLogger().log(Level.SEVERE, "Failed to gather player info.\n" + e);
            return;
        }

        for(DbRow player : players)
        {
            UUID playerUUID = UUID.fromString(player.getString("playerID"));
            boolean messagePing = player.get("messagePing");
            Sound messageSound = Sound.valueOf(player.getString("messageSound"));
            float messageSoundPitch = player.getFloat("messageSoundPitch");
            RedPlayer redPlayer = new RedPlayer(playerUUID, messagePing, messageSound, messageSoundPitch, new ArrayList<>());

            dataManager.getPlayers().put(playerUUID, redPlayer);
        }

        for(DbRow rowNote : notes)
        {
            Note note = new Note(rowNote.getInt("noteID"), UUID.fromString(rowNote.getString("playerID")), rowNote.getString("note"));
            dataManager.getPlayers().get(note.getPlayerID()).getNotes().add(note);
        }
    }

    @Override
    public void onDisable()
    {
        DB.close();
    }
}

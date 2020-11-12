package com.redslounge.r3dvanilla;

import co.aikar.commands.BukkitCommandManager;
import com.redslounge.r3dvanilla.commands.*;
import com.redslounge.r3dvanilla.commands.messages.MessagePingCommand;
import com.redslounge.r3dvanilla.commands.messages.PrivateMessageCommand;
import com.redslounge.r3dvanilla.commands.messages.ReplyCommand;
import com.redslounge.r3dvanilla.events.*;
import com.redslounge.r3dvanilla.main.AfkTasks;
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Plugin extends JavaPlugin
{
    private FileConfiguration config, players;
    private final File configFile = new File(getDataFolder(), "config.yml");
    private final File playersFile = new File(getDataFolder(), "players.yml");

    private BukkitCommandManager commandManager;


    @Override
    public void onEnable()
    {
        if(!configFile.exists())
        {
            this.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        if(!playersFile.exists())
        {
            try
            {
                playersFile.createNewFile();
            }
            catch(IOException e)
            {
                this.getLogger().severe("Failed to create players.yml file. \n" + e.toString());
            }
        }
        players = YamlConfiguration.loadConfiguration(playersFile);

        setupCommands();
        setupEvents();
        loadConfig();

        new AfkTasks(this);
    }

    private void setupCommands()
    {
        commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new NotesCommand(this));
        commandManager.registerCommand(new PortalCalculatorCommand(this));
        commandManager.registerCommand(new PrivateMessageCommand(this));
        commandManager.registerCommand(new ReplyCommand(this));
        commandManager.registerCommand(new MessagePingCommand());
        commandManager.registerCommand(new AfkCommand(this));
        commandManager.registerCommand(new SleepCommand(this));
        commandManager.registerCommand(new RedAnalytics());
    }

    private void setupEvents()
    {
        getServer().getPluginManager().registerEvents(new AddNewPlayer(this), this);
        getServer().getPluginManager().registerEvents(new AfkEvents(this), this);
        getServer().getPluginManager().registerEvents(new EndermanEvent(this), this);
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

        ConfigurationSection section = players.getConfigurationSection("");

        for(String playerSectionKey : section.getKeys(false))
        {
            UUID playerUUID = UUID.fromString(playerSectionKey);
            boolean messagePing = players.getBoolean(playerUUID + ".messagePing");
            List<String> notes = new ArrayList<>(players.getStringList(playerUUID + ".notes"));
            RedPlayer redPlayer = new RedPlayer(messagePing, notes);

            dataManager.getPlayers().put(playerUUID, redPlayer);
        }
    }


    @Override
    public void onDisable()
    {
        DataManager dataManager = DataManager.getInstance();
        for(UUID playerUUID : dataManager.getPlayers().keySet())
        {
            players.set(playerUUID + ".notes", dataManager.getPlayers().get(playerUUID).getNotes());
            players.set(playerUUID + ".messagePing", dataManager.getPlayers().get(playerUUID).hasMessagePing());
        }

        try
        {
            players.save(playersFile);
        }
        catch(IOException e)
        {
            this.getServer().getLogger().severe("Failed to save players.yml");
            this.getServer().getLogger().severe(e.toString());
        }
    }
}

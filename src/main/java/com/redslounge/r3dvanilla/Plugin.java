package com.redslounge.r3dvanilla;

import co.aikar.commands.BukkitCommandManager;
import com.redslounge.r3dvanilla.commands.*;
import com.redslounge.r3dvanilla.commands.messages.MessagePingCommand;
import com.redslounge.r3dvanilla.commands.messages.PrivateMessageCommand;
import com.redslounge.r3dvanilla.commands.messages.ReplyCommand;
import com.redslounge.r3dvanilla.events.*;
import com.redslounge.r3dvanilla.main.AfkTasks;
import com.redslounge.r3dvanilla.models.RedConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import sun.plugin2.message.Message;

import java.io.File;
import java.io.IOException;

public class Plugin extends JavaPlugin
{
//    private Config config;
    private RedConfig configSettings;
    private AfkTasks afkTasks;
    private boolean sleepingCooldown;
    private int sleeping;

    private FileConfiguration config, players;
    private final File configFile = new File(getDataFolder(), "config.yml");
    private final File playersFile = new File(getDataFolder(), "players.yml");

    private BukkitCommandManager commandManager;


    @Override
    public void onEnable()
    {
//        config = new Config(this);

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

        afkTasks = new AfkTasks(this);
    }

    private void setupCommands()
    {
        commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new NotesCommand(this));
        commandManager.registerCommand(new PortalCalculatorCommand(this));
        commandManager.registerCommand(new PrivateMessageCommand(this));
        commandManager.registerCommand(new ReplyCommand(this));
        commandManager.registerCommand(new MessagePingCommand());


        getCommand("afk").setExecutor(new AfkCommand(this));
//        getCommand("message").setExecutor(new PrivateMessageCommand(this));
//        getCommand("reply").setExecutor(new PrivateMessageCommand(this));
//        getCommand("note").setExecutor(new NotesCommand(this));
//        getCommand("notes").setExecutor(new NotesCommand(this));
        getCommand("cancelsleep").setExecutor(new SleepCommand(this));
//        getCommand("messageping").setExecutor(new PrivateMessageCommand(this));
        getCommand("bugreport").setExecutor(new BugReport(this));
        getCommand("redanalytics").setExecutor(new RedAnalytics(this));
//        getCommand("portal").setExecutor(new PortalCalculatorCommand(this));
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

    }


    @Override
    public void onDisable()
    {
//        config.saveSettings();
    }

    public void setConfigSettings(RedConfig configSettings)
    {
        this.configSettings = configSettings;
    }

    public RedConfig getConfigSettings()
    {
        return configSettings;
    }

    public AfkTasks getAfkTasks()
    {
        return afkTasks;
    }

    public boolean getSleepingCooldown()
    {
        return sleepingCooldown;
    }

    public void setSleepingCooldown(boolean sleepingCooldown)
    {
        this.sleepingCooldown = sleepingCooldown;
    }

    public int getSleeping()
    {
        return sleeping;
    }

    public void setSleeping(int sleeping)
    {
        this.sleeping = sleeping;
    }
}

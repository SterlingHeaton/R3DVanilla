package com.redslounge.r3dvanilla.main;

import com.redslounge.r3dvanilla.commands.AfkCommand;
import com.redslounge.r3dvanilla.commands.NotesCommand;
import com.redslounge.r3dvanilla.commands.PrivateMessageCommand;
import com.redslounge.r3dvanilla.events.AddNewPlayer;
import com.redslounge.r3dvanilla.events.AfkEvents;
import com.redslounge.r3dvanilla.events.EndermanEvent;
import com.redslounge.r3dvanilla.objects.RedConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class Vanilla extends JavaPlugin
{
    private Config config;
    private RedConfig configSettings;

    public void onEnable()
    {
        config = new Config(this);

        getServer().getPluginManager().registerEvents(new AddNewPlayer(this), this);
        getServer().getPluginManager().registerEvents(new AfkEvents(this), this);
        getServer().getPluginManager().registerEvents(new EndermanEvent(this), this);

        getCommand("afk").setExecutor(new AfkCommand(this));
        getCommand("message").setExecutor(new PrivateMessageCommand(this));
        getCommand("reply").setExecutor(new PrivateMessageCommand(this));
        getCommand("note").setExecutor(new NotesCommand(this));
    }

    public void onDisable()
    {
        config.saveSettings();
    }

    public void setConfigSettings(RedConfig configSettings)
    {
        this.configSettings = configSettings;
    }

    public RedConfig getConfigSettings()
    {
        return configSettings;
    }


}

package com.redslounge.r3dvanilla;

import com.redslounge.r3dvanilla.commands.*;
import com.redslounge.r3dvanilla.events.*;
import com.redslounge.r3dvanilla.main.AfkTasks;
import com.redslounge.r3dvanilla.main.Config;
import com.redslounge.r3dvanilla.objects.RedConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin
{
    private Config config;
    private RedConfig configSettings;
    private AfkTasks afkTasks;
    private boolean sleepingCooldown;
    private int sleeping;

    @Override
    public void onEnable()
    {
        config = new Config(this);

        getServer().getPluginManager().registerEvents(new AddNewPlayer(this), this);
        getServer().getPluginManager().registerEvents(new AfkEvents(this), this);
        getServer().getPluginManager().registerEvents(new EndermanEvent(this), this);
        getServer().getPluginManager().registerEvents(new SleepingEvent(this), this);

        getCommand("afk").setExecutor(new AfkCommand(this));
        getCommand("message").setExecutor(new PrivateMessageCommand(this));
        getCommand("reply").setExecutor(new PrivateMessageCommand(this));
        getCommand("note").setExecutor(new NotesCommand(this));
        getCommand("notes").setExecutor(new NotesCommand(this));
        getCommand("cancelsleep").setExecutor(new SleepCommand(this));
        getCommand("messageping").setExecutor(new PrivateMessageCommand(this));
        getCommand("bugreport").setExecutor(new BugReport(this));
        getCommand("redanalytics").setExecutor(new RedAnalytics(this));
        getCommand("portal").setExecutor(new PortalCalculatorCommand(this));

        afkTasks = new AfkTasks(this);
    }

    @Override
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

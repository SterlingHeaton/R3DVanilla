package com.redslounge.r3dvanilla.main;

import com.redslounge.r3dvanilla.objects.RedConfig;
import com.redslounge.r3dvanilla.objects.RedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Config
{
    private Vanilla plugin;
    private File configFileMain, configFilePlayer;
    private FileConfiguration configMain, configPlayer;

    public Config(Vanilla pl)
    {
        plugin = pl;

        createFiles();
    }

    private void createFiles()
    {
        boolean createSettings = false;

        if(!plugin.getDataFolder().exists())
        {
            plugin.getDataFolder().mkdirs();
            createSettings = true;
        }

        configFileMain = new File(plugin.getDataFolder(), "Config.yml");
        configFilePlayer = new File(plugin.getDataFolder(), "Player.yml");

        if(!configFileMain.exists())
        {
            createFile(configFileMain);
            createSettings = true;
        }

        if(!configFilePlayer.exists())
        {
            createFile(configFilePlayer);
            createSettings = true;
        }

        if(createSettings)
        {
            createSettings();
        }
        else
        {
            loadSettings();
        }
    }

    private void createSettings()
    {
        plugin.setConfigSettings(new RedConfig(10, false));

        try
        {
            getConfigMain().save(configFileMain);
        }
        catch(IOException e)
        {
            System.out.print(Utils.color("&cSomething went wrong while trying to save the main config file, whoops.. "));
        }
    }

    private void loadSettings()
    {
        plugin.setConfigSettings(new RedConfig(getConfigMain().getInt("noteLimit"), getConfigMain().getBoolean("messagePing")));

        ConfigurationSection section = getConfigPlayers().getConfigurationSection("");
        for(String playerStringID : section.getKeys(false))
        {
            UUID playerID = UUID.fromString(playerStringID);
            String playerName = Bukkit.getPlayer(playerID).getName();
            boolean messagePing = getConfigPlayers().getBoolean(playerID + ".messagePing");
            List<String> notes = new ArrayList<String>(getConfigPlayers().getStringList(playerID + ".notes"));

            RedPlayer player = new RedPlayer(playerID, playerName, messagePing, notes);

            plugin.getConfigSettings().addPlayer(playerID, player);
        }
    }

    public void saveSettings()
    {
        for(UUID playerID : plugin.getConfigSettings().getPlayers())
        {
            getConfigPlayers().set(playerID + ".notes", plugin.getConfigSettings().getPlayer(playerID).getNotes());
            getConfigPlayers().set(playerID + ".messagePing", plugin.getConfigSettings().getPlayer(playerID).isMessagePing());
        }
    }

    private void createFile(File file)
    {
        try
        {
            file.createNewFile();
        }
        catch(Exception e)
        {
            System.out.println(Utils.color("&cFailed to create the file: " + file.getName()));
        }
    }

    public FileConfiguration getConfigMain()
    {
        return configMain;
    }

    public FileConfiguration getConfigPlayers()
    {
        return configPlayer;
    }
}

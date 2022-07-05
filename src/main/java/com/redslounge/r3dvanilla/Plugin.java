package com.redslounge.r3dvanilla;

import co.aikar.commands.BukkitCommandManager;
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
import com.redslounge.r3dvanilla.models.RedPlayer;
import org.bukkit.Sound;
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
        setupCommandCompletions();
        setupEvents();
        loadConfig();

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
                list.add(String.valueOf(i+1));
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
        dataManager.setMessageTag(config.getString("messageTag") + " ");
        dataManager.setReplyTag(config.getString("replyTag") + " ");
        dataManager.setMessagePingTag(config.getString("messagePingTag") + " ");
        dataManager.setPortalTag(config.getString("portalTag") + " ");
        dataManager.setItemCalculatorTag(config.getString("itemCalculatorTag") + " ");
        dataManager.setStackCalculatorTag(config.getString("stackCalculatorTag") + " ");

        ConfigurationSection section = players.getConfigurationSection("");

        for(String playerSectionKey : section.getKeys(false))
        {
            UUID playerUUID = UUID.fromString(playerSectionKey);
            boolean messagePing = players.getBoolean(playerUUID + ".messagePing");
            List<String> notes = new ArrayList<>(players.getStringList(playerUUID + ".notes"));
            Sound sound = Sound.valueOf(players.getString(playerUUID + ".messageSound"));
            float pitch = (float) players.getDouble(playerUUID + ".messageSoundPitch");
            RedPlayer redPlayer = new RedPlayer(playerUUID, messagePing, sound, pitch, notes);

            dataManager.getPlayers().put(playerUUID, redPlayer);
        }
    }

    @Override
    public void onDisable()
    {
        DataManager dataManager = DataManager.getInstance();

        for(RedPlayer redPlayer : dataManager.getPlayers().values())
        {
            players.set(redPlayer.getPlayerUUID() + ".notes", redPlayer.getNotes());
            players.set(redPlayer.getPlayerUUID() + ".messagePing", redPlayer.hasMessagePing());
            players.set(redPlayer.getPlayerUUID() + ".messageSound", redPlayer.getMessageSound().name());
            players.set(redPlayer.getPlayerUUID() + ".messageSoundPitch", redPlayer.getMessageSoundPitch());
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

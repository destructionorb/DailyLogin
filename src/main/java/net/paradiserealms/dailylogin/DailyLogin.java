package net.paradiserealms.dailylogin;

import net.paradiserealms.dailylogin.files.PlayerStorage;
import net.paradiserealms.dailylogin.listeners.PlayerJoin;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
//TODO: create the part of the plugin that will distribute login rewards
//TODO: create the config, which will allow for the configuration of what is distributed as rewards

/**
 * The main class of the DailyLogin plugin. Handles the logic for enabling and disabling the plugin, as well as loading
 * the config file.
 * @author destructionorb
 */
public final class DailyLogin extends JavaPlugin {

    /**
     * Handles the startup logic for the plugin, including outputting minor debug messages and starting the listener.
     */
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nDaily Reward System Starting\n\n" );

        loadConfig();
        //listener for player join
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        PlayerStorage.setup();
        PlayerStorage.save();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nDaily Reward System Started\n\n");
    }

    /**
     * Handles shutdown logic for the plugin, which at this point is just a minor debug message.
     */
    @Override
    public void onDisable() {
        PlayerStorage.save();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nDaily Reward System Shutdown\n\n");
    }

    /**
     * Loads the config file, copying defaults if no changes have been made. Saves the config after this.
     */
    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}

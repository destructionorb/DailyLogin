package net.paradiserealms.dailylogin.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 *Creates the player data storage file, and holds methods for saving it and getting the file
 * @author destructionorb
 */
public class PlayerStorage {

    private static File file;
    private static FileConfiguration playerStorage;

    /**
     * Generate the player data storage file, either getting the existing one or creating a new one if it does not exist
     */
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DailyLogin").getDataFolder(),"playerStorage.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
        playerStorage = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Returns the file when called
     * @return FileConfiguration playerStorage
     */
    public static FileConfiguration get() {
        return playerStorage;
    }

    /**
     * Saves any edits made to the player data storage file when called
     */
    public static void save() {
        try {
            playerStorage.save(file);
        } catch(IOException e) {
            System.out.println("Couldn't save file");
        }
    }
}

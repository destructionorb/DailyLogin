package net.paradiserealms.dailylogin.listeners;

import net.paradiserealms.dailylogin.files.PlayerStorage;
import net.paradiserealms.dailylogin.util.GiveBonus;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.time.LocalDateTime;

/**
 * Listener for player join events on the server
 * @author destructionorb
 */
public class PlayerJoin implements Listener {
    //TODO: a check for a full inventory, which could honestly be avoided entirely by a command or user prompt

    private Plugin plugin;
    private Server server;

    /**
     * Constructs the listener with the plugin and server, so they can be passed onto the GiveBonus class
     * @param plugin the plugin
     * @param server the server the plugin is installed on
     */
    public PlayerJoin(Plugin plugin, Server server) {
        this.plugin = plugin;
        this.server = server;
    }

    /**
     * The event handler for the listener, checks whether it is a new day for the player (or their first time joining).
     * If it is their first time joining, it sets their days played and number of days they've earned rewards for to 1,
     * and their last login to that day. If it is not their first login, it checks when their last login was, and if it
     * was not that same day,it gets the number of days played and number of days they've earned rewards for, and
     * increments both by one. It sets the values in the PlayerStorage file to these incremented values, unless
     * the player's next rewardDay would be 32, in which case it resets it back to 1.
     * @param e a player joining
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        int days = 1; //the number of days the player has logged on
        int rewardDays = 1; //the number in the players reward calendar
        int currday;

        LocalDateTime date = LocalDateTime.now();
        currday = date.getDayOfYear();

            if(!player.hasPlayedBefore()) {
                GiveBonus giveBonus = new GiveBonus(player, rewardDays, plugin, server);
                giveBonus.dailyBonus();
                PlayerStorage.get().set("player." + player.getUniqueId() + ".days", days);
                PlayerStorage.get().set("player." + player.getUniqueId() + ".reward days", rewardDays);
                PlayerStorage.get().set("player." + player.getUniqueId() + ".last login", currday);
                PlayerStorage.save();
            } else if(PlayerStorage.get().getInt("player." + player.getUniqueId() + ".last login") != date.getDayOfYear()) {
                days = PlayerStorage.get().getInt("player." + player.getUniqueId() + ".days");
                days++;
                PlayerStorage.get().set("player." + player.getUniqueId() + ".days", days);
                rewardDays = PlayerStorage.get().getInt("player." + player.getUniqueId() + ".reward days");
                GiveBonus giveBonus = new GiveBonus(player, rewardDays, plugin, server);
                giveBonus.dailyBonus();
                rewardDays++;
                if(rewardDays == 32) {
                    rewardDays = 1;
                }

                PlayerStorage.get().set("player." + player.getUniqueId() + ".reward Days", rewardDays);
                PlayerStorage.get().set("player." + player.getUniqueId() + ".last login", currday);
                PlayerStorage.save();
            }
        }
    }

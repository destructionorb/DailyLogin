package net.paradiserealms.dailylogin.listeners;

import net.paradiserealms.dailylogin.files.PlayerStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;

/**
 * Listener for player join events on the server
 * @author destructionorb
 */
public class PlayerJoin implements Listener {
    //TODO: create and format output when a reward is distributed to a player

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
        int days; //the number of days the player has logged on
        int rewardDays; //the number in the players reward calendar
        int currday;

        LocalDateTime date = LocalDateTime.now();
        currday = date.getDayOfYear();

            if(!player.hasPlayedBefore()) {
                days = 1;
                rewardDays = 1;
                PlayerStorage.get().set("Player." + player.getUniqueId() + ".Days", days);
                PlayerStorage.get().set("Player." + player.getUniqueId() + ".Reward Days", rewardDays);
                PlayerStorage.get().set("Player." + player.getUniqueId() + ".Last Login", currday);
                PlayerStorage.save();
            } else if(PlayerStorage.get().getInt("Player." + player.getUniqueId() + ".Last Login") != date.getDayOfYear()) {
                days = PlayerStorage.get().getInt("Player." + player.getUniqueId() + ".Days");
                days++;
                PlayerStorage.get().set("Player." + player.getUniqueId() + ".Days", days);
                rewardDays = PlayerStorage.get().getInt("Player." + player.getUniqueId() + ".Reward Days");

                rewardDays++;
                if(rewardDays == 32) {
                    rewardDays = 1;
                }

                PlayerStorage.get().set("Player." + player.getUniqueId() + ".Reward Days", rewardDays);
                PlayerStorage.get().set("Player." + player.getUniqueId() + ".Last Login", currday);
                PlayerStorage.save();
            }
        }
    }

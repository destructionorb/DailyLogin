package net.paradiserealms.dailylogin.util;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Provides the player with their daily login bonus
 * @author destructionorb
 */
public class GiveBonus {

    private Player player;
    private int rewardDay;
    private Plugin plugin;
    private Server server;

    /**
     * Constructor that takes the information provided by PlayerJoin in order to be used by dailyBonus()
     * @param player the player being given the login bonus
     * @param rd the player's reward day count
     * @param plugin this plugin
     * @param server the server this plugin is installed on
     */
    public GiveBonus(Player player, int rd, Plugin plugin, Server server) {
        this.player = player;
        rewardDay = rd;
        this.plugin = plugin;
        this.server = server;
    }

    /**
     * The method that actually provides the login bonus to the player. Takes their reward day and matches it to the
     * config, obtaining the reward defined there. It gives the player the reward through the console, and then notifies
     * them that they have received it.
     */
    public void dailyBonus() {
        String reward = plugin.getConfig().getString(Integer.toString(rewardDay));
        server.dispatchCommand(server.getConsoleSender(), "give " + player.getName() + " " + reward);
        player.sendMessage(ChatColor.GOLD + "You've received your reward for Day " + rewardDay + ", thanks for playing!");
    }
}

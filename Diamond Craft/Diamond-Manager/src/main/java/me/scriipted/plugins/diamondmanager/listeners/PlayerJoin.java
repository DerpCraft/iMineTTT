/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager.listeners;

import me.scriipted.plugins.diamondmanager.DiamondManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author tjs238
 */
public class PlayerJoin implements Listener{
    private DiamondManager plugin;
    
    public PlayerJoin(DiamondManager plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.AQUA+"[DiamondCraft] "+ChatColor.LIGHT_PURPLE+"Loading Chat Channels...");
        if (player.hasPermission("dc.member")) {
            player.performCommand("ch g");
            player.performCommand("ch l");
        } else if (player.hasPermission("dc.donor") && player.hasPermission("dc.member")) {
            player.sendMessage(ChatColor.AQUA+"[DiamondCraft] "+ChatColor.LIGHT_PURPLE+"VIP member found. Loading VIP channels!");
            player.performCommand("ch g");
            player.performCommand("ch vip");
            player.performCommand("ch l");
        } else if (player.hasPermission("dc.member") && player.hasPermission("dc.donor") && player.hasPermission("dc.staff")) {
            player.sendMessage(ChatColor.AQUA+"[DiamondCraft] "+ChatColor.LIGHT_PURPLE+"Staff member found. Loading Staff channels!");
            player.performCommand("ch g");
            player.performCommand("ch staff");
            player.performCommand("ch l");
        }
    }
    
}

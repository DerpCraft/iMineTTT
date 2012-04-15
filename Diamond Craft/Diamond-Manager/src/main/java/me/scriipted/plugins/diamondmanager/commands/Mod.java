/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager.commands;

import me.scriipted.plugins.diamondmanager.DiamondManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author tjs238
 */
public class Mod implements CommandExecutor {
    
    private DiamondManager plugin;
    
    public Mod (DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = (Player) sender;
        Server server = Bukkit.getServer();
        if (player.hasPermission("dc.shout.mod") && !player.hasPermission("dc.shout.smod")) {
            String playerName = player.getDisplayName();
            server.broadcastMessage(ChatColor.YELLOW+"[G]"+player.getDisplayName()+ChatColor.YELLOW+": "+ChatColor.GOLD+"Need Help? Message me! "+ChatColor.YELLOW+"Able to place "+ChatColor.RED+"Fire"+ChatColor.YELLOW+", "+ChatColor.AQUA+"Water"+ChatColor.YELLOW+", "+ChatColor.DARK_RED+"Lava"+ChatColor.YELLOW+", "+ChatColor.DARK_AQUA+"Warps");
            return true;
        } else if (!player.hasPermission("dc.shout.mod")){
            player.sendMessage(ChatColor.RED+"You have to be a mod to run this command!");
        } else if (player.hasPermission("dc.shout.mod") && player.hasPermission("dc.shout.smod")) {
            server.broadcastMessage(ChatColor.YELLOW+"[G]"+player.getDisplayName()+ChatColor.YELLOW+": "+ChatColor.GOLD+"Need Help? Message me! "+ChatColor.YELLOW+"Able to help with everything!");
            return true;
        }
        
        return false;
    }
    
}

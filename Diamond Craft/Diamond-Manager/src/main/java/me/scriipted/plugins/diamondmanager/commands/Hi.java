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
public class Hi implements CommandExecutor{
    
    private DiamondManager plugin;
    
    public Hi (DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = (Player) sender;
        Server server = Bukkit.getServer();
        if (player.hasPermission("dc.shout.hi")) {
            server.broadcastMessage(ChatColor.YELLOW+"[G]"+player.getDisplayName()+ChatColor.YELLOW+": "+ChatColor.DARK_BLUE+"W "+ChatColor.DARK_GREEN+"E "+ChatColor.DARK_AQUA+"L "+ChatColor.RED+"C "+ChatColor.DARK_PURPLE+"O "+ChatColor.GOLD+"M "+ChatColor.GRAY+"E");
            server.broadcastMessage(ChatColor.GRAY+"to "+ChatColor.DARK_GRAY+"---- Diamond Craft ----");
            return true;
        } else {
            player.sendMessage(ChatColor.RED+"You must be a Mod to type this command!");
            return true;
        }
    }
    
}

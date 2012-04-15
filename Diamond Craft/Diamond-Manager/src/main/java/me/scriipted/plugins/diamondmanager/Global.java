/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

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
public class Global implements CommandExecutor{
    
    private DiamondManager plugin;
    private Server server = Bukkit.getServer();
    
    public Global (DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equals("global")) {
            if(player.hasPermission("dc.chat.global")) {
                
                String msg = "";
                for (String part : args) {
                    if (msg != "") msg += " ";
                    msg += part;
		}
                
                server.broadcastMessage(ChatColor.YELLOW+"[G]"+player.getDisplayName()+ChatColor.YELLOW+": "+replaceColors(msg));
            }
            return true;
        }
        return false;
    }
    
    public String replaceColors (String message) {
		return message.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
	}
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

import java.util.*;
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
public class Shout implements CommandExecutor{
    
    private DiamondManager plugin;
    private Server server = Bukkit.getServer();
    
    public Map<Player, Boolean> hasShouted = new HashMap<Player, Boolean>();
    
    public Shout (DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equals("shout")) {
            Player player = (Player) sender;
            String msg = "";
            for (String part : args) {
                if (msg != "") msg += " ";
                    msg += part;
	    }
            
            shout (player, msg);
            return true;
        }
        return false;
    }
    
    public void shout(Player player, String msg) {
        if (msg.isEmpty()) {
            player.sendMessage(ChatColor.RED+"You must type a message to shout!");
            return;
        }
        if (hasShouted.containsValue(false) && player.hasPermission("dc.shout")) {
            if (player.hasPermission("dc.color")) {
                server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+replaceColors(msg));
                hasShouted.put(player, true);
                setTimer(player);
            } else {
                server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+msg);
                hasShouted.put(player, true);
                setTimer(player);
            }
        } else if (player.hasPermission("dc.shout.bypass")) {
            if (player.hasPermission("dc.color")) {
                server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+replaceColors(msg));
            } else {
                server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+msg);
            }
        } else if (hasShouted.containsValue(true)) {
            player.sendMessage(ChatColor.RED+"Sorry you must wait 1 minute from when you last shouted to shout!");
        } else {
            if (player.hasPermission("dc.color")) {
                server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+replaceColors(msg));
                hasShouted.put(player, true);
            } else {
                server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+msg);
                hasShouted.put(player, true);
            }
        }
        
        if(plugin.config == null) {
            plugin.config.GetConfig();
        }
        List<String> swear = plugin.config.GetConfig().getStringList("swear");
        /*List<CharSequence> swear = new ArrayList<CharSequence>();
        swear.add("fuck");
        swear.add("shit");
        swear.add("damn");
        swear.add("ass");
        swear.add("cunt");
        swear.add("dick");
        swear.add("cock");
        swear.add("nigger");
        swear.add("bitch"); */
        
         for (CharSequence word: swear) {
             if (msg.toLowerCase().contains(word) && !player.hasPermission("dc.swear")) {
                 player.kickPlayer("Do not swear!");
             }
         }
    }
    
    public void setTimer(final Player player) {
        server.getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {
            public void run() {
                hasShouted.put(player, false);
            }
        }, 1200L);
    }
    
    private String replaceColors (String message) {
		return message.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
	}
}

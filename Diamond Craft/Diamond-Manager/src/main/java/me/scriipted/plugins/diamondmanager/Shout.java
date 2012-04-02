/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

import java.util.HashMap;
import java.util.Map;
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
    
    public Shout (DiamondManager plugin) {
        this.plugin = plugin;
    }
    
    private Long time = plugin.getConfig().getLong("Shout.Timer");

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
        if (hasShouted.containsValue(false) && player.hasPermission("dc.shout")) {
            server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+msg);
            hasShouted.put(player, true);
            setTimer(player);
        } else if (player.hasPermission("dc.shout.bypass")) {
            server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+msg);
        } else if (hasShouted.containsValue(true)) {
            player.sendMessage(ChatColor.RED+"Sorry you must wait 1 minute from when you last shouted to shout!");
        } else {
            server.broadcastMessage(ChatColor.RED+"[S]"+ChatColor.WHITE+player.getDisplayName()+ChatColor.GRAY+": "+msg);
            hasShouted.put(player, true);
        }
    }
    
    public void setTimer(final Player player) {
        server.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {
                hasShouted.put(player, false);
            }
        }, time);
    }
    
    public Map<Player, Boolean> hasShouted = new HashMap<Player, Boolean>();
}

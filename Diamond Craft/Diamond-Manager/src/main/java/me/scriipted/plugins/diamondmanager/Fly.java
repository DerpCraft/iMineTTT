/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author tjs238
 */
public class Fly implements CommandExecutor{
    
    private DiamondManager plugin;
    
    public Fly(DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (cmd.getName().equals("fly")) {
            Player p = (Player) sender;
            if(p.hasPermission("dc.fly")) {
                toogleFlyStatus(p);
                /*if (p.getAllowFlight() == false) {
                    p.setAllowFlight(true);
                    p.sendMessage(ChatColor.GRAY+"You started flying");
                    return true;
                } else if (p.getAllowFlight() == true) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(ChatColor.GRAY+"You have stopped flying");
                    return true;
                }
                p.setAllowFlight(!p.getAllowFlight());
                p.setFlying(p.getAllowFlight());
                return true; */
                return true;
            } else if (!p.hasPermission("dc.fly")) {
                p.sendMessage(ChatColor.GRAY+"You do not have permission to fly!");
                return true;
            }
        }
        
        return false;
    }
    
    public Map<Player, Boolean> isFlying = new HashMap<Player, Boolean>();
    
    public void toogleFlyStatus(Player player) {
        if(isFlying.containsKey(player)) {
            if(isFlying.get(player)) {
                isFlying.put(player, false);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(ChatColor.GRAY+"You Stopped Flying!");
            } else {
                isFlying.put(player, true);
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.GRAY+"You Started to Fly!");
            }
        } else {
            isFlying.put(player, true);
            player.setAllowFlight(true);
            player.sendMessage(ChatColor.GRAY+"You Started To Fly!");
        }
    }
    
}

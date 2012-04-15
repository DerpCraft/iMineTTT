/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager.commands;

import me.scriipted.plugins.diamondmanager.DiamondManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author tjs238
 */
public class playerz implements CommandExecutor {
    private DiamondManager plugin;
    public int onlinePlayerz;
    private Server server = Bukkit.getServer();
    
    public playerz (DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = (Player) sender;
        player.sendMessage("");
        return true;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager.warning;

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
public class Warn implements CommandExecutor{
    
    private DiamondManager plugin;
    
    public Warn (DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = (Player) sender;
        Server server = Bukkit.getServer();
        String[] Nargs = args;
        if (args.length == 1) {
            player.sendMessage(ChatColor.AQUA+"====Warn====");
            player.sendMessage(ChatColor.AQUA+"/warn <p-name> <reason>");
            player.sendMessage(ChatColor.RED+"NOT YET IMPLEMENTED");
            return true;
        } else if (args.length == 2) {
            player.sendMessage(ChatColor.AQUA+"====Warn====");
            player.sendMessage(ChatColor.AQUA+"/warn <p-name> <reason>");
            player.sendMessage(ChatColor.RED+"You need to add a reason to warn!");
            return true;
        } else if (args.length >= 3) {
            Player target = server.getPlayer(args[1]);
            String tname = target.getDisplayName();
            WarnMethod(player, args);
            player.sendMessage(ChatColor.AQUA+"====Warn====");
            player.sendMessage(ChatColor.AQUA+"You warned "+tname+ChatColor.AQUA+" for ");
        }
        return false;
    }
    
    private boolean WarnMethod(CommandSender sender, String[] args) {
        Server server = Bukkit.getServer();
        Player player = (Player) sender;
        Player target = server.getPlayer(args[1]);
        String name = args[1];
        String dname = target.getDisplayName();
        int argsammount = args.length;
        int i;
        String areason = "";
        for (i = 2; i < args.length; i++) {
            areason = areason + " " + args[i];
        }
        WarnedPlayers warnP = plugin.getDatabase().find(WarnedPlayers.class).where().ieq("name", name).ieq("playerName",sender.getName()).findUnique();
        if (warnP == null) {
            warnP = new WarnedPlayers();
            warnP.setPlayer(player);
            warnP.setName(name);
        }
        warnP.setWarnReason(areason);
        int currentLevel = warnP.getWarnLevel();
        warnP.setWarnLevel(currentLevel + 1);
        String sname = player.getDisplayName();
        target.sendMessage(ChatColor.RED+"You have been warned by: "+sname+" for: "+areason);
        player.sendMessage(ChatColor.AQUA+"You have warned: "+dname+" for: "+areason);
        return true;
    }
    
}

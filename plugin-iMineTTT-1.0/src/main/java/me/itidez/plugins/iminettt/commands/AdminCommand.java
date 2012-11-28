/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.commands;

import me.itidez.plugins.iminettt.CommandManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.player.*;

/**
 *
 * @author tjs238
 */
public class AdminCommand {
    @CommandManager.Command(name = "admin", alias = "a", sender = CommandManager.Sender.EVERYONE)
    public static boolean admin(ConsoleCommandSender sender, String... arg) {
        String[] args = getArgs(arg);
        Player player = (Player)sender;
        
        if(player.hasPermission("minettt.admin") {
            if(args.length == 0) {
                // TODO: Enter admin help page
            } else if (args[0].equalsIgnoreCase("status")) {
                // TODO: Enter status method
            } else if (args[0].equalsIgnoreCase("start")) {
                // TODO: Add official game class & startup methods. Below is template
                Game currentGame = GameManager.getCurrentGame();
                if(currentGame == null || !(currentGame instanceof Game)) {
                    currentGame = new Game();
                } 
                
                if(currentGame.hasStarted()) {
                    player.sendMessage(ChatColor.RED+"Error: Game Currently in Progress! Please run /admin <stop/restart> instead");
                    return true;
                }
                GameManager.start(currentGame);
                player.sendMessage(ChatColor.GREEN+"Game countdown engaged!");
            } else if (args[0].equalsIgnoreCase("stop")) {
                // TODO: Add official game class & shutdown methods. Below is template
                Game currentGame = GameManager.getCurrentGame();
                if(currentGame == null || !(currentGame instanceof Game)) {
                    player.sendMessage(ChatColor.RED+"Error: Game not started! Run /admin <start> to begin game!");
                    return true;
                }
                if(currentGame.hasStarted()) {
                    GameManager.stop(currentGame);
                    player.sendMessage(ChatColor.GREEN+"Shutting game down...");
                } else {
                    player.sendMessage(ChatColor.RED+"Error: Game not started! Run /admin <start> to begin game!");
                }
            } else if (args[0].equalsIgnoreCase("restart")) {
                // TODO: Add official game class & shutdown methods. Below is template
                Game currentGame = GameManager.getCurrentGame();
                if(currentGame == null || !(currentGame instanceof Game)) {
                    player.sendMessage(ChatColor.RED+"Error: Game not started! Starting game instead!");
                    GameManager.start(currentGame);
                    return true;
                }
                if(currentGame.hasStarted()) {
                    player.sendMessage(ChatColor.GREEN+"Restarting game in progress");
                    GameManager.stop(currentGame);
                    // TODO: Add Bukkit scheduler to start game again
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED+"Error: Game not started! Starting game instead!");
                    GameManager.start(currentGame);
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("spectate")) {
                // TODO: Add official game class & spectate methods
                Game currentgame = GameManager.getCurrentGame();
                if(currentGame == null || !(currentGame instanceof Game)) {
                    player.sendMessage(ChatColor.RED+"Error: Game not started! Can not spectate in Pre-Game Mode!");
                    return true;
                }
                
                if(player instanceof Player) {
                    Game.addSpectator(player);
                } else {
                    player.sendMessage("You must be a player to spectate");
                }
                
            }
            return true;
        }
        return false;
    }
    
    public String[] getArgs(String... a) {
        String[] args = null;
        int i = 0;
        for(String arg : a) {
            args[i] = arg;
            i++;
        }
        
        return args;
    }
}
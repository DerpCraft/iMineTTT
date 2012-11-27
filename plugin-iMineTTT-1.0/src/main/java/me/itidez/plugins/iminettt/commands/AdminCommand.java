/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.commands;

import me.itidez.plugins.iminettt.CommandManager;
import org.bukkit.command.ConsoleCommandSender;

/**
 *
 * @author tjs238
 */
public class AdminCommand {
    @CommandManager.Command(name = "admin", alias = "a", sender = CommandManager.Sender.EVERYONE)
    public static boolean admin(ConsoleCommandSender sender, String... args) {
        
        return false;
    }
}

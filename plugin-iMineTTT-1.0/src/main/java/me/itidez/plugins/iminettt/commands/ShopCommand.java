/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.commands;

import java.util.HashMap;
import me.itidez.plugins.iminettt.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author tjs238
 */
public class ShopCommand {
    @CommandManager.Command(name = "shop", alias = "s", sender = CommandManager.Sender.PLAYER)
    public static boolean shop(ConsoleCommandSender sender, String... args) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)sender, 5, "Terrorist Shop");
        HashMap<Integer, ItemStack> is = new HashMap<Integer, ItemStack>();
        inv.;
        return true;
    }
}

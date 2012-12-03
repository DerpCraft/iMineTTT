/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.commands;

import me.itidez.plugins.iminettt.CommandManager;
import me.itidez.plugins.iminettt.VaultManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

/**
 *
 * @author tjs238
 */
public class DonorShopCommand implements ConversationAbandonedListener{
    private ConversationFactory convoFactory;
    
    @CommandManager.Command(name = "donorshop", alias = "dshop", sender = CommandManager.Sender.PLAYER)
    public static boolean donorShop(ConsoleCommandSender sender, String... args) {
        Player player = (Player)sender;
        VaultManager vm = new VaultManager();
        if(vm.chat.playerInGroup(player, "donor")) {
            openShop(player);
        }
        return true;
    }
    
    private void openShop(Player p) {
        IconMenu menu = new IconMenu("Donor Shop", 9, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.getPlayer().sendMessage("You have chosen " + event.getName());
                event.setWillClose(true);
            }
        }, plugin)
        .setOption(3, new ItemStack(Material.IRON_SWORD, 1), "Weapons", "Fight to the death with God Weapons")
        .setOption(4, new ItemStack(Material.IRON_CHEST, 1), "Armour", "I help you live ^_^")
        .setOption(5, new ItemStack(Material.EMERALD, 1), "Powerups", "Nuke 'Em ALL!");
    }
}

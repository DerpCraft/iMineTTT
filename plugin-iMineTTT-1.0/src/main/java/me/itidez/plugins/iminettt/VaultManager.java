/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 *
 * @author tjs238
 */
public class VaultManager {
    public Chat chat;
    public Permission permission;
    public Economy economy;
    
    public VaultManager() {
        setupChat();
        setupPermissions();
        setupEconomy();
    }
    
    private boolean setupChat()
    {
        RegisteredServiceProvider chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = (Chat)chatProvider.getProvider();
        }
        return chat != null;
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if(permissionProvider != null) 
            permission = (Permission)permissionProvider.getProvider();
        return permission != null;
    }
    
    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) {
            economy = (Economy)economyProvider.getProvider();
        }
        return economy != null;
    } 
}

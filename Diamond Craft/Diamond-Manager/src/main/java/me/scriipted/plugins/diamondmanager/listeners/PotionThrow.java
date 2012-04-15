/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager.listeners;

import me.scriipted.plugins.diamondmanager.DiamondManager;
import org.bukkit.event.Listener;

/**
 *
 * @author tjs238
 */
public class PotionThrow implements Listener{
    
    private DiamondManager plugin;
    
    public PotionThrow(DiamondManager plugin) {
        this.plugin = plugin;
    }
    
    public void onPotionThrow () {
    //
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author tjs238
 */
public class Protection implements Listener{
    private DiamondManager plugin;
    
    public Protection (DiamondManager plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void event(PlayerInteractEvent ev) {
        Player p = ev.getPlayer();
        Block b = ev.getClickedBlock();
        
        if(ev.getAction() == Action.LEFT_CLICK_BLOCK && p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
            if(plugin.getWorldGuard().canBuild(p, p.getLocation())) {
                b.setType(Material.AIR);
            }
        }
    }
}

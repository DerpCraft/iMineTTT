/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

/**
 *
 * @author tjs238
 */
public class SPower {
    private DiamondManager plugin;
    
    public SPower (DiamondManager plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPowerOn(BlockRedstoneEvent ev) {
        Block block = ev.getBlock();
        if (block.getType().equals(Material.SIGN) && block.isBlockPowered()) {
            transferPower(block);
        }
    }
    
    public void transferPower(Block b) {
       if(b.getType() == Material.SIGN) {
           BlockState state = b.getState();
           if (state instanceof Sign) {
               Sign sign = (Sign)state;
               
               String tchannel = sign.getLine(1);
               
               Location l = null;
               
               if (plugin.channels.containsKey(tchannel)) {
                   plugin.channels.get(plugin.schannels.get(l));
               }
           }
       }
    }
    
}

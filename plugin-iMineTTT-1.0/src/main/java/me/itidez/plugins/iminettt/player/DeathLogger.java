/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.player;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.itidez.plugins.iminettt.Iminettt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author tjs238
 */
public class DeathLogger implements Listener{
    private Iminettt plugin;
    //private EntityManager manager;
    
    public DeathLogger(Iminettt plugin) {
        this.plugin = plugin;
        //this.manager = RemoteEntities.createManager(plugin);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        /*if(player instanceof Player) {
            RemoteEntity entity;
            try {
                entity = manager.createEntity(RemoteEntityType.Villager, player.getLocation(), false);
                entity.setStationary(true);
                entity.getMind().addBehaviour(new InteractBehavior(entity) {
                    @Override
                    public void onInteract(Player inPlayer)
                    {
                        if(inPlayer.getMetadata("Team").toString().equalsIgnoreCase("Detective")) {
                            inPlayer.sendMessage(ChatColor.YELLOW+"You have found the body of "+player.getName()+", they were a(n) "+player.getMetadata("Team").toString());
                            Bukkit.broadcastMessage(ChatColor.GOLD+"The body of "+player.getName()+" has been found! They were a(n) "+player.getMetadata("Team").toString());
                        } else {
                           
                        }
                    }
                });
            } catch (NoNameException ex) {
                Logger.getLogger(DeathLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
}

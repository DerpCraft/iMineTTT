/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.chat;

import me.itidez.plugins.iminettt.Iminettt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

/**
 *
 * @author tjs238
 */
public class ChatManager implements Listener{
    private Iminettt plugin;
    
    public ChatManager(Iminettt plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getDisplayName();
        event.setCancelled(true);
        //for()
    }
}

package me.itidez.plugins.iminettt.chat.listeners;

import static me.itidez.plugins.iminettt.chat.util.Replacer.replaceAll;
import static me.itidez.plugins.iminettt.chat.util.Replacer.replaceAllLiteral;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.itidez.plugins.iminettt.Iminettt;
import me.itidez.plugins.iminettt.chat.api.Target;
import me.itidez.plugins.iminettt.chat.targets.Channel;
import me.itidez.plugins.iminettt.chat.targets.ChatPlayer;

/**
*
* @author iTidez
*/
@SuppressWarnings("unused")
public class PlayerListener implements Listener {
    private Iminettt plugin;
    private boolean prefixOnJoin;
    private boolean prefixOnQuit;

    public PlayerListener(Iminettt instance) {
        plugin = instance;
        prefixOnJoin = plugin.getConfig().getBoolean("plugin.prefixOnJoin", false);
        prefixOnQuit = plugin.getConfig().getBoolean("plugin.prefixOnQuit", false);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void playerJoin(PlayerJoinEvent event) {
        plugin.getPlayerManager().registerPlayer(event.getPlayer().getName());
        if (plugin.getIRCBot() != null) {
            MinecraftJoinEvent evt = new MinecraftJoinEvent(event.getPlayer().getName());
            plugin.getServer().getPluginManager().callEvent(evt);
        }
        if (prefixOnJoin) {
            event.setJoinMessage("[" + plugin.getPlayerManager().getPlayer(event.getPlayer().getName()).getGroup() + "] " + event.getJoinMessage());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    void playerKick(PlayerKickEvent event) {
        if (prefixOnQuit) {
            event.setLeaveMessage("[" + plugin.getPlayerManager().getPlayer(event.getPlayer().getName()).getGroup() + "] "+ event.getLeaveMessage());
        }
        plugin.getPlayerManager().unregisterPlayer(event.getPlayer());
        if (plugin.getIRCBot() != null) {
            MinecraftQuitEvent evt = new MinecraftQuitEvent(event.getPlayer().getName());
            plugin.getServer().getPluginManager().callEvent(evt);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void playerQuit(PlayerQuitEvent event) {
        if (prefixOnQuit) {
            event.setQuitMessage("[" + plugin.getPlayerManager().getPlayer(event.getPlayer().getName()).getGroup() + "] " + event.getQuitMessage());
        }
        plugin.getPlayerManager().unregisterPlayer(event.getPlayer());
        if (plugin.getIRCBot() != null) {
            MinecraftQuitEvent evt = new MinecraftQuitEvent(event.getPlayer().getName());
            plugin.getServer().getPluginManager().callEvent(evt);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    void playerChatLowest(AsyncPlayerChatEvent event) {
        ChatPlayer player = plugin.getPlayerManager().getPlayer(event.getPlayer().getName());
        if (player == null) { return; } // player object was null
        if (player.isMuted()) {
            player.sendMessage(ChatColor.RED + "You are muted.");
            event.setCancelled(true);
            return;
        }
        Target target = player.getTarget();
        if (target == null) { return; } // target object was null
        if (target.isMuted()) {
            player.sendMessage(ChatColor.RED + "Target is muted.");
            event.setCancelled(true);
            return;
        }
        if (player.colorfulChat()) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }
        String format = player.getFormattedMessage();
        Set<Player> players = new HashSet<Player>();
        if (target instanceof Channel) {
            if (!((Channel)target).getOccupants().contains(event.getPlayer())) {
                event.getPlayer().sendMessage(ChatColor.RED + "You aren't on that channel.");
                event.setCancelled(true);
                return;
            }
            List<Player> occupants = ((Channel) target).getOccupants();
            players.addAll(occupants);
            if (!player.bypassIgnore()) {
                // check whether players are ignoring this sender
                for (Player p : occupants) {
                    ChatPlayer tmp = plugin.getPlayerManager().getPlayer(p.getName());
                    if (tmp != null && tmp.isIgnoring(player.getName())) {
                        players.remove(p);
                    }
                }
            }
        } else if (target instanceof ChatPlayer) {
            player.whisperTo(target, event.getMessage());
            ((ChatPlayer)target).whisperFrom(player, event.getMessage());
            event.setCancelled(true);
            return;
        } else {
            players.addAll(target.getRecipients());
        }
        event.getRecipients().clear();
        event.getRecipients().addAll(players);
        event.setFormat(format);
        event.setMessage(event.getMessage());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void playerChatMonitor(AsyncPlayerChatEvent event) {
        if (plugin.getIRCBot() == null) { return; }
        final String message = ChatColor.stripColor(replaceAllLiteral(event.getFormat(), "%2$s", replaceAll(event.getMessage(), '$', "\\\\$")));
        ChatPlayer player = plugin.getPlayerManager().getPlayer(event.getPlayer().getName());
        if (player.getTarget() instanceof Channel) {
            ((Channel)player.getTarget()).sendToIRC(message);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void playerChangedWorld(PlayerChangedWorldEvent event) {
        if (!plugin.getPlayerManager().isPlayerRegistered(event.getPlayer().getName())) {
            return;
        }
        plugin.getChannelManager().playerWorldChange(event.getPlayer(), event.getFrom().getName(), event.getPlayer().getWorld().getName());
    }

}
package me.itidez.plugins.iminettt.chat.listeners;

import me.itidez.plugins.iminettt.Iminettt;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
* @author iTidez
*/
@SuppressWarnings("unused")
public class IRCListener implements Listener {
    private Iminettt plugin;

    public IRCListener(Iminettt instance) {
        plugin = instance;
    }

    @EventHandler
    void ircMessage(IRCMessageEvent event) {
        String format = plugin.getConfig().getString("format.message", "[%t] &d* %m");
        String target = plugin.getChannelManager().getGlobalChannel().getName();
        format = format.replaceAll("%t", plugin.getChannelManager().getGlobalChannel().getColoredName());
        format = format.replaceAll("%p", event.getTag());
        format = format.replaceAll("%n|%dn|%fn", event.getNickname());
        format = format.replaceAll("%m", event.getMessage());
        format = format.replaceAll("&([a-fA-F0-9])", "\u00A7$1");
        plugin.getChannelManager().getGlobalChannel().sendMessage(format);
        plugin.getChannelManager().log(target, ChatColor.stripColor(format));
    }

    @EventHandler
    void ircJoin(IRCJoinEvent event) {
        String format = "[%t] &d* %m";
        String target = plugin.getChannelManager().getGlobalChannel().getName();
        format = format.replaceAll("%t", plugin.getChannelManager().getGlobalChannel().getColoredName());
        format = format.replaceAll("%m", event.getMessage());
        format = format.replaceAll("&([a-fA-F0-9])", "\u00A7$1");
        plugin.getChannelManager().getGlobalChannel().sendMessage(format);
        plugin.getChannelManager().log(target, ChatColor.stripColor(format));
    }

    @EventHandler
    void ircQuit(IRCQuitEvent event) {
        String format = "[%t] &c* %m";
        String target = plugin.getChannelManager().getGlobalChannel().getName();
        format = format.replaceAll("%t", plugin.getChannelManager().getGlobalChannel().getColoredName());
        format = format.replaceAll("%m", event.getMessage());
        format = format.replaceAll("&([a-fA-F0-9])", "\u00A7$1");
        plugin.getChannelManager().getGlobalChannel().sendMessage(format);
        plugin.getChannelManager().log(target, ChatColor.stripColor(format));
    }

    @EventHandler
    void mcMessage(MinecraftMessageEvent event) {
        plugin.getIRCBot().msg(event.getNetwork(), event.getChannel(), event.getMessage());
        plugin.log("[" + event.getNetwork() + "]->" + event.getChannel() + ": " + event.getMessage());
    }

    @EventHandler
    void mcJoin(MinecraftJoinEvent event) {
        plugin.getIRCBot().join(null, null, event.getNickname());
    }

    @EventHandler
    void mcQuit(MinecraftQuitEvent event) {
        plugin.getIRCBot().quit(null, null, event.getNickname());
    }

}
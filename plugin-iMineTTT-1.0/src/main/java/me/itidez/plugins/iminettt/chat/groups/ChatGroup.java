package me.itidez.plugins.iminettt.chat.groups;

import me.itidez.plugins.iminettt.Iminettt;
import me.itidez.plugins.iminettt.chat.api.Group;


/**
* @author iTidez
*/
public class ChatGroup implements Group {
    private Iminettt plugin;
    private String name;
    private String prefix;
    private String suffix;
    private int channels;
    private String format_message;
    private String format_to;
    private String format_from;

    public ChatGroup(Iminettt plugin, String group) {
        this.plugin = plugin;
        this.name = group;
        this.prefix = plugin.getConfig().getString("groups." + group + ".prefix", "");
        this.suffix = plugin.getConfig().getString("groups." + group + ".suffix", "");
        this.channels = plugin.getConfig().getInt("groups." + group + ".settings.max_channels", 1);
        this.format_message = plugin.getConfig().getString("groups." + group + ".format.message", "[%t] %p %n&F: %m");
        this.format_to = plugin.getConfig().getString("groups." + group + ".format.to", "");
        this.format_from = plugin.getConfig().getString("groups." + group + ".format.from", "");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public int maxChannels() {
        return this.channels;
    }
}
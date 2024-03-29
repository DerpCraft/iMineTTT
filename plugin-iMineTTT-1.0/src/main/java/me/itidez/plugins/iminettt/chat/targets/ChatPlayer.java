package me.itidez.plugins.iminettt.chat.targets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.itidez.plugins.iminettt.chat.PlayerManager;
import me.itidez.plugins.iminettt.chat.api.Target;
import me.itidez.plugins.iminettt.chat.util.Replacer;
import me.itidez.plugins.iminettt.chat.util.Replacer.Handler;


import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


/**
*
* @author iTidez
*/
@SuppressWarnings("javadoc")
public class ChatPlayer implements Target {
    private static class NodeGrabber implements Replacer.Handler {
        final String node;

        NodeGrabber(final String node) {
            this.node = node;
        }

        @Override
        public String getValue(final Object... scope) {
            String value = null;
            if (scope[2] != null) {
                value = ((ConfigurationSection) scope[2]).getString(node);
            }
            if (value == null) {
                value = ((ConfigurationSection) scope[1]).getString(node);
            }
            return value;
        }
    }

    public enum Type {
        GLOBAL("global"),
        NORMAL("normal"),
        WHISPER_RECEIVE("whisper_receive"),
        WHISPER_SEND("whisper_send");

        private String type;

        private Type(final String type) {
            this.type = type;
        }

        public String getName() {
            return this.type;
        }

    }
    private final static Replacer[] replacers;
    private final static Replacer[] replacers_whisper;
    static {
        final Handler AFK = new NodeGrabber("afk");
        final Handler GROUP = new NodeGrabber("group");
        final Handler HEROES =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    final ChatPlayer chatPlayer = (ChatPlayer) scope[0];
                    final PlayerManager manager = chatPlayer.manager;
                    return "";
                }
            };
        final Handler PREFIX = new NodeGrabber("prefix");
        final Handler SUFFIX = new NodeGrabber("suffix");
        final Handler SELF =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    return ((ChatPlayer) scope[0]).getName();
                }
            };
        final Handler SELF_DISPLAY =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    final Player player = ((ChatPlayer) scope[0]).getPlayer();
                    if (player != null) return player.getDisplayName();
                    return "";
                }
            };
        final Handler TARGET =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    final Target target = ((ChatPlayer) scope[0]).target;
                    if (target != null) {
                        if (target instanceof Channel) return ((Channel)target).getColoredName();
                        return target.getName();
                    }
                    return "";
                }
            };
        final Handler TARGET_DISPLAY =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    final Player p = ((ChatPlayer) scope[0]).getPlayer();
                    if (p != null) return p.getDisplayName();
                    return "";
                }
            };
        final Handler WORLD =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    final ChatPlayer chatPlayer = (ChatPlayer) scope[0];
                    return chatPlayer.manager.getPlugin().getWorldManager().getAlias(chatPlayer.world);
                }
            };
        final Handler MESSAGE =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    return (String) scope[3];
                }
            };

        replacers = new Replacer[] {
            new Replacer("%afk", AFK, false),
            new Replacer("%group", GROUP, false),
            new Replacer("%g", GROUP, true),
            new Replacer("%hero", HEROES, false),
            new Replacer("%h", HEROES, true),
            new Replacer("%prefix", PREFIX, false),
            new Replacer("%p", PREFIX, true),
            new Replacer("%name", SELF, false),
            new Replacer("%n", SELF, true),
            new Replacer("%display", SELF_DISPLAY, false),
            new Replacer("%dn", SELF_DISPLAY, true),
            new Replacer("%suffix", SUFFIX, false),
            new Replacer("%s", SUFFIX, false),
            new Replacer("%target", TARGET, false),
            new Replacer("%t", TARGET, true),
            new Replacer("%channel", TARGET, false),
            new Replacer("%c", TARGET, true),
            new Replacer("%disp_target", TARGET_DISPLAY, false),
            new Replacer("%dt", TARGET_DISPLAY, true),
            new Replacer("%message", MESSAGE, false),
            new Replacer("%m", MESSAGE, true),
            new Replacer("%world", WORLD, false),
            new Replacer("%w", WORLD, true)};
        Arrays.sort(replacers);

        final Handler WHISPER_TARGET =
            new Replacer.Handler() {
                @Override
                public String getValue(final Object... scope) {
                    return ((ChatPlayer) scope[0]).reply.getName();
                }
            };
        replacers_whisper = new Replacer[] {
            new Replacer("%afk", AFK, false),
            new Replacer("%group", GROUP, false),
            new Replacer("%g", GROUP, true),
            new Replacer("%hero", HEROES, false),
            new Replacer("%h", HEROES, true),
            new Replacer("%prefix", PREFIX, false),
            new Replacer("%p", PREFIX, true),
            new Replacer("%name", SELF, false),
            new Replacer("%n", SELF, true),
            new Replacer("%display", SELF_DISPLAY, false),
            new Replacer("%dn", SELF_DISPLAY, true),
            new Replacer("%suffix", SUFFIX, false),
            new Replacer("%s", SUFFIX, false),
            // Begin whisper specific
            new Replacer("%target", WHISPER_TARGET, false),
            new Replacer("%t", WHISPER_TARGET, true),
            new Replacer("%channel", WHISPER_TARGET, false),
            new Replacer("%c", WHISPER_TARGET, true),
            // End whisper specific
            new Replacer("%message", MESSAGE, false),
            new Replacer("%m", MESSAGE, true),
            new Replacer("%disp_target", TARGET_DISPLAY, false),
            new Replacer("%dt", TARGET_DISPLAY, true),
            new Replacer("%world", WORLD, false),
            new Replacer("%w", WORLD, true)};
        Arrays.sort(replacers_whisper);
    }

    private boolean afk; // whether the player is afk or not
    private String afk_message;
    private final Set<String> auto_join = new HashSet<String>();
    private final Set<String> ignores = new HashSet<String>();
    private boolean bypass_ignore = false;
    private boolean colorful;
    private String group; // the player's group
    private final PlayerManager manager;
    private boolean muted;
    private final String name;
    private Target reply;
    private Target target;
    private String world; // the player's current world

    public ChatPlayer(final PlayerManager man, final Player p) {
        long time = System.nanoTime();
        manager = man;
        name = p.getName();
        world = p.getWorld().getName();
        colorful = p.hasPermission("chatsuite.colorize");
        List<String> joins;
        List<String> ignored;
        if (manager.getConfig().getConfigurationSection(name) != null) {
            joins = manager.getConfig().getStringList(name + ".auto_join");
            ignored = manager.getConfig().getStringList(name + ".ignores");
        } else {
            joins = manager.getConfig().getStringList("default_channels");
            ignored = new ArrayList<String>();
        }
        auto_join.addAll(joins);
        ignores.addAll(ignored);
        if (manager.getConfig().get(name) != null) {
            final String t = manager.getConfig().getString(getName() + ".target");
            if (t != null) {
                target = manager.getPlugin().getTarget(t);
            }
        }
        if (target == null) {
            target = manager.getPlugin().getChannelManager().getGlobalChannel();
        }
        getGroup();
        muted = manager.getConfig().getBoolean(getName() + ".muted", false);
        if (muted) {
            p.sendMessage(ChatColor.RED + "You are muted.");
        }
        String nick = manager.getConfig().getString(getName() + ".nickname");
        if (nick != null) {
            p.setDisplayName(nick);
            p.sendMessage(ChatColor.GREEN + "Your nickname is: " + ChatColor.WHITE + nick);
        }
        bypass_ignore = p.hasPermission("chatsuite.bypass.ignore");
        time = System.nanoTime() - time;
        manager.getPlugin().debug("Player '" + name + "' registered in group '" + group + "' and " + (muted ? "" : "not ") + "muted took " + (time / 1000000L) + "ms. (" + time + "ns)");
    }

    public boolean colorfulChat() {
        return colorful;
    }

    public boolean addIgnore(String name) {
        return ignores.add(name);
    }

    public boolean removeIgnore(String name) {
        return ignores.remove(name);
    }

    public boolean bypassIgnore() {
        return bypass_ignore;
    }

    public boolean isIgnoring(String name) {
        return ignores.contains(name);
    }

    public List<String> getIgnoreList() {
        return new ArrayList<String>(ignores);
    }

    public Set<String> getAutoJoinChannels() {
        return auto_join;
    }

    public String getAutoJoinChannelString() {
        final StringBuilder ajoin = new StringBuilder();
        for (final String ch : auto_join) {
            ajoin.append(ChatColor.AQUA).append(ch).append(ChatColor.WHITE).append(", ");
        }
        return ajoin.toString().substring(0, ajoin.toString().length()-2);
    }

    public String getFormattedMessage() {
        String format = manager.getPlugin().getChannelManager().getConfig().getString("channels." + getTarget().getName() + ".format");
        if (format == null) {
            format = manager.getPlugin().getConfig().getString("groups." + group + ".format.message");
            if (format == null) {
                format = manager.getPlugin().getConfig().getString("format.message");
                if (format == null) {
                    format = "[%t] %p %n&F: %m";
                }
            }
        }
        format = parse(format, "%2$s", false);
        return format;
    }

    public String getFormattedWhisperFrom(final Target target, final String message) {
        String format = manager.getPlugin().getConfig().getString("groups." + ((ChatPlayer)target).getGroup() + ".format.from");
        if (format == null) {
            format = manager.getPlugin().getConfig().getString("format.from");
            if (format == null) {
                format = "&7[From] %t>>&F: %m";
            }
        }
        format = parse(format, message, true);
        return format;
    }

    public String getFormattedWhisperTo(final Target target, final String message) {
        String format = manager.getPlugin().getConfig().getString("groups." + ((ChatPlayer)target).getGroup() + ".format.to");
        if (format == null) {
            format = manager.getPlugin().getConfig().getString("format.to");
            if (format == null) {
                format = "&7[To] %t>>&F: %m";
            }
        }
        format = parse(format, message, true);
        return format;
    }

    public String getGroup() {
        long time = System.nanoTime();
        final Player p = getPlayer();
        if (p == null)
            return manager.getPlugin().getDefaultGroup();
        int weight = 0;
        for (final String key : manager.getPlugin().getGroups()) {
            final int i = manager.getPlugin().getGroupNode(key).getInt("weight");
            if ((p.hasPermission("chatsuite.groups." + key) || p.hasPermission("group." + key)) && i > weight) {
                weight = i;
                group = key;
            }
        }
        if (group == null) {
            group = p.isOp() ? manager.getPlugin().getOpGroup() : manager.getPlugin().getDefaultGroup();
        }
        time = System.nanoTime() - time;
        manager.getPlugin().debug(name + ": Determined '" + group + "' in " + (time / 1000000L) + "ms. (" + time + "ns)");
        return group;
    }

    @Override
    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return manager.getPlugin().getServer().getPlayer(name);
    }

    public Set<Player> getRecipients() {
        Set<Player> players = new HashSet<Player>();
        players.add(getPlayer());
        return players;
    }

    public Target getTarget() {
        return target;
    }

    @Override
    public boolean isMuted() {
        return muted;
    }

    public void join(final Channel c) {
        if (!auto_join.contains(c.getName())) {
            sendMessage(c.getColoredName() + " added to Auto-Join list.");
        }
        auto_join.add(c.getName());
    }

    public String parse(String format, final String message, final boolean isWhisper) {
        final ConfigurationSection group = manager.getPlugin().getGroupNode(this.group);
        final ConfigurationSection user = manager.getConfig().getConfigurationSection(this.name);
        format = Replacer.makeReplacements(format, isWhisper ? replacers_whisper : replacers, this, group, user, message);
        format = ChatColor.translateAlternateColorCodes('&', format);
        return format;
    }

    public void part(final Channel c) {
        if (auto_join.contains(c.getName())) {
            sendMessage(c.getColoredName() + " removed from Auto-Join list.");
        }
        auto_join.remove(c.getName());
    }

    @Override
    public void persist() {
        final String t = (target instanceof Channel ? "c:" + target.getName() : "p:" + target.getName());
        manager.getConfig().set(getName() + ".target", t);
        final List<String> joins = new ArrayList<String>();
        joins.addAll(auto_join);
        if (joins.size() > 0) {
            manager.getConfig().set(getName() + ".auto_join", joins);
        }
        manager.getConfig().set(getName() + ".muted", muted);
        final List<String> ignore = new ArrayList<String>();
        ignore.addAll(ignores);
        if (ignore.size() > 0) {
            manager.getConfig().set(getName() + ".ignores", ignore);
        }
        Player p = getPlayer();
        if (p != null && p.getDisplayName() != null && !p.getDisplayName().equals(p.getName())) {
            manager.getConfig().set(getName() + ".nickname", getPlayer().getDisplayName());
        }
    }

    public void reply(final String message) {
        if (reply == null) { return; }
        whisperTo(reply, message);
        ((ChatPlayer)reply).whisperFrom(this, message);
    }

    @Override
    public void sendMessage(final String message) {
        final Player p = getPlayer();
        if (p != null) { p.sendMessage(message); }
    }

    public void setColorfulChat(final boolean val) {
        colorful = val;
    }

    public void setPrefix(String prefix) {
        manager.getConfig().set(this.name + ".prefix", prefix);
    }

    public void setSuffix(String suffix) {
        manager.getConfig().set(this.name + ".suffix", suffix);
    }

    public void setTarget(final Target t) {
        setTarget(t, false);
    }

    public void setTarget(final Target t, final boolean silent) {
        target = t;
        if (!silent) {
            target = t;
            final Player p = getPlayer();
            if (p != null) { p.sendMessage("[ChatSuite] Your target is now: " + target.getName()); }
        }
    }

    public void setWorld(final String w) {
        getGroup();
        world = w;
    }

    public void toggleAfk(final String message) {
        this.afk_message = message;
        this.afk = !this.afk;
    }

    @Override
    public void toggleMute() {
        muted = !muted;
        if (muted) {
            sendMessage(ChatColor.RED + "You have been muted.");
        } else {
            sendMessage(ChatColor.GREEN + "You have been unmuted.");
        }
    }

    /**
* Indicates that this chat player has just received a whisper from the specified target
* @param from The person who just sent a whisper
* @param message The contents of the message
*/
    public void whisperFrom(final Target from, final String message) {
        if (from instanceof ChatPlayer && isIgnoring(from.getName())) {
            return;
        }
        reply = from;
        final String format = getFormattedWhisperFrom(from, message);
        sendMessage(format);
    }

    public void whisperTo(final Target to, final String message) {
        if (to instanceof ChatPlayer && ((ChatPlayer) to).isIgnoring(to.getName())) {
            return;
        }
        reply = to;
        final String format = getFormattedWhisperTo(to, message);
        sendMessage(format);
        if (to instanceof ChatPlayer && ((ChatPlayer)to).afk) {
            sendMessage(to.getName() + " is afk: " + ((ChatPlayer)to).afk_message);
        }
    }
}
package me.itidez.plugins.iminettt.chat;

import me.itidez.plugins.iminettt.chat.api.Manager;
import me.itidez.plugins.iminettt.chat.targets.Channel;
import me.itidez.plugins.iminettt.chat.targets.ChatPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import me.itidez.plugins.iminettt.Iminettt;

/**
*
* @author krinsdeath
*/
public class PlayerManager implements Manager {
    private Iminettt plugin;

    private HashMap<String, ChatPlayer> players = new HashMap<String, ChatPlayer>();
    private FileConfiguration configuration;
    private File config;
    private boolean persist = false;

    public PlayerManager(Iminettt instance) {
        clean();
        plugin = instance;
        config = new File(plugin.getDataFolder(), "players.yml");
        if (!config.exists()) {
            getConfig().setDefaults(YamlConfiguration.loadConfiguration(plugin.getClass().getResourceAsStream("/defaults/players.yml")));
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        saveConfig();
        persist = plugin.getConfig().getBoolean("plugin.persist_user_settings");
        buildPlayerList();
    }

    public void clean() {
        if (persist) {
            for (ChatPlayer player : players.values()) {
                player.persist();
            }
        }
        players.clear();
    }

    @Override
    public FileConfiguration getConfig() {
        if (configuration == null) {
            configuration = YamlConfiguration.loadConfiguration(config);
            configuration.setDefaults(YamlConfiguration.loadConfiguration(config));
        }
        return configuration;
    }

    @Override
    public void saveConfig() {
        try {
            getConfig().save(config);
        } catch (Exception e) {
            plugin.log("An error occurred while saving 'players.yml'");
        }
    }

    @Override
    public Iminettt getPlugin() {
        return plugin;
    }

    private void buildPlayerList() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            registerPlayer(p.getName());
        }
    }

    /**
* Gets the ChatPlayer instance for the player specified
* @param p The player we're fetching.
* @return The player's ChatPlayer instance if it already exists, or creates a new instance for them
*/
    public ChatPlayer getPlayer(String p) {
        if (plugin.getServer().getPlayer(p) == null) {
            return null;
        }
        if (!isPlayerRegistered(p)) {
            registerPlayer(p);
        }
        return players.get(p);
    }

    public Set<ChatPlayer> getPlayers() {
        return new HashSet<ChatPlayer>(players.values());
    }

    /**
* Checks whether the specified player is registered with ChatSuite or not
* @param p The player we're checking
* @return true if the player is registered in ChatSuite, otherwise false
*/
    public boolean isPlayerRegistered(String p) {
        return (players.get(p) != null);
    }

    public void registerPlayer(String player) {
        if (players.containsKey(player)) {
            return;
        }
        ChatPlayer cplayer = new ChatPlayer(this, plugin.getServer().getPlayer(player));
        players.put(player, cplayer);
        Channel chan = plugin.getChannelManager().getGlobalChannel();
        if (chan != null) {
            chan.join(cplayer.getPlayer());
        }
        for (String channel : cplayer.getAutoJoinChannels()) {
            chan = plugin.getChannelManager().getChannel(channel);
            if (chan != null && !chan.contains(player)) {
                chan.join(cplayer.getPlayer());
            }
        }
        plugin.getTarget(getConfig().getString(cplayer.getName() + ".target", "c:" + plugin.getChannelManager().getDefaultChannel()));
    }

    public void unregisterPlayer(Player player) {
        if (!players.containsKey(player.getName())) {
            return;
        }
        plugin.getChannelManager().removePlayerFromAllChannels(player);
        players.get(player.getName()).persist();
        players.remove(player.getName());
        plugin.debug("Player '" + player.getName() + "' unregistered");
    }

}

package me.itidez.plugins.iminettt.chat;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.plugin.Plugin;

/**
*
* @author itidez
*/
public class WorldManager {
    private Iminettt plugin;
    private MVWorldManager MVWorldManager;

    public WorldManager(Iminettt instance) {
        plugin = instance;
        fetchAliases();
    }

    public void clean() {
        MVWorldManager = null;
    }

    public String getAlias(String world) {
        if (MVWorldManager != null && MVWorldManager.isMVWorld(world)) {
            return MVWorldManager.getMVWorld(world).getColoredWorldString();
        }
        return world;
    }

    private void fetchAliases() {
        Plugin tmp = plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (tmp != null) {
            plugin.debug("Found Multiverse-Core! Registering aliases...");
            MultiverseCore multiverse = (MultiverseCore) tmp;
            MVWorldManager = multiverse.getMVWorldManager();
        }
    }

}

package me.itidez.plugins.iminettt;

import java.util.logging.Logger;
import me.itidez.plugins.iminettt.commands.AdminCommand;
import me.itidez.plugins.iminettt.commands.DonorShopCommand;
import me.itidez.plugins.iminettt.commands.ShopCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Iminettt extends JavaPlugin {
    private Logger log;
    private PluginDescriptionFile description;
    private String prefix;
    private String version;
    public boolean debug = false;
    
    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        this.log = Logger.getLogger("Minecraft");
        this.description = getDescription();
        this.version = this.description.getVersion();
        this.prefix = ("[" + this.description.getName() + "] ");
        debug("Setting up error logger");
        ErrorLogger.register(this, "iMineTTT", "me.itidez.plugins", "http://mantis.derpcraft.co/");
        debug("Registered Error Logger");
        CommandManager commandRegistrator = new CommandManager(this);
        commandRegistrator.register(AdminCommand.class);
        commandRegistrator.register(ShopCommand.class); // iTidez Edit: Added on GitHub (Fetch REPO)
        commandRegistrator.register(DonorShopCommand.class);
        debug("Registered Commands"); // iTidez Edit: Added on GitHub (Fetch REPO)
        /*
         * Remote Entity Manager Setup
         * Do not edit past class file name
         */
        //EntityManager manager = RemoteEntities.createManager(this);
        
        // Actual Entity variable. Assign player death's location to spawn location
        // RemoteEntity entity = manager.createEntity(RemoteEntity.Zombie,     Bukkit.getWorld("world").getSpawnLocation(), false);
        
    }
    
    public void log(String message) {
        this.log.info(this.prefix + message);
    }
    
    public void debug(String message) {
        this.log.info("[DEV]"+this.prefix+message);
    }
}
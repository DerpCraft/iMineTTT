package me.itidez.plugins.iminettt;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.itidez.plugins.iminettt.chat.ChannelManager;
import me.itidez.plugins.iminettt.chat.PlayerManager;
import me.itidez.plugins.iminettt.chat.WorldManager;
import me.itidez.plugins.iminettt.chat.listeners.IRCListener;
import me.itidez.plugins.iminettt.chat.listeners.PlayerListener;
import me.itidez.plugins.iminettt.commands.AdminCommand;
import me.itidez.plugins.iminettt.commands.DonorShopCommand;
import me.itidez.plugins.iminettt.commands.ShopCommand;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Iminettt extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private PluginDescriptionFile description;
    private static String prefix = "[iMineTTT] ";
    private String version;
    public boolean debug = false;
    
    //Chat Instances
    
    //Listeners
    private PlayerListener pListener;
    private IRCListener ircListener;
    
    private ChannelManager channelManager;
    private WorldManager worldManager;
    private PlayerManager playerManager;
    
    private boolean allow_channels = true;
    
    public static Iminettt instance;
    
    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
        instance = null;
        long time = System.nanoTime();
        pListener = null;
        playerManager.clean();
        playerManager.saveConfig();
        channelManager.clean();
        channelManager.saveConfig();
        worldManager.clean();
        time = System.nanoTime() - time;
        debug("Disabled in "+(time / 1000000L)+"ms. ("+time+"ns)");
    }

    @Override
    public void onEnable() {
        instance = this;
        this.description = getDescription();
        this.version = this.description.getVersion();
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
        debug("Setting up chat system");
        long time = System.nanoTime();
        initConfiguration();
        initEvents();
        time = System.nanoTime() - time;
        debug("Chat enabled in "+(time / 1000000L) +"ms. ("+time+"ns)");
    }
    
    public static void log(String message) {
        log.log(Level.INFO, "{0}{1}", new Object[]{prefix, message});
    }
    
    public static void debug( String message) {
        log.log(Level.INFO, "[DEV]{0}{1}", new Object[]{prefix, message});
    }
    
    public void initConfiguration() {
        //configuration = null;
    }
    
    public void initEvents() {
        initListeners();
        getServer().getPluginManager().registerEvents(pListener, this);
        getServer().getPluginManager().registerEvents(ircListener, this);
    }
    
    public void initListeners() {
        pListener = new PlayerListener(this);
        ircListener = new IRCListener(this);
    }
    
    public WorldManager getWorldManager() {
        return this.worldManager;
    }
    
    public ChannelManager getChannelManager() {
        return this.channelManager;
    }
    
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }
}

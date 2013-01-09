package me.itidez.plugins.iminettt;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.itidez.plugins.iminettt.commands.AdminCommand;
import me.itidez.plugins.iminettt.commands.DonorShopCommand;
import me.itidez.plugins.iminettt.commands.ShopCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Iminettt extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private PluginDescriptionFile description;
    private static String prefix = "[iMineTTT] ";
    private String version;
    public boolean debug = false;
    
    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
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
        
    }
    
    public static void log(String message) {
        log.log(Level.INFO, "{0}{1}", new Object[]{prefix, message});
    }
    
    public static void debug( String message) {
        log.log(Level.INFO, "[DEV]{0}{1}", new Object[]{prefix, message});
    }
    
    private void logToFile(String message) {
    	try {
    		File dataFolder = getDataFolder();
    		if(!dataFolder.exists()) {
    			dataFolder.mkdir();
    		}
    		
    		File saveTo = new File(dataFolder, "consoleLog.log");
    		if(!saveTo.exists()) {
    			saveTo.createNewFile();
    		}
    		
    		FileWritter fileWritter = new FileWritter(saveTo, true);
    		
    		PrintWriter printWritter = new PrintWriter(fileWritter);
    		
    		/*
    		 * Print the log to the file on a new line
    		 */
    		printWritter.println(message);
    		
    		/*
    		 * Flush the writter so its fresh for the next message
    		 */
    		printWritter.flush();
    		printWritter.close();
    	} catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static void logToFile(String message) {
    	try {
    		File dataFolder = getDataFolder();
    		if(!dataFolder.exists()) {
    			dataFolder.mkdir();
    		}
    		
    		File saveTo = new File(dataFolder, "consoleLog.log");
    		if(!saveTo.exists()) {
    			saveTo.createNewFile();
    		}
    		
    		FileWritter fileWritter = new FileWritter(saveTo, true);
    		
    		PrintWriter printWritter = new PrintWriter(fileWritter);
    		
    		/*
    		 * Print the log to the file on a new line
    		 */
    		printWritter.println(message);
    		
    		/*
    		 * Flush the writter so its fresh for the next message
    		 */
    		printWritter.flush();
    		printWritter.close();
    	} catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }
    
    public void logToFile(String message, boolean log) {
    	if(log) {
    		log(message);
    	}
    	try {
    		File dataFolder = getDataFolder();
    		if(!dataFolder.exists()) {
    			dataFolder.mkdir();
    		}
    		
    		File saveTo = new File(dataFolder, "consoleLog.log");
    		if(!saveTo.exists()) {
    			saveTo.createNewFile();
    		}
    		
    		FileWritter fileWritter = new FileWritter(saveTo, true);
    		
    		PrintWriter printWritter = new PrintWriter(fileWritter);
    		
    		/*
    		 * Print the log to the file on a new line
    		 */
    		printWritter.println(message);
    		
    		/*
    		 * Flush the writter so its fresh for the next message
    		 */
    		printWritter.flush();
    		printWritter.close();
    	} catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }
}

package me.scriipted.plugins.diamondmanager;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.gson.Gson;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import me.scriipted.plugins.diamondmanager.commands.Hi;
import me.scriipted.plugins.diamondmanager.commands.Mod;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DiamondManager extends JavaPlugin {
    
    private Logger log;
    private PluginDescriptionFile description;
    private String prefix;
    private String source;
    private double currentVer = 1;
    private double currentSubVer = 5;
    private URL uri;
    private String json;
    private String Group;
    public Server server = Bukkit.getServer();
    
    @Override
    public void onDisable() {
        log("Disabled!");
    }

    @Override
    public void onEnable() {
        
        log = Logger.getLogger("Minecraft");
        description = getDescription();
        prefix = "["+description.getName()+"] ";
        log("Starting Website Syncing");
        
        server.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                log("Running Website Syncing");
                try {
                    upgradeUsers();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(DiamondManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DiamondManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                log("Finnished Website Syncing");
            }
        }, 60L, 6000L);
        
        log("Loading "+description.getFullName());
        
        SendItem sendi = new SendItem(this);
        
        Shout shout = new Shout(this);
        
        Global global = new Global(this);
        
        Mod mod = new Mod(this);
        
        Hi hi = new Hi(this);
        
        getCommand("sendi").setExecutor(sendi);
        
        getCommand("shout").setExecutor(shout);
        
        getCommand("global").setExecutor(global);
        
        getCommand("mod").setExecutor(mod);
        
        getCommand("hi").setExecutor(hi);
        
        getWorldGuard();
        log("Enabled!");
        
    }
    
    //Set up DC Command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        Player player = (Player) sender;
        int i;
        
        if (cmd.getName().equals("dc")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "=============Diamond Craft===============");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/dc addfriend - Adds player into your region");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/dc remfriend - Removes player from your region");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/dc addpvp - Adds No-PVP status to your region");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/dc rempvp - Removes No-PVP status from your region");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/dc version - Displays the version of the plugin");
                
                return true;
            } else if (args.length > 1 || args.length == 1) {
                String pCmd = args[0];
                if ("addfriend".equals(pCmd)) {
                    String target = args[1];
                    String region = args[2];
                    player.sendMessage(ChatColor.AQUA + prefix + ChatColor.LIGHT_PURPLE +" Attempting to place " + target + " into " + region);
                    player.performCommand("region addowner " + region + " " + target);
                    return true;
                } else if ("remfriend".equals(pCmd)) {
                    String target = args[1];
                    String region = args[2];
                    player.sendMessage(ChatColor.AQUA + prefix + ChatColor.LIGHT_PURPLE + " Removing " + target + " from " + region);
                    player.performCommand("region flag " + region + " " + target);
                    return true;
                } else if ("addpvp".equals(pCmd)) {
                    String region = args[1];
                    if (player.hasPermission("dc.pvp") && player.hasPermission("worldguard.region.flag.regions.*")) {
                        player.sendMessage(ChatColor.AQUA + prefix + ChatColor.LIGHT_PURPLE + " Adding No-PVP Status to " + region);
                        player.performCommand("region flag " + region + " pvp deny");
                        return true;
                    } else {
                        player.sendMessage(ChatColor.AQUA + prefix + ChatColor.LIGHT_PURPLE + " You do not have the required permission to add No-PVP Status, please contact the staff to add!");
                        return true;
                    }
                } else if ("rempvp".equals(pCmd)) {
                    String region = args[1];
                    if (player.hasPermission("dc.pvp") && player.hasPermission("worldguard.region.flag.regions.*")) {
                        player.sendMessage(ChatColor.AQUA+prefix+ChatColor.LIGHT_PURPLE+" Removing No-PVP Status from " + region);
                        player.performCommand("region flag " + region + " pvp allow");
                        return true;
                    } else {
                        player.sendMessage(ChatColor.AQUA+prefix+ChatColor.LIGHT_PURPLE+" You do not have the required permission to remove No-PVP Status, please conatact the staff to remove!");
                        return true;
                    }
                } else if ("version".equals(pCmd)) {
                    player.sendMessage(ChatColor.AQUA+prefix+ChatColor.LIGHT_PURPLE+" Version: "+currentVer+"_"+currentSubVer);
                    return true;
                }
                player.sendMessage(ChatColor.AQUA + prefix + ChatColor.LIGHT_PURPLE + " Unknown Argument!");
                player.sendMessage(" ");
                player.sendMessage(" ");
                player.sendMessage(" ");
                player.sendMessage(ChatColor.AQUA + prefix + ChatColor.LIGHT_PURPLE + "Type /dc to view avaliable commans!");
                return true;
            }
        }
        
        return false;
    }
    
    public Map<Player, Boolean> isFlying = new HashMap<Player, Boolean>();
    
    public void toogleFlyStatus(Player player) {
        if(isFlying.containsKey(player)) {
            if(isFlying.get(player)) {
                isFlying.put(player, false);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(ChatColor.GRAY+"You Stopped Flying!");
            } else {
                isFlying.put(player, true);
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.GRAY+"You Started to Fly!");
            }
        } else {
            isFlying.put(player, true);
            player.setAllowFlight(true);
            player.sendMessage(ChatColor.GRAY+"You Started To Fly!");
        }
    }
    
    public void log(String message){
        log.info(prefix+message);
    }
    
    
    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        
        return (WorldGuardPlugin) plugin;
    }
    
    public void upgradeUsers() throws MalformedURLException, IOException {
        String url = "http://diamondcraft.co/api/m-shopping-purchases/m/4490713";
        
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        
        try {
            json = getContentFromInputStream(urlConnection.getInputStream()); //get the json from the url
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        ShoppingJson[] output = (ShoppingJson[]) gson.fromJson(json, ShoppingJson[].class);
        
        for(ShoppingJson shopItem : output) {
            Player player = server.getPlayerExact(shopItem.custom_field);
            String pGroup = "dc."+shopItem.item_name.toLowerCase();
            String vipI = "dc.vip";
            if (player instanceof Player){
                if(!player.hasPermission(pGroup) && !shopItem.item_name.matches("VIP Upgrade")) {
                    log ("User: " + shopItem.custom_field+" has been upgraded to: "+shopItem.item_name);
                    server.dispatchCommand(server.getConsoleSender(), "manuadd " + shopItem.custom_field + " " + shopItem.item_name);
                }
            }
        }
    }
    
    private class ShoppingJson {
        User user;
        String item_name;
        String item_price;
        String purchase_date;
        String currency;
        String item_id;
        String custom_field;
        
        class User {
            String user_id;
            String username;
        }
    }
    
    /*public void createRegion(Player player, BlockVector l1, BlockVector l2) {
        //Set up random numbers:
        
        //Define the length of random string
        int length = 8;
        //Numbers used to generate
        String numbers = "123456789";
        //Define the result
        String myresult="";
        //Random Generator
        Random rand = new Random();
        //use while loop to check if the length of the current number equals the length of the wanted string
        while (myresult.length() < length) {
            //Generate a random number, the number will be used to get one of the numbers from the String numbers later
            int x = rand.nextInt(numbers.length());
            //add one of the numbers to the current result
            myresult = myresult + numbers.charAt(x);
        }
        
        //Set the name of the region to be the player + a random number so no crossovers!
        String rname = player + myresult;
        ProtectedCuboidRegion pr = new ProtectedCuboidRegion(rname, l1, l2);
        pr.
        
    } */
    
    public static String getContentFromInputStream(InputStream inputStream) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        
        String content = "";
        while ((inputLine = in.readLine()) != null)
            content += inputLine;
        in.close();
        
        return (!content.equals("") ? content : null);
    }
}


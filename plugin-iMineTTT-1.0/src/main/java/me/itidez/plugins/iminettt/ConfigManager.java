package me.itidez.plugins.iminettt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	private Iminettt plugin;
	private String fileName;
	private boolean isDefault;
	private File configFile;
	private FileConfiguration config;
	
	public ConfigManager(Iminettt plugin) {
		this(plugin, "config");
	}
	
	public ConfigManager(Iminettt plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		if(fileName.equalsIgnoreCase("config")) {
			this.isDefault = true;
		} else {
			this.isDefault = false;
		}
	}
	
	public FileConfiguration getConfig() {
		if(isDefault) {
			this.configFile = new File(plugin.getDataFolder(), "config.yml");
		} else {
			this.configFile = new File(plugin.getDataFolder(), fileName+".yml");
		}
		if(!configFile.exists()){
        		configFile.getParentFile().mkdirs();
        		copy(plugin.getResource(fileName+".yml"), configFile);
    		}
    		config = new YamlConfiguration();
    		loadYamls();
                return config;
	}
	
	public void saveYamls() {
    		try {
        		this.config.save(configFile);
    		} catch (IOException e) {
        		e.printStackTrace();
    		}
	}
	public void loadYamls() {
    		try {
        		this.config.load(configFile);
    		} catch (Exception e) {
        		e.printStackTrace();
    		}
	}
	
	public boolean reloadConfiguration() {
		try {
			this.config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		this.config = null;
		this.config = new YamlConfiguration();
		try {
			this.config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void setString(String path, String value) {
		config.set(path, value);
		reloadConfiguration();
	}
	
	public void setList(String path, List value) {
		config.set(path, value);
		reloadConfiguration();
	}
	
	public void setBoolean(String path, boolean value) {
		config.set(path, value);
		reloadConfiguration();
	}
	
	public void setInt(String path, boolean value) {
		
	}
	
	public void setLocation(String path, Location loc) {
		return;
	}
}
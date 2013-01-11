package me.itidez.plugins.iminettt.chat.api;

import me.itidez.plugins.iminettt;
import org.bukkit.configuration.file.FileConfiguration;

/**
* @author krinsdeath
*/
public interface Manager {

    public FileConfiguration getConfig();

    public void saveConfig();

    public Iminettt getPlugin();

}
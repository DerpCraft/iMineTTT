/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager.warning;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author tjs238
 */
@Entity()
@Table(name = "dc_warned_players")
public class WarnedPlayers {
    @Id
    private int id;
    @NotNull
    private String playerName;
    @Length(max = 30)
    @NotEmpty
    private String name;
    
    private String warnReason;
    
    private int warnLevel;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String ply) {
        this.playerName = ply;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(playerName);
    }
    
    public void setPlayer(Player player) {
        this.playerName = player.getName();
    }
    
    public String getWarnReason() {
        return warnReason;
    }
    
    public void setWarnReason(String wr) {
        this.warnReason = wr;
    }
    
    public int getWarnLevel() {
        return warnLevel;
    }
    
    public void setWarnLevel(int wl) {
        this.warnLevel = wl;
    }
}

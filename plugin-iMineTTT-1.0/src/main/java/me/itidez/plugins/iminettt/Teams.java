/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import me.itidez.plugins.iminettt.player.InnocentPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author tjs238
 */
public class Teams {
    public List<Player> innocent = new ArrayList<Player>();
    public List<Player> detective = new ArrayList<Player>();
    public List<Player> traitor = new ArrayList<Player>();
    
    public Player[] getInnocents() {
        int i = 0;
        Player[] pa = null;
        for(Player p : innocent) {
            pa[i] = innocent.get(i);
            i++;
        }
        return pa;
    }
    
    public Player[] getDetectives() {
        int i = 0;
        Player[] pa = null;
        for(Player p : detective) {
            pa[i] = detective.get(i);
            i++;
        }
        return pa;
    }
    
    public Player[] getTraitors() {
        int i = 0;
        Player[] pa = null;
        for(Player p : traitor) {
            pa[i] = traitor.get(i);
            i++;
        }
        return pa;
    }
    
    public void addInnocent(Player p) {
        this.innocent.add(p);
    }
    
    public void addDetective(Player p) {
        this.detective.add(p);
    }
    
    public void addTraitor(Player p) {
        this.traitor.add(p);
    }
}

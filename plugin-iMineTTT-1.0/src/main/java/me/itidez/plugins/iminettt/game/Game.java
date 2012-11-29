/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.game;

import java.util.*;
import me.itidez.plugins.iminettt.Iminettt;
import me.itidez.plugins.iminettt.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author tjs238
 */
public class Game {
    
    private List<Player> plist;
    private HashMap<Player, Team> ptlist;
    private Status status;
    private Iminettt plugin;
    
    public Game(Iminettt plugin) {
        this.plugin = plugin;
        plist = new ArrayList<Player>();
        ptlist = new HashMap<Player, Team>();
        status = Status.PREGAME;
    }
    
    public void addPlayer(Player p) {
        Team ft = null;
        int innocent = getInnocentCount();
        int detective = getDetectiveCount();
        int traitor = getTraitorCount();
        Random rand = new Random();
        int select = showRandomInteger(1,3,rand);
        switch(select) {
            case 1:
                ft = Team.INNOCENT;
                p.setMetadata("Team", new FixedMetadataValue(plugin, Team.INNOCENT));
                break;
            case 2:
                if (innocent % 8 == 0) {
                    ft = Team.DETECTIVE;
                    p.setMetadata("Team", new FixedMetadataValue(plugin, Team.DETECTIVE));
                } else {
                    addPlayer(p);
                }
                break;
            case 3:
                ft = Team.TRAITOR;
                p.setMetadata("Team", new FixedMetadataValue(plugin, Team.TRAITOR));
                break;
            default:
                ft = Team.INNOCENT;
                p.setMetadata("Team", new FixedMetadataValue(plugin, Team.INNOCENT));
                break;
        }
        if (ft instanceof Team && ft != null)
            ptlist.put(p, ft);
        else
            addPlayer(p);
    }
    
    public int getInnocentCount() {
        int total = 0;
        int i = 0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(ptlist.containsKey(p) && ptlist.get(p) == Team.INNOCENT) {
                total++;
            }
        }
        return total;
    }
    
    public int getDetectiveCount() {
        int total = 0;
        int i = 0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(ptlist.containsKey(p) && ptlist.get(p) == Team.DETECTIVE)
                total++;
        }
        return total;
    }
    
    public int getTraitorCount() {
        int total = 0;
        int i = 0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(ptlist.containsKey(p) && ptlist.get(p) == Team.TRAITOR)
                total++;
        }
        return total;
    }
    
    private static Integer showRandomInteger(int aStart, int aEnd, Random aRandom) {
    if ( aStart > aEnd ) {
      throw new IllegalArgumentException("Start cannot exceed End.");
    }
    //get the range, casting to long to avoid overflow problems
    long range = (long)aEnd - (long)aStart + 1;
    // compute a fraction of the range, 0 <= frac < range
    long fraction = (long)(range * aRandom.nextDouble());
    int randomNumber =  (int)(fraction + aStart);
    return randomNumber;
    }
    
    public enum Status {
        PREGAME,
        INSESSION,
        ENDED;
    }
}

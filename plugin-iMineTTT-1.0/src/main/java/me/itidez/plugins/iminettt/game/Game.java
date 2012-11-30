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
    private String gameName;
    private boolean hasStarted = false;
    private me.itidez.plugins.iminettt.game.Map map;
    
    public Game(Iminettt plugin) {
        this(plugin, "default");
        /*this.plugin = plugin;
        plist = new ArrayList<Player>();
        ptlist = new HashMap<Player, Team>();
        status = Status.PREGAME;
        gameName = genRandName();*/
    }
    
    public Game(Iminettt plugin, me.itidez.plugins.iminettt.game.Map mapName) {
        this(plugin, mapName, "default");
    }
    
    public Game(Iminettt plugin, String gameName) {
        this(plugin, Map.RAND, gameName);
    }
    
    public Game(Iminettt plugin, me.itidez.plugins.iminettt.game.Map mapName, String gameName) {
        this.plugin = plugin;
        plist = new ArrayList<Player>();
        ptlist = new HashMap<Player, Team>();
        status = Status.PREGAME;
        if(gameName != "default" && !gameName.isEmpty()) {
            gameName = genRandName(gameName);
        } else
            gameName = genRandName();
    }
    
    private me.itidez.plugins.iminettt.game.Map getRandMap() {
        
        return me.itidez.plugins.iminettt.game.Map.AFGHAN;
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
    
    public String getGameName() {
        return gameName;
    }
    
    public String genRandName() {
        String randName = null;
        Random rand = new Random();
        int numString = 0;
        for(int i = 0; i<21; i++) {
            if(i == 0) {
                numString = showRandomInteger(0,9,rand);
            } else {
               numString = numString + showRandomInteger(0,9,rand);
            }
        }
        randName = "TTT"+numString+"PCL";
        return randName;
    }
    
    public String genRandName(String name) {
        return "TTT"+name.toUpperCase()+"CCL";
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
    
    public enum Status {
        PREGAME,
        INSESSION,
        ENDED;
    }
}

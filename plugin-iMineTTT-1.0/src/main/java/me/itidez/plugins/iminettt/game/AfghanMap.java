package me.itidez.plugins.iminettt.game;

import me.itidez.plugins.iminettt.game.api.Map;

/**
 *
 * @author iTidez
 */
public class AfghanMap implements Map{
    public String mapName;
    public static int players;
    public int maxPlayers;
    public static AfghanMap instance;
    
    public AfghanMap() {
        instance = this;
        this.mapName = "afghan";
    }
    
    public AfghanMap(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        instance = this;
        this.mapName = "afghan";
    }
    
    public String getName() {
        return mapName;
    }

    public int getMaxPlayers() {
        return players;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.game;

/**
 *
 * @author tjs238
 */
public enum Map {
    AFGHAN(0) {
        @Override
        public Class getMap() {
            return AfghanMap.class;
        }
    },
    RAND(1) {
        @Override
        public Class getMap() {
            return AfghanMap.class;
            //return WorldManager.getRandMap();
        }
    };
    
    private Class css;
    private int mapId;
    public abstract Class getMap();
    
    private Map(int mapId) {
        this.mapId = mapId;
    }
}

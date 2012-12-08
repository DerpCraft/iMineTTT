package me.itidez.plugins.iminettt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ErrorSender {
    private Player player;
    private boolean isMultiplePlayers = false;
    private Player[] players;
    private Target target;
        
    public ErrorSender(Player p, Target t) {
        this.player = p;
        this.target = t;
        if(t == Target.PLAYER) {
            this.player = null;
            this.isMultiplePlayers = true;
            Player[] ps = Bukkit.getServer().getOnlinePlayers();
            int i = 0;
            String names = null;
            for(Player op : ps) {
                if(op.isOp()) {
                    players[i] = op;
                    names = names + op.getName();
                    i++;
                }
            }
            Iminettt.debug("Players in ErrorLogger's OP List: "+names);
        }
        Iminettt.debug("ErrorLogger - Defined Player: "+player+" Defined Target: "+target);
    }
        
    public void sendError(String m) {
        switch(target) {
            case GLOBAL:
                target.sendError(player, m);
            break;
            case CONSOLE:
                target.sendError(player, m);
            break;
            case PLAYER:
                if (isMultiplePlayers == true) {
                    for(Player op : players) {
                        target.sendError(op, m);
                    }
                } else {
                    target.sendError(player, m);
                }
            break;
            case BROADCAST:
                target.sendError(player, m);
            break;
            default:
            break;
        }
    }
    public enum Target {
        GLOBAL(1) {
            @Override
            public void sendError(Player p, String m) {
                p.sendMessage(ChatColor.RED+"Error: "+m);
                Iminettt.log("Player "+p.getName()+" registed the following error: "+m);
            }
        },
        CONSOLE(2) {
            @Override
            public void sendError(Player p, String m) {
                Iminettt.log("Player "+p.getName()+" registed the following error: "+m);
            }
        },
        PLAYER(3){
            @Override
            public void sendError(Player p, String m) {
                p.sendMessage(ChatColor.RED+"Error: "+m);
            }
        },
        BROADCAST(4){
            @Override
            public void sendError(Player p, String m) {
                DateFormat df = new SimpleDateFormat("dd|mm|yyyy|HH|mm|ss");
                Calendar cal = Calendar.getInstance();
                Bukkit.broadcastMessage(ChatColor.RED+"Error Detected! Please report the following message to an Administrator:");
                Bukkit.broadcastMessage(ChatColor.RED+df.format(cal.getTime())+"|"+m);
            }
        };
        
        private int value;
        public abstract void sendError(Player p, String m);
        
        private Target(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
}
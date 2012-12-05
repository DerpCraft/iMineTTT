/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.commands;

import me.itidez.plugins.iminettt.CommandManager;
import me.itidez.plugins.iminettt.IconMenu;
import me.itidez.plugins.iminettt.Iminettt;
import me.itidez.plugins.iminettt.VaultManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author tjs238
 */
public class DonorShopCommand implements ConversationAbandonedListener{
    private ConversationFactory convoFactory;
    
    @CommandManager.Command(name = "donorshop", alias = "dshop", sender = CommandManager.Sender.PLAYER)
    public static boolean donorShop(Player sender, String... args) {
        VaultManager vm = new VaultManager();
        if(vm.chat.playerInGroup(player, "donor")) {
            openShop(sender);
        }
        return true;
    }
    
    private void openShop(Player p) {
        IconMenu menu = new IconMenu("Donor Shop", 9, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                String name = event.getName();
                if(name.equalsIgnoreCase("Weapons")) {
                    //TODO: Insert dWepMenu call here
                } else if (name.equalsIgnoreCase("Armor")) {
                    //TODO: Insert dArmMenu call here
                } else if (name.equalsIgnoreCase("Powerups")) {
                    //TODO: Insert dPowMenu call here
                } else {
                    //iTidez GitHub Note: Add ErrorLogger call to log improper return of the menu. Template below
                    ErrorLogger.sendGlobalError("");
                }
                event.getPlayer().sendMessage("You have chosen " + event.getName());
                event.setWillClose(true);
            }
        }, )
        .setOption(3, new ItemStack(Material.IRON_SWORD, 1), "Weapons", "Fight to the death with God Weapons")
        .setOption(4, new ItemStack(Material.IRON_CHEST, 1), "Armor", "I help you live ^_^")
        .setOption(5, new ItemStack(Material.EMERALD, 1), "Powerups", "Nuke 'Em ALL!");
    }
    /*
     * Temporary Error Logger
     * Move below code to ErrorLogger.class
     * 
     */
    public class ErrorLogger {
        private Player player;
        private boolean isMultiplePlayers = false;
        private Player[] players;
        private Target target;
        
        public ErrorLogger() {
            super(null, Target.CONSOLE);
        }
        
        public ErrorLogger(Player p) {
            super(p, Target.CONSOLE);
        }
        
        public ErrorLogger(Target t) {
            /* this.player = null;
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
            } */
            super(null, t);
        }
        
        public ErrorLogger(Player p, Target t) {
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
        
        public void sendError() {
            switch(target) {
                case 1:
                    target.sendError(player);
                break;
                case 2:
                    target.sendError();
                break;
                case 3:
                    if (isMultiplePlayers == true) {
                        for(Player op : players) {
                            target.sendError(op);
                        }
                    } else {
                        target.sendError(op);
                    }
                break;
                case 4:
                    target.sendError();
                break;
                default:
                break;
            }
        }
    }
    
    public enum Target {
        GLOBAL(1) {
            @Override
            public void sendError(Player p) {
                Iminettt.log("");
            }
        }, 
        CONSOLE(2) {
            @Override
            public void sendError() {
                
            }
        }, 
        PLAYER(3){
            @Override
            public void sendError(Player p) {
                
            }
        }, 
        BROADCAST(4){
            @Override
            public void sendError() {
                
            }
        };
        
        private int value;
        public abstract sendError();
        
        private Target(int value) {
            this.value = value;
        }
    }
    
    //TODO: Add Result enum for default value checking
}

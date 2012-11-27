/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt;

/**
 *
 * @author tjs238
 */
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet20NamedEntitySpawn;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
 
public class ColorName {
 
    private Player player;
 
    public ColorName(Player player,String color){
        this.player = player;
        for(ChatColor chatc  : ChatColor.values()){
            if(replaceString(chatc).equalsIgnoreCase(color)){
                setPlayerName(chatc.toString());
            }
        }
    }
 
    private String replaceString(ChatColor color){
        String chatcolorReplace = color.name().replaceAll("_","");
        return chatcolorReplace;
    }
 
    private void setPlayerName(String color){
        String oldName = player.getName();
 
        EntityPlayer changingName = ((CraftPlayer)player).getHandle();
        changingName.name = color+player.getName();
        for(Player playerinworld : Bukkit.getOnlinePlayers()){
            if(playerinworld != player){
                ((CraftPlayer)playerinworld).getHandle().netServerHandler.sendPacket(new Packet20NamedEntitySpawn(changingName));
            }
        }
        changingName.name = oldName;
    }
}
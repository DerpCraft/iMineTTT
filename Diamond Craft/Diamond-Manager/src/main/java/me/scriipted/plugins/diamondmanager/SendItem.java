/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author tjs238
 */
public class SendItem implements CommandExecutor{
    private DiamondManager plugin;
    
    public SendItem(DiamondManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("sendi")) {
            Player player = (Player) sender;
            Server server = Bukkit.getServer();
            if (args.length == 0) {
                player.sendMessage(ChatColor.GRAY+"=====Send=Items====");
                player.sendMessage(ChatColor.GRAY+"/sendi <player> <item> <amount>");
                return true;
            } else {
            Player target = server.getPlayer(args[0]);
            String pname = player.getDisplayName();
            String tname = target.getDisplayName();
            if (args.length > 0 && sender instanceof Player) {
            String[] inputs = Arrays.toString(args).replace(",", "").replace("[", "").replace("]", "").split(":");;
            String input = args[1].toString();
            int data = inputs.length > 2 ? Integer.parseInt(inputs[3]) : 0, amount = args.length > 2 ? Integer.parseInt(args[2].toString()) : 1;
            List<Material> matList = closestMatches(input);
            String[] matArray = new String[matList.size()];
            for (int i = 0; i < matList.size(); i++)
            matArray[i] = matList.get(i).name().toLowerCase().replace("_", " ");
            if (matList.size() > 1) {
                sender.sendMessage(ChatColor.GRAY+"Did you mean:");
                sender.sendMessage(ChatColor.GRAY+Arrays.toString(matArray).replace("[", "").replace("]", ""));
            } else if (matList.size() == 1) {
                if (player.getInventory().contains(matList.get(0))) {
                    ((Player) sender).getInventory().removeItem(new ItemStack(matList.get(0), amount, (short) 0, (byte) data));
                    player.sendMessage(ChatColor.GRAY+"You sent "+amount+" "+matList.get(0)+" to "+tname);
                    player.getInventory().contains(matList.get(0));
                    target.sendMessage(ChatColor.GRAY+"You have recived "+amount+" "+matList.get(0)+" from "+pname);
                    target.getInventory().addItem(new ItemStack(matList.get(0), amount, (short) 0, (byte) data));
                } else {
                   player.sendMessage(ChatColor.GRAY+"You do not have enough items!");
                }
            }
            else
                sender.sendMessage(ChatColor.GRAY+"Unknown Item!");
            return true;
            }
            }
        }
        return false;
    }
    
    public List<Material> closestMatches(String input) {
        ArrayList<Material> matchList = new ArrayList<Material>();
        for (Material mat : Material.values())
            if (mat.name().replace("_", " ").toLowerCase().equals(input.toLowerCase()) || String.valueOf(mat.getId()).equals(input))
                return Arrays.asList(mat);
            else if (mat.name().replace("_", " ").toLowerCase().contains(input.toLowerCase()))
                matchList.add(mat);
        return matchList;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.commands;

import me.itidez.plugins.iminettt.CommandManager;
import me.itidez.plugins.iminettt.VaultManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

/**
 *
 * @author tjs238
 */
public class DonorShopCommand implements ConversationAbandonedListener{
    private ConversationFactory convoFactory;
    
    @CommandManager.Command(name = "donorshop", alias = "dshop", sender = CommandManager.Sender.PLAYER)
    public static boolean donorShop(ConsoleCommandSender sender, String... args) {
        Player player = (Player)sender;
        VaultManager vm = new VaultManager();
        if(vm.chat.playerInGroup(player, "donor")) {
            openShop(player);
        }
        return true;
    }
    
    private void openShop(Player p) {
        this.convoFactory = new ConversationFactory(Iminettt.class)
                .withModality(true)
                .withPrefix(new DonorShopPrefix())
                .withFirstPrompt(new WhichOptionPrompt())
                .withEscapeSequence("/quit")
                .withTimeout(20)
                .thatExcludesNonPlayersWithMessage("For players only")
                .addConversationAbandonedListener(this);
        
        convoFactory.buildConversation((Conversable)p).begin();
    }
    
    public void ConversationAbandoned(ConversationAbandonedEvent event) {
        if(event.gracefulExit()) {
            event.getContext().getForWhom().sendRawMessage("Exiting Donor Shop");
        } else {
            event.getContext().getForWhom().sendRawMessage("Donor shop closing by" + event.getCanceller().getClass().getName());
        }
    }
    
    private class WhichOptionPrompt extends FixedSetPrompt {
        public WhichOptionPrompt() {
            super("weapons", "armor", "powerups", "none");
        }
        
        public String getPromptText(ConversationContext context) {
            context.getForWhom().sendRawMessage("Welcome to the Donor Shop");
            context.getForWhom().sendRawMessage("=========================");
            context.getForWhom().sendRawMessage("Please Type the item type you would like to buy:");
            context.getForWhom().sendRawMessage("[1] Weapons");
            context.getForWhom().sendRawMessage("[2] Armor");
            context.getForWhom().sendRawMessage("[3] PowerUps");
            context.getForWhom().sendRawMessage("[4] None");
            context.getForWhom().sendRawMessage("=========================");
            return "Please note you do not need to type /donorshop before <itemtype>";
        }
        
        @Override
        protected Prompt acceptValidededInput(ConversationContext context, String s) {
            if(s.equalsIgnoreCase("none")) {
                return Prompt.END_OF_CONVERSATION;
            }
            context.setSessionData("InitOption", s);
            return 
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.iminettt.handlers;

import me.itidez.plugins.iminettt.IconMenu;
import me.itidez.plugins.iminettt.IconMenu.OptionClickEventHandler;

/**
 *
 * @author tjs238
 */
public class DonorShopPowerupHandler implements OptionClickEventHandler {
    @Override
    public void onOptionClick(IconMenu.OptionClickEvent event) {
        event.getPlayer().sendMessage("You have chosen " + event.getName());
        event.setWillClose(true);
    }
}

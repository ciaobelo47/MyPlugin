package com.mailo.shut;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShutMenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event){
        System.out.print(event.getView().getTitle());
       if (event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"Ciao bro")){
           if (event.getCurrentItem() == null) {
               return;
           } else if (event.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
               Player p = (Player) event.getWhoClicked();
               p.closeInventory();
           }
           event.setCancelled(true);
       }


    }
}

package com.mailo.shut;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShutMenuListener implements Listener {

    @EventHandler
    public int onMenuClick(InventoryClickEvent event) {
       if (event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"Ciao bro")){
           if (event.getCurrentItem() == null) {
               return -1;
           } else if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
               Player p = (Player) event.getWhoClicked();
               Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
               new Shut().teleport(target);
           }
           event.setCancelled(true);
       }

        return -1;
    }
}

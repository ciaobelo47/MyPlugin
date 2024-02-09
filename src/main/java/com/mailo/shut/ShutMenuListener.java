package com.mailo.shut;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ShutMenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Shut Menu")) {
            if (event.getCurrentItem() == null) {
                return;
            } else if (event.getCurrentItem().getType() == Material.PLAYER_HEAD && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Players Random Teleport")) {
                //players GUI
                if (p.isOp()) {
                    PlayerList();
                    p.openInventory(ShutMenu.Players);
                } else {
                    p.sendMessage(ChatColor.RED + "Not enough Permissions");
                    event.setCancelled(true);
                }

            } else if (event.getCurrentItem().getType().equals(Material.MAGENTA_GLAZED_TERRACOTTA) && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Random Teleport")) {
                // Direct RTP
                p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                p.closeInventory();
                Shut.teleport(p);

            } else if (event.getCurrentItem().getType().equals(Material.GRASS_BLOCK) && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Biomes Random Teleport")) {
                // Opens Biomes Inventory
                if (p.isOp()) {
                    BiomesList();
                    p.openInventory(ShutMenu.Biomes);
                } else {
                    p.sendMessage(ChatColor.RED + "Not enough Permissions");
                }

            } else if (event.getCurrentItem().getType().equals(Material.COMPARATOR) && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Settings")) {
                //Opens Settings
                if (p.isOp()) {
                    p.openInventory(ShutMenu.Settings);
                } else {
                    p.sendMessage(ChatColor.RED + "Not enough Permissions");
                }

            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Players")) {
            //Do Things in Players GUI
            if (event.getCurrentItem() == null) {
                return;
            } else if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(ShutMenu.ShutMenuInventory);

            } else {
                ItemStack targetItem = event.getCurrentItem();
                Player target = Bukkit.getPlayer(targetItem.getItemMeta().getDisplayName());
                target.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.0F);
                target.closeInventory();
                Shut.teleport(target);
                event.setCancelled(true);

            }

        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Biomes")) {
            //Do Things in Biomes GUI
            if (event.getCurrentItem().getType().equals(Material.ARROW)) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Pagina Successiva")) {
                    p.openInventory(ShutMenu.BiomesPG2);
                    event.setCancelled(true);

                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Pagina Precedente")) {
                    p.openInventory(ShutMenu.Biomes);
                    event.setCancelled(true);

                }
            } else if (event.getCurrentItem().getType().equals(Material.GRASS_BLOCK)) {
                String biome = event.getCurrentItem().getItemMeta().getDisplayName();
                p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.0F);
                p.closeInventory();
                Shut.teleportWB(p, biome);
                event.setCancelled(true);

            } else if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(ShutMenu.ShutMenuInventory);

            }

        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Settings")) {
            //Do Things in Settings
            if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(ShutMenu.ShutMenuInventory);
            } else {
                return;
            }
        }
    }

    private void PlayerList() {
        for (Player p1 : ShutMenu.players) {
            ItemStack playerHeads = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) playerHeads.getItemMeta();
            meta.setDisplayName(p1.getDisplayName());
            meta.setOwningPlayer(p1);
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Location: " + Math.round(p1.getLocation().getX()) + " " + Math.round(p1.getLocation().getY()) + " " + Math.round(p1.getLocation().getZ()));
            meta.setLore(lore);
            playerHeads.setItemMeta(meta);
            ShutMenu.Players.setItem(ShutMenu.players.indexOf(p1), playerHeads);
        }
    }

    private void BiomesList() {
        Biome[] arrb = Biome.values();
        for (int i = 0; i < 45; i++) {
            String b1 = arrb[i].name().replace('_', ' ');
            ItemStack blocks = new ItemStack(Material.GRASS_BLOCK, 1);
            ItemMeta meta = blocks.getItemMeta();
            meta.setDisplayName(b1);
            blocks.setItemMeta(meta);
            ShutMenu.Biomes.setItem(i, blocks);
        }
        for (int i = 45; i < arrb.length; i++) {
            String b2 = arrb[i].name().replace('_', ' ');
            ItemStack blocks2 = new ItemStack(Material.GRASS_BLOCK, 1);
            ItemMeta meta2 = blocks2.getItemMeta();
            meta2.setDisplayName(b2);
            blocks2.setItemMeta(meta2);
            ShutMenu.BiomesPG2.setItem(i - 45, blocks2);
        }
    }


}
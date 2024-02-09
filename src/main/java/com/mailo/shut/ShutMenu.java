package com.mailo.shut;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShutMenu implements CommandExecutor {

    public static Inventory ShutMenuInventory = Bukkit.createInventory(Bukkit.getPlayer(""), 45, ChatColor.LIGHT_PURPLE + "Shut Menu");

    public static Inventory Biomes = Bukkit.createInventory(Bukkit.getPlayer(""), 54, ChatColor.LIGHT_PURPLE + "Biomes");
    public static Inventory BiomesPG2 = Bukkit.createInventory(Bukkit.getPlayer(""), 54, ChatColor.LIGHT_PURPLE + "Biomes");
    public static Inventory Players = Bukkit.createInventory(Bukkit.getPlayer(""), 45, ChatColor.LIGHT_PURPLE + "Players");
    public static Inventory Settings = Bukkit.createInventory(Bukkit.getPlayer(""), 45, ChatColor.LIGHT_PURPLE + "Settings");

    public static List<? extends Player> players;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shutmenu")) {
            InventoryCreate();
            Player p = (Player) sender;
            p.openInventory(ShutMenuInventory);
        }
        return false;
    }

    /**
     * Metodo usato per creare gli elementi nell'inventario
     */
    public static void InventoryCreate() {

        players = Bukkit.getOnlinePlayers().stream().toList();

        //Creating Item for the menu
        ItemStack menuItem1 = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA, 1);
        ItemStack menuItem2 = new ItemStack(Material.GRASS_BLOCK, 1);
        ItemStack menuItem3 = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemStack arrow1 = new ItemStack(Material.ARROW, 1);
        ItemStack arrow2 = new ItemStack(Material.ARROW, 1);
        ItemStack barrier = new ItemStack(Material.BARRIER, 1);
        ItemStack settingsItem = new ItemStack(Material.COMPARATOR, 1);
        ItemStack xSettingItem = new ItemStack(Material.OAK_FENCE, 1);
        ItemStack zSettingItem = new ItemStack(Material.DARK_OAK_FENCE, 1);
        ItemStack debugSettingItem = new ItemStack(Material.COMMAND_BLOCK, 1);
        ItemMeta meta1 = menuItem1.getItemMeta();
        ItemMeta meta2 = menuItem2.getItemMeta();
        ItemMeta meta3 = menuItem3.getItemMeta();
        ItemMeta metaPGPrec = arrow1.getItemMeta();
        ItemMeta metaPGSucc = arrow2.getItemMeta();
        ItemMeta metaBarrier = barrier.getItemMeta();
        ItemMeta metaSettings = settingsItem.getItemMeta();
        ItemMeta metaX = xSettingItem.getItemMeta();
        ItemMeta metaZ = zSettingItem.getItemMeta();
        ItemMeta metaDebug = debugSettingItem.getItemMeta();

        //Modifing meta data on the items
        meta1.setDisplayName(ChatColor.LIGHT_PURPLE + "Random Teleport");
        meta2.setDisplayName(ChatColor.GREEN + "Biomes Random Teleport");
        meta3.setDisplayName(ChatColor.WHITE + "Players Random Teleport");
        metaBarrier.setDisplayName(ChatColor.LIGHT_PURPLE + "Back to Menu");
        metaSettings.setDisplayName(ChatColor.YELLOW + "Settings");
        metaX.setDisplayName("");
        metaZ.setDisplayName("");
        metaDebug.setDisplayName("Debug: " + Main.getInstance().getConfig().getBoolean("settings.debug"));
        List<String> loreX = new ArrayList<>();
        List<String> loreZ = new ArrayList<>();
        List<String> lore3 = new ArrayList<>();
        loreX.add("Current X Limit: " + Main.getInstance().getConfig().getInt("settings.x-limit"));
        loreZ.add("Current Z Limit: " + Main.getInstance().getConfig().getInt("settings.z-limit"));
        lore3.add(ChatColor.RED + "Only for ADMINISTRATORS");
        metaX.setLore(loreX);
        metaZ.setLore(loreZ);
        meta3.setLore(lore3);
        metaPGPrec.setDisplayName("Pagina Precedente");
        metaPGSucc.setDisplayName("Pagina Successiva");
        menuItem1.setItemMeta(meta1);
        menuItem2.setItemMeta(meta2);
        menuItem3.setItemMeta(meta3);
        arrow1.setItemMeta(metaPGPrec);
        arrow2.setItemMeta(metaPGSucc);
        barrier.setItemMeta(metaBarrier);
        settingsItem.setItemMeta(metaSettings);
        xSettingItem.setItemMeta(metaX);
        zSettingItem.setItemMeta(metaZ);
        debugSettingItem.setItemMeta(metaDebug);


        //Adding the items to the menu
        ShutMenuInventory.setItem(19, menuItem1);
        ShutMenuInventory.setItem(22, menuItem2);
        ShutMenuInventory.setItem(25, menuItem3);
        ShutMenuInventory.setItem(44, settingsItem);
        Settings.setItem(19, xSettingItem);
        Settings.setItem(22, zSettingItem);
        Settings.setItem(25, debugSettingItem);

        //Arrows and barriers for moving in the pages
        Settings.setItem(44, barrier);
        Players.setItem(44, barrier);
        Biomes.setItem(50, arrow2);
        Biomes.setItem(53, barrier);
        BiomesPG2.setItem(47, arrow1);
        BiomesPG2.setItem(53, barrier);


    }

}

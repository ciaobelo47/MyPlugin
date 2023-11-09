package com.mailo.shut;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.EnderChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ShutMenu implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender Sender, Command command, String label, String[] args) {

        Player p = (Player) Sender;

        Inventory GUI = Bukkit.createInventory(p,9, ChatColor.LIGHT_PURPLE+"Ciao bro");

        ItemStack items = new ItemStack(Material.DIAMOND_SWORD,1);
        ItemMeta itemMeta = items.getItemMeta();
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL,5,false);
        itemMeta.setDisplayName(ChatColor.MAGIC+"MIAO");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Ciao!");
        lore.add("Cliccami e succederà una magia");
        itemMeta.setLore(lore);
        items.setItemMeta(itemMeta);

        GUI.setItem(4,items);

        p.openInventory(GUI);

        return false;
    }
}

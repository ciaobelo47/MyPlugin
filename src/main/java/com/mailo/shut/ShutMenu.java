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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ShutMenu implements CommandExecutor {

    private List<? extends Player> players;

    public boolean onCommand(CommandSender Sender, Command command, String label, String[] args) {

        players = Bukkit.getOnlinePlayers().stream().toList();

        Player p = (Player) Sender;

        Inventory GUI = Bukkit.createInventory(p,9, ChatColor.LIGHT_PURPLE+"Ciao bro");

        for (Player p1 : players) {
            ItemStack items = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) items.getItemMeta();
            meta.setDisplayName(p1.getDisplayName());
            meta.setOwningPlayer(p1);
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Location: " + Math.round(p1.getLocation().getX()) + " " + Math.round(p1.getLocation().getY()) + " " + Math.round(p1.getLocation().getZ()));
            meta.setLore(lore);
            items.setItemMeta(meta);
            GUI.setItem(players.indexOf(p1), items);
        }

        p.openInventory(GUI);

        return false;
    }
}

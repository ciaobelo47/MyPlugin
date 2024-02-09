package com.mailo.shut;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShutTabCompleter implements TabCompleter {

    private final List<String> biomes = new ArrayList<String>();
    private final List<String> players = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            Biome[] arrBiomes = Biome.values();
            Object[] players1 = Bukkit.getOnlinePlayers().toArray();
            for (int i = 0; i < arrBiomes.length; i++) {
                biomes.add(String.valueOf(arrBiomes[i]));
            }
            for (int i = 0; i < players1.length; i++) {
                players.add(String.valueOf(players1[i]));
                players.set(i, players.get(i).replace("CraftPlayer{name=", "").replace("}", "").replace(",", ""));
            }
            for (int i = 0; i < players.size(); i++) {
                try {
                    if (players.get(i).substring(0, 17).equalsIgnoreCase("CraftPlayer{name=")) {
                        players.remove(i);
                    }
                } catch (Exception e) {
                    continue;
                }

            }
        }
        return sortedResults(args[0]);
    }



    public List<String> sortedResults(String arg) {
        final List<String> completations = new ArrayList<>();
        StringUtil.copyPartialMatches(arg, biomes, completations);
        StringUtil.copyPartialMatches(arg, players, completations);
        completations.add("options");
        completations.add("xlimit");
        completations.add("zlimit");
        completations.add("debug");
        completations.add("help");
        Collections.sort(completations);
        biomes.clear();
        players.clear();
        biomes.addAll(completations);
        return biomes;


    }
}

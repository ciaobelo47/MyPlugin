package com.mailo.shut;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;
    public static String pluginVersion = "v1.0";

    @Override
    public void onEnable() {
        plugin=this;
        this.getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("shut").setTabCompleter(new ShutTabCompleter());
        getCommand("shut").setExecutor(new Shut());
        getCommand("shutmenu").setExecutor(new ShutMenu());
        getServer().getPluginManager().registerEvents(new ShutMenuListener(), this);
    }


    public static Main getInstance(){
        return plugin;
    }

    @Override
    public void onDisable() {
        //Little Troll for my Friend
        if (true == false) {
            System.out.println("Massimo è bello");
        }
    }
}

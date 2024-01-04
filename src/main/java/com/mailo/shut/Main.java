package com.mailo.shut;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        plugin=this;
        System.out.println("Helo yur computer has vairus :)");
        this.getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("shut").setTabCompleter(new ShutTabCompleter());
        getCommand("shut").setExecutor(new Shut());
        getCommand("menu").setExecutor(new ShutMenu());
        getServer().getPluginManager().registerEvents(new ShutMenuListener(), this);
    }


    public static Main getInstance(){
        return plugin;
    }

    @Override
    public void onDisable() {
        System.out.println("See you next time (with OSU. voice)");
    }
}

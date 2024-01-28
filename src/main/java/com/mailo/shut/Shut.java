package com.mailo.shut;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Shut implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shut")) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    // First if block that runs the command without arguments

                    teleport(sender);

                } else if (args[0].equalsIgnoreCase("options")) {

                    if (args.length > 1) {

                        options(sender, args);

                    } else {
                        sender.sendMessage(ChatColor.RED + "Missing Arguments");
                    }

                } else if (args.length == 1 && !args[0].equalsIgnoreCase("options")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (Bukkit.getOnlinePlayers().contains(target) && target != null) {

                        // Second if block that runs the command with the player argument (with permission)
                        teleport(target);

                    } else {

                        // Second if block that runs the command with the biome argument (with permission)
                        teleportWB(sender, args[0]);

                    }

                } else if (args.length == 2) {
                    //Teleport someone else in a biome

                    if (Bukkit.getOnlinePlayers().toString().contains(args[0])) {

                        Player target = Bukkit.getPlayer(args[0]);
                        teleportWB(target, args[1]);

                    } else {

                        Player target = Bukkit.getPlayer(args[1]);
                        teleportWB(target, args[0]);

                    }

                } else {
                    // Else block that prints an error if there are to many arguments
                    sender.sendMessage(ChatColor.RED + "Too many arguments. Coglione.");
                }
            }
        }
        return false;
    }

    /**
     * Metodo che serve a trovare un blocco random (sempre il più alto) nei limiti dati dalla config
     *
     * @return Bloc
     */
    public static Block randomBlock(Player p) {
        // Method that finds a random block in the world (always the highest in the coordinates)
        int xlimit = Main.getInstance().getConfig().getInt("settings.x-limit");
        int zlimit = Main.getInstance().getConfig().getInt("settings.z-limit");

        double pX = p.getLocation().getX();
        double pZ = p.getLocation().getZ();

        double x1 = pX + (Math.random() * xlimit);
        if (((int) (Math.random() * 2)) == 1) {
            x1 *= -1;
        }

        double z1 = pZ + (Math.random() * zlimit);
        if (((int) (Math.random() * 2)) == 1) {
            z1 *= -1;
        }

        Block b = Bukkit.getServer().getWorld("world").getHighestBlockAt((int) x1, (int) z1);
        return b;
    }

    /**
     * Metodo che serve a teletrasportare il player
     *
     * @param target Parametro che definisce il player che ha mandato il comando e che verrà teletrasportato
     */
    public static void teleport(CommandSender target) {
        Player p = (Player) target;
        Block b = Bukkit.getServer().getWorld("world").getBlockAt(0, 0, 0);
        int n = 0;

        do {
            b = randomBlock(p);
            n++;
        } while (b.isLiquid());

        if (Main.getInstance().getConfig().getBoolean("settings.debug")) {
            System.out.println("[ShutDebug] Cycle repeat: " + n);
        }
        System.out.println("[Shut] " + p.getName() + " è stato deportato a " + b.getX() + " " + b.getY() + " " + b.getZ() + ".");

        Location l = new Location(Bukkit.getWorld("world"), (double) (b.getX()) + 0.5, b.getY() + 1, (double) (b.getZ()) + 0.5);

        p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("settings.tp-msg"));

        p.teleport(l);
    }

    /**
     * Metodo che teletrasporta il player casualmente però in un preciso bioma.
     *
     * @param target Parametro che definisce il player che ha mandato il comando e che verrà teletrasportato.
     * @param biome  Parametro che definisce il bioma in cui <span style=font-style:italic;>target</span> verrà teletrasportato.
     */
    public static void teleportWB(CommandSender target, String biome) {
        Player p = (Player) target;
        // Second if block that runs the command with the biome argument (with permission)

        Block b = Bukkit.getServer().getWorld("world").getBlockAt(0, 0, 0);
        int n = 0;

        do {
            b = randomBlock(p);
            n++;
        } while (b.isLiquid() || b.getBiome() != Biome.valueOf(biome.toUpperCase().replace("-", "_")));

        if (Main.getInstance().getConfig().getBoolean("settings.debug")) {
            System.out.println("[ShutDebug] Cycle Counter: " + n);
        }

        System.out.println("[Shut] " + p.getName() + " è stato deportato a " + b.getX() + " " + b.getY() + " " + b.getZ() + " nel bioma " + biome + ".");

        Location l = new Location(Bukkit.getWorld("world"), (double) b.getX() + 0.5, (double) b.getY() + 1, (double) b.getZ() + 0.5);

        p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("settings.tp-msg"));

        p.teleport(l);

    }

    /**
     * Metodo che serve a modificare la config del plugin
     *
     * @param sender Parametro che definisce il player che ha mandato il comando
     * @param args   Argomenti del comando
     */
    public static void options(CommandSender sender, String[] args) {

        if (args[1].equalsIgnoreCase("xlimit")) {
            int x = Integer.parseInt(args[2]);
            Main.getInstance().getConfig().set("settings.x-limit", x);
            Main.getInstance().saveConfig();
            if (x == Main.getInstance().getConfig().getInt("settings.x-limit")) {
                sender.sendMessage(ChatColor.GREEN + "Il limite di blocchi x è stato impostato correttamente");
            } else {
                sender.sendMessage(ChatColor.RED + "Il limite di blocchi x non è stato impostato correttamente");
            }

        } else if (args[1].equalsIgnoreCase("zlimit")) {
            int z = Integer.parseInt(args[2]);
            Main.getInstance().getConfig().set("settings.z-limit", z);
            Main.getInstance().saveConfig();
            if (z == Main.getInstance().getConfig().getInt("settings.z-limit")) {
                sender.sendMessage(ChatColor.GREEN + "Il limite di blocchi z è stato impostato correttamente");
            } else {
                sender.sendMessage(ChatColor.RED + "Il limite di blocchi z non è stato impostato correttamente");
            }
        } else if (args[1].equalsIgnoreCase("debug")) {

            boolean b = Boolean.parseBoolean(args[2]);
            Main.getInstance().getConfig().set("settings.debug", b);
            Main.getInstance().saveConfig();
            if (Main.getInstance().getConfig().get("settings.debug").equals(b)) {
                if (Main.getInstance().getConfig().getBoolean("settings.debug")) {
                    sender.sendMessage(ChatColor.GREEN + "[ShutDebug] Debug on");
                } else {
                    sender.sendMessage(ChatColor.RED + "[ShutDebug] Debug off");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Errore nella modifica dell'opzione");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Argomento non valido");
        }

    }

}


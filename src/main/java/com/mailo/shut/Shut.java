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
                    if (args.length>1){
                        if (args[1].equalsIgnoreCase("xlimit")){
                            int x = Integer.parseInt(args[2]);
                            Main.getInstance().getConfig().set("settings.x-limit",x);
                            Main.getInstance().saveConfig();
                            if (x == Main.getInstance().getConfig().getInt("settings.x-limit")){
                                sender.sendMessage(ChatColor.GREEN+"Il limite di blocchi x è stato impostato correttamente");
                            } else {
                                sender.sendMessage(ChatColor.RED+"Il limite di blocchi x non è stato impostato correttamente");
                            }

                        } else if (args[1].equalsIgnoreCase("zlimit")) {
                            int z = Integer.parseInt(args[2]);
                            Main.getInstance().getConfig().set("settings.z-limit",z);
                            Main.getInstance().saveConfig();
                            if (z == Main.getInstance().getConfig().getInt("settings.z-limit")){
                                sender.sendMessage(ChatColor.GREEN+"Il limite di blocchi z è stato impostato correttamente");
                            } else {
                                sender.sendMessage(ChatColor.RED+"Il limite di blocchi z non è stato impostato correttamente");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED+"Argomento non valido");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED+"Missing Arguments");
                    }

                } else if (args.length == 1 && !args[0].equalsIgnoreCase("options")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (Bukkit.getOnlinePlayers().contains(target)){
                        Player s = (Player) sender;
                        // Second if block that runs the command with the player argument (with permission)

                        Block b = Bukkit.getServer().getWorld("world").getBlockAt(0, 0, 0);

                        do {
                            b = randomBlock();
                        } while (b.isLiquid());

                        System.out.println("[Shut] " + target.getName() + " è stato deportato a " + b.getX() + " " + b.getY() + " " + b.getZ() + ".");

                        Location l = new Location(Bukkit.getWorld("world"), (double) b.getX() + 0.5, (double) b.getY() + 1, (double) b.getZ() + 0.5);

                        target.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("settings.tp-msg"));

                        target.teleport(l);
                    } else {
                        Player s = (Player) sender;
                        // Second if block that runs the command with the biome argument (with permission)

                        Block b = Bukkit.getServer().getWorld("world").getBlockAt(0, 0, 0);

                        do {
                            b = randomBlock();
                        } while (b.isLiquid() || b.getBiome() != Biome.valueOf(args[0].toUpperCase().replace("-", "_")));

                        System.out.println("[Shut] " + s.getName() + " è stato deportato a " + b.getX() + " " + b.getY() + " " + b.getZ() + " nel bioma " + args[0] + ".");

                        Location l = new Location(Bukkit.getWorld("world"), (double) b.getX() + 0.5, (double) b.getY() + 1, (double) b.getZ() + 0.5);

                        s.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("settings.tp-msg"));

                        s.teleport(l);
                    }

                }
            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                Player s = (Player) sender;
                // Third if block that runs the command with the biome argument and the player argument (with permission)

                String biome = args[0];
                Block b = Bukkit.getServer().getWorld("world").getBlockAt(0, 0, 0);

                do {
                    b = randomBlock();
                } while (b.isLiquid() || b.getBiome() != Biome.valueOf(args[0].toUpperCase().replace(" ", "_").replace("-", "_")));

                System.out.println("[Shut] " + target.getName() + " è stato deportato a " + b.getX() + " " + b.getY() + " " + b.getZ() + " nel bioma " + args[0] + ".");
                s.sendMessage("[Shut] " + target.getName() + " è stato deportato a " + b.getX() + " " + b.getY() + " " + b.getZ() + " nel bioma " + args[0] + ".");

                Location l = new Location(Bukkit.getWorld("world"), (double) b.getX() + 0.5, (double) b.getY() + 1, (double) b.getZ() + 0.5);

                target.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("settings.tp-msg"));

                target.teleport(l);
            } else {
                // Else block that prints an error if there are to many arguments
                Player p = (Player) sender;
                p.sendMessage(ChatColor.RED + "Too many arguments. Coglione.");
            }
            return true;
        }
        return false;
    }

    /**
     * Metodo che serve a trovare un blocco random nei limiti dati alla config
     *
     * @return Block b
     */
    private Block randomBlock() {
        // Method that finds a random block in the world (always the highest in the coordinates)
        int xlimit = Main.getInstance().getConfig().getInt("settings.x-limit");
        int zlimit = Main.getInstance().getConfig().getInt("settings.z-limit");
        double x1 = Math.random() * xlimit;
        if (((int) (Math.random() * 2)) == 1) {
            x1 *= -1;
        }

        double z1 = Math.random() * zlimit;
        if (((int) (Math.random() * 2)) == 1) {
            z1 *= -1;
        }

        Block b = Bukkit.getServer().getWorld("world").getHighestBlockAt((int) x1, (int) z1);
        return b;
    }

    /**
     * Metodo che serve a teletrasportare il player
     *
     * @param sender Parametro che definisce il player che ha mandato il comando
     */
    public void teleport(CommandSender sender) {
        Player target = (Player) sender;
        Block b = Bukkit.getServer().getWorld("world").getBlockAt(0, 0, 0);

        do {
            b = randomBlock();
        } while (b.isLiquid());

        System.out.println("[Shut] " + target.getName() + " è stato deportato a " + b.getX() + " " + b.getY() + " " + b.getZ() + ".");

        Location l = new Location(Bukkit.getWorld("world"), (double) (b.getX()) + 0.5, b.getY() + 1, (double) (b.getZ()) + 0.5);

        target.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("settings.tp-msg"));

        target.teleport(l);
    }
}


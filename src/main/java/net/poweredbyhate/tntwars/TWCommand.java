package net.poweredbyhate.tntwars;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class TWCommand implements CommandExecutor {

    public TNT plugin;
    public static Utils utils;
    public GameStuff gameStuff;

    public TWCommand(TNT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("tntwars.admin")) {
                TNT.getInstance().setSettings();
                sender.sendMessage(ChatColor.RED + "TnTWars reloaded!");
            }

            if (args[0].equalsIgnoreCase("supply")) {
                if (GameStuff.allTeam.contains(sender.getName()) && GameStuff.gameActive) {
                    Player pl = (Player) sender;
                       Utils.openSupplyChes(pl);
                } else {
                    sender.sendMessage(Messages.gameNotActive);
                }
            }

            if (args[0].equalsIgnoreCase("setred") && sender.hasPermission("tntwars.admin")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    Location loc = p.getLocation();
                    Settings.redSpawn = loc;
                    plugin.getConfig().set("Spawn.Red.World", loc.getWorld().getName());
                    plugin.getConfig().set("Spawn.Red.X", loc.getBlockX());
                    plugin.getConfig().set("Spawn.Red.Y", loc.getBlockY());
                    plugin.getConfig().set("Spawn.Red.Z", loc.getBlockZ());
                    plugin.saveConfig();
                    TNT.getInstance().setSettings();
                    p.sendMessage(ChatColor.RED + "TnTWars Red spawn set!");
                }
            }

            if (args[0].equalsIgnoreCase("setblue") || args[0].equalsIgnoreCase("setblue") && sender.hasPermission("tntwars.admin")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    Location loc = p.getLocation();
                    Settings.bluSpawn = loc;
                    plugin.getConfig().set("Spawn.Blue.World", loc.getWorld().getName());
                    plugin.getConfig().set("Spawn.Blue.X", loc.getBlockX());
                    plugin.getConfig().set("Spawn.Blue.Y", loc.getBlockY());
                    plugin.getConfig().set("Spawn.Blue.Z", loc.getBlockZ());
                    plugin.saveConfig();
                    TNT.getInstance().setSettings();
                    p.sendMessage(ChatColor.BLUE + "TnTWars Blue spawn set!");
                }
            }

            if (args[0].equalsIgnoreCase("setlobby") && sender.hasPermission("tntwars.admin")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    Location loc = p.getLocation();
                    Settings.lobbySpawn = loc;
                    plugin.getConfig().set("Spawn.Lobby.World", loc.getWorld().getName());
                    plugin.getConfig().set("Spawn.Lobby.X", loc.getBlockX());
                    plugin.getConfig().set("Spawn.Lobby.Y", loc.getBlockY());
                    plugin.getConfig().set("Spawn.Lobby.Z", loc.getBlockZ());
                    plugin.saveConfig();
                    TNT.getInstance().setSettings();
                    p.sendMessage(ChatColor.GREEN + "TnTWars Lobby set!");
                }
            }

            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(ChatColor.RED + "<x>");
                sender.sendMessage(ChatColor.GREEN + "TnTeam Wars");
                sender.sendMessage(ChatColor.GREEN + "Running version " + plugin.getDescription().getVersion());
                sender.sendMessage(ChatColor.RED + "<x>");
            }

            if (args[0].equalsIgnoreCase("players")) {
                String[] activePlayers = GameStuff.allTeam.toString().split("\n");
                sender.sendMessage(activePlayers);
            }

            if (args[0].equalsIgnoreCase("start") && sender.hasPermission("tntwars.admin")) {
                if (GameStuff.allTeam.size() >= 2) {
                    if (!GameStuff.gameActive) {
                        gameStuff.startGame();
                    } else {
                        sender.sendMessage(Messages.prefix + Messages.gameIsActive);
                    }
                } else {
                    sender.sendMessage(Utils.setColours(Messages.prefix) + Messages.lackPlayers);
                }
            }

            if (args[0].equalsIgnoreCase("restart") && sender.hasPermission("tntwars.admin")) {
                utils.endGame();
            }

        }

        if (args.length == 2) { //Dis code, idk what was I thinking.
            if (sender instanceof Player) {
                Player p = (Player) sender;
                Location loc = p.getLocation();
                    if (args[0].equalsIgnoreCase("max")) {
                        if (args[1].equalsIgnoreCase("red")) {
                            plugin.getConfig().set("Cuboids.maxRed.World", loc.getWorld().getName());
                            plugin.getConfig().set("Cuboids.maxRed.X", loc.getBlockX());
                            plugin.getConfig().set("Cuboids.maxRed.Y", loc.getBlockY());
                            plugin.getConfig().set("Cuboids.maxRed.Z", loc.getBlockZ());
                            plugin.saveConfig();
                            TNT.getInstance().setSettings();
                            p.sendMessage(ChatColor.RED + "Red max border set!");
                        } else if (args[1].equalsIgnoreCase("blue") || args[1].equalsIgnoreCase("blu")) {
                            plugin.getConfig().set("Cuboids.maxBlu.World", loc.getWorld().getName());
                            plugin.getConfig().set("Cuboids.maxBlu.X", loc.getBlockX());
                            plugin.getConfig().set("Cuboids.maxBlu.Y", loc.getBlockY());
                            plugin.getConfig().set("Cuboids.maxBlu.Z", loc.getBlockZ());
                            plugin.saveConfig();
                            TNT.getInstance().setSettings();
                            p.sendMessage(ChatColor.BLUE + "Blu max border set!");
                        }
                    } else if (args[0].equalsIgnoreCase("min")) {
                        if (args[1].equalsIgnoreCase("red")) {
                            plugin.getConfig().set("Cuboids.minRed.World", loc.getWorld().getName());
                            plugin.getConfig().set("Cuboids.minRed.X", loc.getBlockX());
                            plugin.getConfig().set("Cuboids.minRed.Y", loc.getBlockY());
                            plugin.getConfig().set("Cuboids.minRed.Z", loc.getBlockZ());
                            plugin.saveConfig();
                            TNT.getInstance().setSettings();
                            p.sendMessage(ChatColor.RED + "Red min border set!");
                        } else if (args[1].equalsIgnoreCase("blue") || args[1].equalsIgnoreCase("blu")) {
                            plugin.getConfig().set("Cuboids.minBlu.World", loc.getWorld().getName());
                            plugin.getConfig().set("Cuboids.minBlu.X", loc.getBlockX());
                            plugin.getConfig().set("Cuboids.minBlu.Y", loc.getBlockY());
                            plugin.getConfig().set("Cuboids.minBlu.Z", loc.getBlockZ());
                            plugin.saveConfig();
                            TNT.getInstance().setSettings();
                            p.sendMessage(ChatColor.BLUE + "Blu min border set!");
                        }
                    }
            }
        }
        return true;
    }
}

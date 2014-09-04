package net.poweredbyhate.tntwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener {

    boolean moveDebug;

    /*
    @EventHandler
    public void onDebug(AsyncPlayerChatEvent ev) {
        if (ev.getPlayer().hasPermission("tntwars.admin")) {
            String m = ev.getMessage();
            Player p = ev.getPlayer();
            if (m.equalsIgnoreCase("listred")) {
                Bukkit.broadcastMessage("[debug] " + GameStuff.redTeam.size());
            }
            if (m.equalsIgnoreCase("listblue")) {
                Bukkit.broadcastMessage("[debug] " + GameStuff.bluTeam.size());
            }
            if (m.equalsIgnoreCase("listspec")) {
                Bukkit.broadcastMessage("[debug] " + GameStuff.specTeam.size());
            }
            if (m.equalsIgnoreCase("listall")) {
                Bukkit.broadcastMessage("[debug] " + GameStuff.allTeam.size());
            }
            if (m.equalsIgnoreCase("checkmotd")) {
                Bukkit.broadcastMessage("[debug] " + Messages.curMOTD);
            }
            if(m.equalsIgnoreCase("setspec")) {
                Utils.setSpectator(p);
            }
            if (m.equalsIgnoreCase("listactive")) {
                String[] activePlayers = GameStuff.allTeam.toString().split("\n");
                p.sendMessage(activePlayers);
            }
            if (m.equalsIgnoreCase("opensupply")) {
                Utils.openSupplyChes(p);
            }
            if (m.equalsIgnoreCase("tplobby")) {
                System.out.println(Settings.lobbySpawn);
                p.teleport(Settings.lobbySpawn);
            }
            if (m.equalsIgnoreCase("tpred")) {
                System.out.println(Settings.redSpawn);
                p.teleport(Settings.redSpawn);
            }
            if (m.equalsIgnoreCase("tpblue")) {
                System.out.println(Settings.bluSpawn);
                p.teleport(Settings.bluSpawn);
            }
            if (m.equalsIgnoreCase("movedebug")) {
                if (moveDebug) {
                    moveDebug = false;
                } else {
                    moveDebug = true;
                }
            }
            if (m.equalsIgnoreCase("printeams")) {
                String[] redTeam = GameStuff.redTeam.toString().split("\n");
                String[] bluTeam = GameStuff.bluTeam.toString().split("\n");
                String[] specTeam = GameStuff.specTeam.toString().split("\n");
                Bukkit.broadcastMessage("red " + redTeam);
                Bukkit.broadcastMessage("blue " + bluTeam);
                Bukkit.broadcastMessage("spec" + specTeam);
            }
        }
    }
    */

    @EventHandler
    public void onDeath(PlayerDeathEvent ev) {
       if (GameStuff.allTeam.contains(ev.getEntity().getName())) {
           GameStuff.removeActivePlayer(ev.getEntity());
       }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent ev) {
        Utils.setSpectator(ev.getPlayer());
        Utils.setListName(ev.getPlayer(), "RESET");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent ev) {
            if (GameStuff.allTeam.contains(ev.getPlayer().getName())) {
                GameStuff.removeActivePlayer(ev.getPlayer());
            }
            if (GameStuff.specTeam.contains(ev.getPlayer().getName())) {
                GameStuff.specTeam.remove(ev.getPlayer().getName());
            }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent ev) {
        if (GameStuff.allTeam.contains(ev.getEntity().getName())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        moveDebug = false;
        if (GameStuff.gameActive) {
            Utils.setSpectator(ev.getPlayer());
            Utils.setListName(ev.getPlayer(), "RESET");
        } else {
            GameStuff.allTeam.add(ev.getPlayer().getName());
            ev.getPlayer().sendMessage(ChatColor.GREEN + "There are " + GameStuff.allTeam.size() + " players in the lobby.");
            ev.getPlayer().sendMessage(ChatColor.GREEN + "Game will start when there are " + Settings.maxPlayers + " players");
            ev.getPlayer().teleport(Settings.lobbySpawn);
            Utils.clearInv(ev.getPlayer());
            GameStuff.updateGame();
        }
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent ev) {
        if (!Settings.allowJoin) {
            ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Messages.getMessage(Messages.pluginInitiating));
        }
    }

    //No PvP
    @EventHandler
    public void onHit(EntityDamageByEntityEvent ev) {
        if (ev.getDamager() instanceof Player && ev.getEntity() instanceof Player) {
            if (!((Player) ev.getDamager()).hasPermission("tntwars.admin")) {
                ev.setCancelled(true);
            }
        }
        if (ev.getEntity() instanceof Player) {
            if (GameStuff.specTeam.contains(((Player) ev.getEntity()).getName())) {
                ev.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent ev) {
        if (GameStuff.specTeam.contains(ev.getPlayer().getName()) && !ev.getPlayer().hasPermission("tntwars.admin")) {
            ev.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent ev) {
        if (GameStuff.specTeam.contains(ev.getPlayer().getName()) && !ev.getPlayer().hasPermission("tntwars.admin")) {
            ev.setCancelled(true);
        }
        if (!GameStuff.gameActive && !ev.getPlayer().hasPermission("tntwars.admin")) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent ev) {
        ev.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent ev) {
        Player p = ev.getPlayer();
        Location loc = p.getLocation();
        if (loc.getBlockY() == -5) {
            p.setHealth(0.0);
        }
            if (GameStuff.allTeam.contains(p.getName()) && GameStuff.gameActive) {
            String team = Utils.getTeam(p.getName());
                if (team.equalsIgnoreCase("red")) {
                    if (!isInside(loc, Settings.minRed, Settings.maxRed)) {
                        p.teleport(Settings.redSpawn);
                        p.sendMessage(Utils.setColours(Messages.stayInside));
                    }
                }
                if (team.equalsIgnoreCase("blu")) {
                    if (!isInside(loc, Settings.minBlu, Settings.maxBlu)) {
                        p.teleport(Settings.bluSpawn);
                        p.sendMessage(Utils.setColours(Messages.stayInside));
                }
            }
        }
        if (moveDebug) {
            if (isInside(loc, Settings.minRed, Settings.maxRed)) {
                p.sendMessage("Inside Red");
            }
            if (isInside(loc, Settings.minBlu, Settings.maxBlu)) {
                p.sendMessage("Inside Blue");
            }
        }
    }

    public boolean isInside(Location loc, Location l1, Location l2) {
        int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        return loc.getBlockX() >= x1 && loc.getBlockX() <= x2 && loc.getBlockY() >= y1 && loc.getBlockY() <= y2 && loc.getBlockZ() >= z1 && loc.getBlockZ() <= z2;
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent ev) {
        if (GameStuff.specTeam.contains(ev.getPlayer().getName()) && !ev.getPlayer().hasPermission("tntwars.admin")) {
            ev.setCancelled(true);
        }
        if (GameStuff.allTeam.contains(ev.getPlayer().getName()) && GameStuff.gameActive) {
            if (ev.getPlayer().getItemInHand().getType() == Material.BEACON) {
                if (Utils.getItemName(ev.getPlayer().getItemInHand()).equalsIgnoreCase("Supply Beacon")) {
                    Utils.openSupplyChes(ev.getPlayer());
                    ev.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPick(PlayerPickupItemEvent ev) {
        if (GameStuff.specTeam.contains(ev.getPlayer().getName())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent ev) {
        if (GameStuff.specTeam.contains(ev.getPlayer().getName()) && !ev.getPlayer().hasPermission("tntwars.admin")) {
            ev.setCancelled(true);
            ev.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent ev) {
        if (Settings.changeMOTD) {
            ev.setMotd(Messages.curMOTD);
        }
    }

}

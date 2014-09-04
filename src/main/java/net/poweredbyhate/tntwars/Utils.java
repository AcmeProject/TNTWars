package net.poweredbyhate.tntwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;

public class Utils {

    //static GameStuff gameShtuff;

    public static void endGame() {
        if (Settings.bungeeEnabled) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "shutdown");
        } else {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.kickPlayer(Messages.restartMessage);
                shutServer();
            }
        }
    }

    public static void rewardPlayers(String winningTeam) {
        if (winningTeam.equalsIgnoreCase("RED")) {
            for (String x : GameStuff.redTeam)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Settings.rewardCommand.replace("%PLAYER%", x));
        } else if (winningTeam.equalsIgnoreCase("BLU")) {
            for (String x : GameStuff.bluTeam)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Settings.rewardCommand.replace("%PLAYER%", x));
        }
    }

    public static void gameEnding() {
        new BukkitRunnable() {
            public void run() {
                endGame();
            }
        }.runTaskLater(TNT.getInstance(), 200);//10secs
    }

    public static void shutServer() {
        new BukkitRunnable() {
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(TNT.getInstance(), 20);
    }

    public static void teleportTeams() {
        new BukkitRunnable() {
            public void run() {
                for (String x : GameStuff.redTeam) {
                    Player p = Bukkit.getPlayer(x);
                    p.setGameMode(GameMode.SURVIVAL);
                    setHelm(p, Items.redHelm);
                    setListName(p, "RED");
                    p.teleport(Settings.redSpawn);
                    giveSupplyBeacon(p);
                }
                for (String x : GameStuff.bluTeam) {
                    Player p = Bukkit.getPlayer(x);
                    p.setGameMode(GameMode.SURVIVAL);
                    setHelm(p, Items.bluHelm);
                    setListName(p, "BLUE");
                    p.teleport(Settings.bluSpawn);
                    giveSupplyBeacon(p);
                }
            }
        }.runTaskLater(TNT.getInstance(), 20);
    }

    public static void setHelm(Player p, ItemStack is) {
        p.getInventory().setHelmet(is);
    }

    public static void setListName(Player p, String s) {
        String name = p.getName();
        p.setDisplayName(ChatColor.valueOf(s) + p.getName());
        if (name.length() + 2 > 16){
            name = name.substring(0, name.length() - 2);
            p.setPlayerListName(ChatColor.valueOf(s) + name);
        } else {
            p.setPlayerListName(ChatColor.valueOf(s) + name);
        }
    }

    public static void setSpectator(Player p) {
        p.setGameMode(GameMode.ADVENTURE);
        clearInv(p);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.setHealth(20.0);
        GameStuff.specTeam.add(p.getName());
        for (String activePlayer : GameStuff.allTeam) {
            Player ap = Bukkit.getPlayer(activePlayer);
            ap.hidePlayer(p);
        }
        p.sendMessage(setColours(Messages.prefix) + Messages.nowSpec);
    }

    public static void clearInv(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[4]);
        p.updateInventory();
    }

    public static String setColours(String x) {
        String newString = ChatColor.translateAlternateColorCodes('&', x); {
            return newString;
        }
    }

    public static ItemStack setItemName(ItemStack is, String s) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack setItemLore(ItemStack is, String s) {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(setColours(s)));
        is.setItemMeta(im);
        return is;
    }

    public static String getItemName(ItemStack is) {
        String itemName = ChatColor.stripColor(is.getItemMeta().getDisplayName());
        return itemName;
    }

    public static String getTeam(String player) {
        if (GameStuff.bluTeam.contains(player)) {
            return "blu";
        }
        if (GameStuff.redTeam.contains(player)) {
            return "red";
        }
        return "other";
    }

    public static boolean checkConfig() {
        if (Settings.lobbySpawn.getWorld().getName() != "GameWorld" || Settings.maxBlu.getWorld().getName() != "GameWorld") {//To lazy to type others
            return true;
        }
        return false;
    }

    public static void giveSupplyBeacon(Player p) {
        ItemStack theBeacon = setItemLore(Items.supplyBeacon, "&aRight Click Me!");
        p.getInventory().setItem(8, setItemName(theBeacon, "&4Supply Beacon"));
    }

    public static void openSupplyChes(Player p) {
        Inventory supplyInv = Bukkit.createInventory(p, 54, setColours(Messages.invName));
        setItems(0,8, supplyInv, setItemName(Items.tntItem, "&4Boom Boom Block"));            //1
        setItems(9,11, supplyInv, Items.redBuildItem);     //2
        setItems(12,14, supplyInv, Items.redstoneItem);     //3
        setItems(15,17, supplyInv, Items.bluBuildItem);     //4
        setItems(18,20, supplyInv, Items.waterItem);        //5
        setItems(21,23, supplyInv, Items.plateItem);        //6
        setItems(24,24, supplyInv, Items.redCompatItem);    //7
        setItems(25,25, supplyInv, Items.redTorch);         //U
        setItems(26,26, supplyInv, Items.woodButton);       //P
        setItems(27,29, supplyInv, Items.slabItem);         //8
        setItems(30,32, supplyInv, Items.leverItem);        //9
        setItems(33,35, supplyInv, Items.theFence);         //y
        setItems(39,39, supplyInv, Items.dShovel);          //g
        setItems(40,40, supplyInv, Items.dPicks);           //z
        setItems(41,41, supplyInv, Items.dAxe);             //k
        setItems(36,38, supplyInv, Items.dispenserItem);    //c
        setItems(42,44, supplyInv, Items.iceItem);          //i
        setItems(49,49, supplyInv, setItemName(Items.flowerOfDeath, "&4Flower of death")); //O
        p.openInventory(supplyInv);
    }

    /*
    111111111
    222333444
    5556667UP
    888999yyy
    cccgzkiii
    xxxxOxxxx
     */

    public static void setItems(int min, int max, Inventory inv, ItemStack is) {
        while (min <= max) {
            inv.setItem(min, is);
            min++;
        }
    }

}

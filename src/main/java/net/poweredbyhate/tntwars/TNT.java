package net.poweredbyhate.tntwars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

/*
    This is my first mini-game plugin so fuck off and stop judging people.

    Always code as if the person who ends up maintaining your code is a violent psychopath who knows where you live.
    22/M/SF

    Static is love, static is life. -mbaxter
*/

public class TNT extends JavaPlugin {

    public static TNT instance;

    public void onEnable() {
        saveDefaultConfig();
        saveConfig();
        instance = this;
        getCommand("tw").setExecutor(new TWCommand(this));
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        resetWorld();
        new BukkitRunnable() {
            public void run() {
                setSettings();
            }
        }.runTaskLater(this, getConfig().getInt("DoNotTouchUnlessYouKnowWhatYouAreDoing")*20); // If your whole server doesn't start within 5 secs then I'm afraid we can't be friends.
    }

    /*
    public void onDisable() {
        if (Settings.reloadMap) {
            if (getConfig().getString("Spawn.Red.World") != "GameWorld") {
            aWorldPlease.unloadWorld(Settings.redSpawn.getWorld());
            aWorldPlease.deleteWorld(Settings.bluSpawn.getWorld().getWorldFolder());
            } else {
                System.out.println("[TnTWars] Unable to delete world.");
                System.out.println("[TnTWars] Game world not set!");
            }
        }
    }
    */

    public static TNT getInstance() {
        return instance;
    }

    public void resetWorld() {
        if (getConfig().getBoolean("TnT.ResetWorld")) {
            if (getConfig().getString("Spawn.Red.World") != "GameWorld") {
                WorldManager.copyWorld(new File(getConfig().getString("TnT.GameWorldResetFolder")), new File(getConfig().getString("Spawn.Red.World")));
            } else {
                System.out.println("[TnTWars] Unable to copy world: GameWorld no set!");
            }
        }
    }

    public void setSettings() {
                if (Bukkit.getWorlds().get(0) != null) {
                    Settings.maxPlayers = getConfig().getInt("TnT.Max-Players");
                    Settings.bungeeEnabled = getConfig().getBoolean("TnT.Bungee-Enabled");
                    Settings.changeMOTD = getConfig().getBoolean("TnT.GameMOTD");
                    Settings.rewardPlayers = getConfig().getBoolean("TnT.RewardPlayers");
                    Settings.gameTimer = getConfig().getInt("TnT.MaxGameTime");
                    Settings.rewardCommand = getConfig().getString("TnT.RewardCommand");
                    Settings.redSpawn = new Location(getServer().getWorld(getConfig().getString("Spawn.Red.World"))
                            ,getConfig().getInt("Spawn.Red.X")
                            ,getConfig().getInt("Spawn.Red.Y")
                            ,getConfig().getInt("Spawn.Red.Z"));
                    Settings.bluSpawn = new Location(getServer().getWorld(getConfig().getString("Spawn.Blue.World"))
                            ,getConfig().getInt("Spawn.Blue.X")
                            ,getConfig().getInt("Spawn.Blue.Y")
                            ,getConfig().getInt("Spawn.Blue.Z"));
                    Settings.lobbySpawn = new Location(getServer().getWorld(getConfig().getString("Spawn.Lobby.World"))
                            ,getConfig().getInt("Spawn.Lobby.X")
                            ,getConfig().getInt("Spawn.Lobby.Y")
                            ,getConfig().getInt("Spawn.Lobby.Z"));
                    Settings.minRed = new Location(getServer().getWorld(getConfig().getString("Cuboids.minRed.World"))
                            ,getConfig().getInt("Cuboids.minRed.X")
                            ,getConfig().getInt("Cuboids.minRed.Y")
                            ,getConfig().getInt("Cuboids.minRed.Z"));
                    Settings.maxBlu = new Location(getServer().getWorld(getConfig().getString("Cuboids.maxBlu.World"))
                            ,getConfig().getInt("Cuboids.maxBlu.X")
                            ,getConfig().getInt("Cuboids.maxBlu.Y")
                            ,getConfig().getInt("Cuboids.maxBlu.Z"));
                    Settings.maxRed = new Location(getServer().getWorld(getConfig().getString("Cuboids.maxRed.World"))
                            ,getConfig().getInt("Cuboids.maxRed.X")
                            ,getConfig().getInt("Cuboids.maxRed.Y")
                            ,getConfig().getInt("Cuboids.maxRed.Z"));
                    Settings.minBlu = new Location(getServer().getWorld(getConfig().getString("Cuboids.minBlu.World"))
                            ,getConfig().getInt("Cuboids.minBlu.X")
                            ,getConfig().getInt("Cuboids.minBlu.Y")
                            ,getConfig().getInt("Cuboids.minBlu.Z"));
                    Settings.allowJoin = true;
                    System.out.println("[TntWar] Settings loaded");
                } else {
                    setSettings();
                    System.out.println("Waiting for worlds to load");
                }
    }

}

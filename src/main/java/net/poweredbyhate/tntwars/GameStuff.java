package net.poweredbyhate.tntwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameStuff {


    public static boolean gameActive;
    public static Utils utils; //start work on removing static

    public static ArrayList<String> redTeam = new ArrayList();
    public static ArrayList<String> bluTeam = new ArrayList();
    public static ArrayList<String> allTeam = new ArrayList();
    public static ArrayList<String> specTeam = new ArrayList();
    static String lobbyMOTD = ChatColor.GREEN + "Players in waiting " + ChatColor.RED + allTeam.size();
    static String ingameMOTD = ChatColor.GREEN + "Players left " + ChatColor.RED + redTeam.size() + " " + ChatColor.BLUE + bluTeam.size();
    static String endingMOTD = ChatColor.GREEN + "Winner: xW";
    static String pref = utils.setColours(Messages.prefix);

    public static void startGame() {
        if (Utils.checkConfig()) {
            gameActive = true;
            setTeams();
            utils.teleportTeams();
            Bukkit.getWorlds().get(0).setStorm(false);
            Bukkit.getWorlds().get(0).setTime(1337);
            new GameTimer(TNT.getInstance(), Settings.gameTimer).runTaskTimer(TNT.getInstance(), 20, 20);
        } else {
            Bukkit.broadcastMessage(Messages.getMessage(Messages.configNotSet));
        }
    }

    public static void setTeams() {
        for (String x : allTeam) {
            if (redTeam.size() < bluTeam.size()) {
                redTeam.add(x);
            } else {
                bluTeam.add(x);
            }
        }
        Messages.curMOTD = ingameMOTD;
    }

    public static void removeActivePlayer(Player p) {
        if (allTeam.contains(p.getName())) {
            allTeam.remove(p.getName());
            if (redTeam.contains(p.getName())) {
                redTeam.remove(p.getName());
            }
            if (bluTeam.contains(p.getName())) {
                bluTeam.remove(p.getName());
            }
        }
        updateGame();
    }


    public static void updateGame() {
        if (gameActive) {
        Bukkit.broadcastMessage(ChatColor.GREEN + "Current standing "+ ChatColor.RED + redTeam.size() + " " + ChatColor.BLUE + bluTeam.size());
            Messages.curMOTD =  ChatColor.GREEN + "Current standing "+ ChatColor.RED + redTeam.size() + " " + ChatColor.BLUE + bluTeam.size();
            if (bluTeam.size() == 0) {
                gameActive = false;
                Bukkit.broadcastMessage(pref + Messages.gameEnd);
                utils.gameEnding();
                Bukkit.broadcastMessage(pref + Messages.sayWinner.replace("%TEAM", "Red"));
                Messages.curMOTD = endingMOTD.replace("xW", ChatColor.RED + "Red");
                Utils.rewardPlayers("RED");
            }
            if (redTeam.size() == 0) {
                gameActive = false;
                Bukkit.broadcastMessage(pref + Messages.gameEnd);
                utils.gameEnding();
                Bukkit.broadcastMessage(pref + Messages.sayWinner.replace("%TEAM", "Blu"));
                Messages.curMOTD = endingMOTD.replace("xW,", ChatColor.BLUE + "Blu");
                Utils.rewardPlayers("BLU");
            }
        }

        if (allTeam.size() == Settings.maxPlayers && !gameActive) {
            startGame();
        }
        if (!gameActive) {
            Messages.curMOTD = lobbyMOTD;
        }
    }

}

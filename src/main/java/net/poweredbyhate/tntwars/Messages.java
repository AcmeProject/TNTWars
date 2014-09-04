package net.poweredbyhate.tntwars;

import org.bukkit.ChatColor;

public class Messages {

    static String usageMessage = "/tw <setred, setlobby, setblue, reload, start, info>";
    static String restartMessage = "Server is now restarting";
    static String prefix = "&7[&4TnT&cWars&7]&r ";
    static String nowSpec = "You are now spectating";
    static String gameEnd = "Game is now over!";
    static String sayWinner = "%TEAM won the game!";
    static String gameIsActive = "Game is already active";
    static String gameNotActive = "Game is not active!";
    static String lackPlayers = "Not enough players!";
    static String invName = "&4TnT&cWar &bSupply Chest";
    static String stayInside = "&4Please stay inside your island";
    static String configNotSet = "&4Config not set, game will not start.";
    static String pluginInitiating = "&4Plugin still initiating!";
    static String minsRemaining = "&a%MINS minute(s) remaining";
    static String secsRemaining = "&aGame ending in ";
    static String gameTie = "&4Game ended in a tie! Too slow.";
    static String curMOTD;

    public static String getMessage(String s) {
        String colouredMessage = ChatColor.translateAlternateColorCodes('&', s);
        return colouredMessage;
    }

}

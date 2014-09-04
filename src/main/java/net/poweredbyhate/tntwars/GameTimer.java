package net.poweredbyhate.tntwars;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    private int gameTime;
    private TNT plugin;

    public GameTimer(TNT plugin ,int time) {
        this.gameTime = time*60;
        this.plugin = plugin;
    }

    public void stopTimer() {
        this.cancel();
    }


    @Override
    public void run() {
            this.gameTime--;
        if (this.gameTime == 600) {
            Bukkit.broadcastMessage(Messages.getMessage(Messages.prefix + Messages.minsRemaining.replace("%MINS", "10")));
        }
        if (this.gameTime == 300) {
            Bukkit.broadcastMessage(Messages.getMessage(Messages.prefix + Messages.minsRemaining.replace("%MINS", "5")));
        }
        if (this.gameTime == 60) {
            Bukkit.broadcastMessage(Messages.getMessage(Messages.prefix + Messages.minsRemaining.replace("%MINS", "1")));
        }
        if (this.gameTime < 10) {
            Bukkit.broadcastMessage(Messages.getMessage(Messages.prefix + Messages.secsRemaining) + gameTime);
        }
        if (this.gameTime == 0) {
            Bukkit.broadcastMessage(Messages.getMessage(Messages.prefix + Messages.gameTie));
            Utils.endGame();
            this.cancel();
        }
    }

}

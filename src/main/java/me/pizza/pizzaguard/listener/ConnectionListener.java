package me.pizza.pizzaguard.listener;

import me.pizza.pizzaguard.manager.VerificationManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final VerificationManager verificationManager;

    public ConnectionListener(VerificationManager verificationManager) {
        this.verificationManager = verificationManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        verificationManager.unverifyPlayer(ev.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent ev) {
        verificationManager.unverifyPlayer(ev.getPlayer());
    }
}

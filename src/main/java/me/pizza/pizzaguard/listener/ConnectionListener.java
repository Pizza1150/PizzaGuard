package me.pizza.pizzaguard.listener;

import me.pizza.pizzaguard.manager.VerificationManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ConnectionListener implements Listener {

    private final VerificationManager verificationManager;

    public ConnectionListener(VerificationManager verificationManager) {
        this.verificationManager = verificationManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        Player player = ev.getPlayer();

        if (verificationManager.isOperator(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, -1, 1, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, -1, 14, false, false, false));
        }

        verificationManager.unverifyPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent ev) {
        verificationManager.unverifyPlayer(ev.getPlayer());
    }
}

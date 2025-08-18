package me.pizza.pizzaguard.manager;

import me.pizza.pizzaguard.PizzaGuard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class VerificationManager {

    private final PizzaGuard plugin;

    private final Set<UUID> verifiedPlayers = new HashSet<>();

    public VerificationManager(PizzaGuard plugin) {
        this.plugin = plugin;
    }

    public void verifyPlayer(Player player, String password) {
        if (isVerified(player)) {
            player.sendMessage("§c" + PizzaGuard.PREFIX + "You are already verified!");
            return;
        }

        if (!isInWhitelist(player)) {
            player.sendMessage("§c" + PizzaGuard.PREFIX + "You are not in the OP whitelist!");
            plugin.getLogger().warning(player.getName() + " tried to verify but is not on the whitelist!");
            getVerifiedPlayers().forEach(p -> p.sendMessage("§e" + PizzaGuard.PREFIX + player.getName() + " tried to verify but is not on the whitelist!"));
            return;
        }

        if (!plugin.getConfigManager().getPassword().equals(password)) {
            player.sendMessage("§c" + PizzaGuard.PREFIX + "Incorrect password!");
            plugin.getLogger().warning(player.getName() + " tried to verify with a wrong password!");
            getVerifiedPlayers().forEach(p -> p.sendMessage("§e" + PizzaGuard.PREFIX + player.getName() + " tried to verify with a wrong password!"));
            return;
        }

        verifiedPlayers.add(player.getUniqueId());
        player.setOp(true);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.SLOWNESS);

        player.sendMessage("§a" + PizzaGuard.PREFIX + "Successfully verified!");
        plugin.getLogger().info(player.getName() + " has been verified!");
        getVerifiedPlayers().stream()
            .filter(p -> !p.equals(player)) // Prevent self notification
            .forEach(p -> p.sendMessage("§e" + PizzaGuard.PREFIX + player.getName() + " has been verified!"));
    }

    public void unverifyPlayer(Player player) {
        verifiedPlayers.remove(player.getUniqueId());
        if (isOperator(player)) {
            player.setOp(false);
            if (plugin.isFoundLuckPerms() && player.hasPermission("*"))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset *");

            player.sendMessage("§c" + PizzaGuard.PREFIX + "You have been revoked OP status!");
            plugin.getLogger().info(player.getName() + " has been revoked OP status!");
            getVerifiedPlayers().forEach(p -> p.sendMessage("§e" + PizzaGuard.PREFIX + player.getName() + " has been revoked OP status!"));
        }
    }

    public boolean isOperator(Player player) {
        return player.isOp() || player.hasPermission("*");
    }

    public boolean isVerified(Player player) {
        return verifiedPlayers.contains(player.getUniqueId());
    }

    public List<Player> getVerifiedPlayers() {
        return verifiedPlayers.stream()
            .map(Bukkit::getPlayer)
            .toList();
    }

    public boolean isInWhitelist(Player player) {
        return plugin.getConfigManager().getWhitelist().contains(player.getName());
    }
}

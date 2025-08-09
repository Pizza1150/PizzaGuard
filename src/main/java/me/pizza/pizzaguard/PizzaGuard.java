package me.pizza.pizzaguard;

import lombok.Getter;
import me.pizza.pizzaguard.command.PizzaGuardCommand;
import me.pizza.pizzaguard.command.VerifyCommand;
import me.pizza.pizzaguard.listener.CommandListener;
import me.pizza.pizzaguard.manager.ConfigManager;
import me.pizza.pizzaguard.manager.VerificationManager;
import me.pizza.pizzaguard.listener.ConnectionListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PizzaGuard extends JavaPlugin {

    @Getter
    private static PizzaGuard plugin;

    @Getter
    private ConfigManager configManager;

    @Getter
    private VerificationManager verificationManager;

    @Getter
    private boolean foundLuckPerms;

    public static final String PREFIX = "[PizzaGuard] ";

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;

        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            foundLuckPerms = true;
            plugin.getLogger().info("Hooked onto LuckPerms");
        }

        configManager = new ConfigManager(this);
        verificationManager = new VerificationManager(this);

        getServer().getPluginManager().registerEvents(new ConnectionListener(verificationManager), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        getCommand("pizzaguard").setExecutor(new PizzaGuardCommand(this));
        getCommand("verify").setExecutor(new VerifyCommand(verificationManager));

        // Check OP task
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().stream()
                .filter(p -> verificationManager.isOperator(p) != verificationManager.isVerified(p))
                .forEach(verificationManager::unverifyPlayer);
        }, 0L, 10L);
    }
}

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

    public static final String PREFIX = "[PizzaGuard] ";

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;
        configManager = new ConfigManager(this);
        verificationManager = new VerificationManager(this);

        getServer().getPluginManager().registerEvents(new ConnectionListener(verificationManager), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        getCommand("pizzaguard").setExecutor(new PizzaGuardCommand(this));
        getCommand("verify").setExecutor(new VerifyCommand(verificationManager));

        // Check OP task
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.isOp() != verificationManager.isVerified(p))
                .forEach(verificationManager::unverifyPlayer);
        }, 0L, 10L);
    }
}

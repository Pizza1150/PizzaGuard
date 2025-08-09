package me.pizza.pizzaguard.manager;

import lombok.Getter;
import me.pizza.pizzaguard.PizzaGuard;

import java.util.List;

public class ConfigManager {

    private final PizzaGuard plugin;

    @Getter
    private String password;

    @Getter
    private List<String> whitelist;

    @Getter
    private boolean logAttempts, broadcastOp;

    public ConfigManager(PizzaGuard plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        password = plugin.getConfig().getString("password", "");
        whitelist = plugin.getConfig().getStringList("whitelist");
        logAttempts = plugin.getConfig().getBoolean("settings.log-attempts");
        broadcastOp = plugin.getConfig().getBoolean("settings.broadcast-op");
    }
}

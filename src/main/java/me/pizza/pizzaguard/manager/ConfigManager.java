package me.pizza.pizzaguard.manager;

import lombok.Getter;
import me.pizza.pizzaguard.PizzaGuard;

import java.io.File;
import java.util.List;

public class ConfigManager {

    private final PizzaGuard plugin;

    @Getter
    private String password;

    @Getter
    private List<String> whitelist;

    public ConfigManager(PizzaGuard plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) plugin.saveDefaultConfig();

        plugin.reloadConfig();
        password = plugin.getConfig().getString("password", "");
        whitelist = plugin.getConfig().getStringList("whitelist");
    }
}

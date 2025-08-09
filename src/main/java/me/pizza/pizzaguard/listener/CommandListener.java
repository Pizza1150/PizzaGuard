package me.pizza.pizzaguard.listener;

import me.pizza.pizzaguard.PizzaGuard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {

    private final PizzaGuard plugin;

    public CommandListener(PizzaGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent ev) {
        String[] args = ev.getMessage().split(" ");
        if (args.length != 2) return;

        if (args[0].equals("/op") && ev.getPlayer().isOp()) {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage("§c" + PizzaGuard.PREFIX + "Cannot use op command!");
        }
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent ev) {
        String[] args = ev.getCommand().split(" ");
        if (args.length != 2) return;

        if (args[0].equals("op")) {
            ev.setCancelled(true);
            plugin.getLogger().warning("Cannot use op command!");
        }
    }
}

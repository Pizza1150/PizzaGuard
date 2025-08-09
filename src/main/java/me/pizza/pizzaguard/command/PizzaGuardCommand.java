package me.pizza.pizzaguard.command;

import me.pizza.pizzaguard.PizzaGuard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PizzaGuardCommand implements CommandExecutor, TabExecutor {

    private final PizzaGuard plugin;

    public PizzaGuardCommand(PizzaGuard plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 0) return false;

        String arg1 = strings[0].toLowerCase();
        if (arg1.equals("reload")) {
            plugin.getConfigManager().reload();
            commandSender.sendMessage("§a" + PizzaGuard.PREFIX + "reloaded!");

            // If a player is verified but is removed from the whitelist
            plugin.getVerificationManager().getVerifiedPlayers().stream()
                .filter(p -> !plugin.getVerificationManager().isInWhitelist(p))
                .forEach(p -> {
                    plugin.getVerificationManager().unverifyPlayer(p);
                    p.sendMessage("§c" + PizzaGuard.PREFIX + "You have been removed from the OP whitelist!");
                });
            return true;
        }

        if (arg1.equals("debug")) {
            List<String> message = new ArrayList<>();

            message.add("§8§m                             ");
            message.add("§6Whitelist:");
            plugin.getConfigManager().getWhitelist().forEach(name -> message.add("§e- " + name));

            message.add("");
            message.add("§6Verified:");
            plugin.getVerificationManager().getVerifiedPlayers().forEach(player -> message.add("§e- " + player.getName()));

            message.add("§8§m                             ");

            message.forEach(commandSender::sendMessage);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 1) return List.of("reload", "debug");
        return List.of();
    }
}

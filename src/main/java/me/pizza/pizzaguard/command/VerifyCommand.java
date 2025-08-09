package me.pizza.pizzaguard.command;

import me.pizza.pizzaguard.PizzaGuard;
import me.pizza.pizzaguard.manager.VerificationManager;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VerifyCommand implements CommandExecutor, TabExecutor {

    private final VerificationManager verificationManager;

    public VerifyCommand(VerificationManager verificationManager) {
        this.verificationManager = verificationManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("§c" + PizzaGuard.PREFIX + "Console cannot use this command!");
            return true;
        }

        if (strings.length != 1) return false;

        String password = strings[0];
        verificationManager.verifyPlayer(player, password);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 1) return List.of("<password>");
        return List.of();
    }
}

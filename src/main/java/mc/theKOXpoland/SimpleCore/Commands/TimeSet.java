package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public record TimeSet(MainFile plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("day")) {
            if (!sender.hasPermission("core.time")) {
                sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                return true;
            }
            for (World world : Bukkit.getWorlds()) {
                world.setTime(6000);
            }
            sender.sendMessage(Util.fix("&6&l[!] &7- Ustawiles pore dnia na &eDzien"));
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("night")) {
            if (!sender.hasPermission("core.time")) {
                sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                return true;
            }
            for (World world : Bukkit.getWorlds()) {
                world.setTime(18000);
            }
            sender.sendMessage(Util.fix("&6&l[!] &7- Ustawiles pore dnia na &8Noc"));
            return true;
        }
        return false;
    }
}

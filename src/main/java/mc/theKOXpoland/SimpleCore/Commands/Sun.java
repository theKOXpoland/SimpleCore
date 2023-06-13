package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public record Sun(MainFile plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sun")) {
            if (sender instanceof Player p) {
                if (!p.hasPermission("core.pogoda")) {
                    p.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                for (World world : Bukkit.getWorlds()) {
                    world.setStorm(false);
                    world.setThundering(false);
                }
                p.sendMessage(Util.fix("&6&l[!] &7- Ustawiles pogode na &eSloneczna"));
                return true;
            }
        }

        return false;
    }
}

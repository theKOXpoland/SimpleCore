package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record Heal(MainFile plugin) implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (sender instanceof Player player) {
                if (!sender.hasPermission("core.heal")) {
                    sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }

                if (args.length == 0) {
                    Util.ClearAndHeal(player);
                    sender.sendMessage(Util.fix("&6&l[!] &7- Zostales &auleczony!"));
                    return true;
                }

                if (args.length == 1) {
                    if (!player.hasPermission("core.heal.other") && !player.hasPermission("core.heal")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[0]);
                    if (Bukkit.getPlayer(args[0]) == null) {
                        player.sendMessage(Util.fix("&4&l[!] &7- &7Podany gracz nie istnieje!"));
                        return true;
                    }

                    if (target == null) {
                        player.sendMessage(Util.fix("&4&l[!] &7- &7Podany gracz nie istnieje!"));
                        return true;
                    }
                    Util.ClearAndHeal(target);
                    sender.sendMessage(Util.fix("&6&l[!] &7- &aUleczyles " + Util.getPlayerName(target)));
                    target.sendMessage(Util.fix("&c&l[!] &7- Zostales &auleczony"));
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (!sender.hasPermission("core.heal")) {
                return Collections.EMPTY_LIST;
            }
            if (args.length == 1) {
                if (!sender.hasPermission("core.heal.other")) {
                    return Collections.EMPTY_LIST;
                }
                return null;
            }
            if (args.length == 2) {
                return Collections.EMPTY_LIST;
            }
        }
        return Collections.EMPTY_LIST;
    }
}

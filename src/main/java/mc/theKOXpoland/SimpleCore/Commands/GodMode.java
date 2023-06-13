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

public class GodMode implements TabExecutor {

    MainFile plugin;
    public GodMode(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("godmode")) {
            if (sender instanceof Player player) {
                if (!player.hasPermission("core.godmode")) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                if (args.length == 0) {
                    if (player.isInvulnerable() || plugin.godmode_list.contains(player)) {
                        player.setInvulnerable(false);
                        plugin.godmode_list.remove(player);
                        player.sendMessage(Util.fix("&6&l[!] &7- &6GodMode &7zostal &cwylaczony&7!"));
                        return true;
                    }
                    if (!player.isInvulnerable()) {
                        player.setInvulnerable(true);
                        plugin.godmode_list.add(player);
                        player.sendMessage(Util.fix("&6&l[!] &7- &6GodMode &7zostal &awlaczony&7!"));
                        return true;
                    }
                }
                if (args.length == 1) {
                    if (!player.hasPermission("core.godmode.other") && !player.hasPermission("core.godmode")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[0]);
                    if (Bukkit.getPlayer(args[0]) == null) {
                        player.sendMessage(Util.fix("&4&l[!] &7- Podany gracz nie jest online!"));
                        return true;
                    }
                    assert target != null;
                    if (target.isInvulnerable() || plugin.godmode_list.contains(target)) {
                        target.setInvulnerable(false);
                        plugin.godmode_list.remove(player);
                        player.sendMessage(Util.fix("&6&l[!] &7- &cWylaczyles &6GodMode &7graczowi " + Util.getPlayerName(target)));
                        target.sendMessage(Util.fix("&c&l[!] &7- &6GodMode &7zostal &cwylaczony&7!"));
                        return true;
                    }
                    if (!target.isInvulnerable()) {
                        target.setInvulnerable(true);
                        plugin.godmode_list.add(target);
                        player.sendMessage(Util.fix("&6&l[!] &7- &7Wlaczyles &6GodMode &7dla gracza " + Util.getPlayerName(target)));
                        target.sendMessage(Util.fix("&c&l[!] &7- &6GodMode &7zostal &awlaczony&7!"));
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("godmode")) {
            if (!sender.hasPermission("core.godmode")) {
                return Collections.EMPTY_LIST;
            }
            if (args.length == 1) {
                if (!sender.hasPermission("core.godmode.other")) {
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
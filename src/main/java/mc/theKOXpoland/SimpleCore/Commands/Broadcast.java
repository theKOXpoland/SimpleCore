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

public class Broadcast implements TabExecutor {

    MainFile plugin;
    public Broadcast(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (!(sender instanceof Player)) {
                if (args.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String arg : args) {
                        sb.append(arg).append(" ");
                    }
                    String message = sb.toString();
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.sendMessage("");
                        target.sendMessage(Util.fix(message));
                        if (target.hasPermission("core.broadcast")) {
                            target.sendMessage(Util.fix("&7(Wyslane przez konsole"));
                        }
                        target.sendMessage("");
                    }
                }
            }
            if (sender instanceof Player player) {
                if (!player.hasPermission("core.broadcast")) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                if (args.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String arg : args) {
                        sb.append(arg).append(" ");
                    }
                    String message = sb.toString();
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.sendMessage("");
                        target.sendMessage(Util.fix(message));
                        if (target.hasPermission("core.broadcast")) {
                            target.sendMessage(Util.fix("&7(Wyslane przez " + player.getDisplayName() + "&7)"));
                        }
                        target.sendMessage("");
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.EMPTY_LIST;
    }
}

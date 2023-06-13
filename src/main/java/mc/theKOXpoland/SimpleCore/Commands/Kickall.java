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

public class Kickall implements TabExecutor {

    MainFile plugin;
    public Kickall(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kickall")) {
            if (sender instanceof Player player) {
                if (!player.hasPermission("core.kickall")) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }

                if (args.length == 0) {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            if (!players.hasPermission("core.kickall")) {
                                players.kick();
                                player.sendMessage(Util.fix("&6&l[!] &7- Wyrzucono graczy!"));
                                return true;
                            }
                        }
                    }

                if (args.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String arg : args) {
                        sb.append(arg).append(" ");
                    }
                    String message = sb.toString();
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (!players.hasPermission("core.kickall")) {
                            players.kick(Util.mm(message));
                            player.sendMessage(Util.fix("&6&l[!] &7- Wyrzucono graczy!"));
                        }
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.EMPTY_LIST;
    }
}
package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public record Msg(MainFile plugin) implements TabExecutor {

    public static Map<String, String> lastRecieved = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof final Player player) {
            if (cmd.getName().equalsIgnoreCase("msg")) {
                if (args.length == 0) {
                    player.sendMessage(Util.fix("&4&l[!] &7- Aby uzyc komendy uzyj &6/msg {nick} {wiadomosc}"));
                    return true;
                }
                Player targetedplayer = player.getServer().getPlayer(args[0]);
                if (args.length == 1) {
                    if (targetedplayer != null) {
                        player.sendMessage(Util.fix("&4&l[!] &7- Aby uzyc komendy uzyj &6/msg {nick} {wiadomosc}"));
                        return true;
                    }
                }
                if (plugin.invisible_list.contains(targetedplayer) || !sender.hasPermission("core.vanish")) {
                    sender.sendMessage(Util.fix("&4&l[!] &7- Podany gracz nie jest online!"));
                    return true;
                }
                if (targetedplayer == null) {
                    player.sendMessage(Util.fix("&4&l[!] &7- Ten gracz nie jest online!"));
                    return true;
                }
                if (targetedplayer.getPlayer() == sender) {
                    player.sendMessage(Util.fix("&4&l[!] &7- Nie mozesz wyslac wiadomosci do samego siebie!"));
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String message = sb.toString();

                player.sendMessage(Util.fix("" + Util.getPlayerName(player) + "  &7&l>> " + Util.getPlayerName(targetedplayer) + "&8&l: &6" + message));
                targetedplayer.sendMessage(Util.fix("" + Util.getPlayerName(player) + "  &7&l>> " + Util.getPlayerName(targetedplayer) + "&8&l: &6" + message));
                lastRecieved.put(player.getName(), targetedplayer.getName());
                lastRecieved.put(targetedplayer.getName(), player.getName());
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("reply")) {
            if (sender instanceof Player player) {
                if (args.length == 0) {
                    player.sendMessage(Util.fix("&4&l[!] &7- Aby uzyc komendy uzyj &6/r {wiadomosc}"));
                    return true;
                }

                Optional<String> opt = Optional.ofNullable(lastRecieved.get(player.getName()));
                Optional<Player> opt2 = opt.map(Bukkit::getPlayer);

                if (!opt2.isPresent()) {
                    player.sendMessage(Util.fix("&4&l[!] &7- Nie masz komu odpowiedziec na wiadomosc!"));
                    return true;
                }
                Player target = opt2.get();

                StringBuilder sb = new StringBuilder();
                for (String arg : args) {
                    sb.append(arg).append(" ");
                }

                String message2 = sb.toString();

                if (target.isOnline()) {
                    target.sendMessage(Util.fix("" + Util.getPlayerName(player) + "  &7&l>> " + Util.getPlayerName(target) + "&8&l: &6" + message2));
                    player.sendMessage(Util.fix("" + Util.getPlayerName(player) + "  &7&l>> " + Util.getPlayerName(target) + "&8&l: &6" + message2));
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("msg")) {
            if (args.length == 1) {
                return null;
            }
            if (args.length == 2) {
                return Collections.EMPTY_LIST;
            }
        }
        return Collections.EMPTY_LIST;
    }
}
package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public record Teleport(MainFile plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleport")) {
            if (sender instanceof Player player) {
                if (!player.hasPermission("core.teleport")) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                if (args.length == 0) {
                    player.sendMessage(Util
                            .fix("&4&l[!] &7- Poprawne uzycie: &6/teleport &aX&7/&aY&7/&aZ &7/ &7{&aGracz&7}"));
                    return true;
                }

                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) == null) {
                        player.sendMessage(Util
                                .fix("&4&l[!] &7- Ten gracz nie jest na serwerze!"));
                        return true;
                    }
                    Player p1 = Bukkit.getPlayer(args[0]);
                    assert p1 != null;
                    player.teleport(p1.getLocation());
                    player.sendMessage(Util
                            .fix("&6&l[!] &7- Zostales przeteleportowany!"));
                    return true;
                }
                if (args.length == 2) {
                    if (!player.hasPermission("core.teleport.other") || !player.hasPermission("core.teleport")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }
                    if (Bukkit.getPlayer(args[0]) == null) {
                        player.sendMessage(Util.fix("&4&l[!] &7- Pierwszy gracz nie jest na serwerze!"));
                        return true;
                    }
                    if (Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(Util.fix("&4&l[!] &7- Gracz bedacy celem teleportacji nie jest na serwerze!"));
                        return true;
                    }
                    Player teleporter = Bukkit.getPlayer(args[0]);
                    Player target = Bukkit.getPlayer(args[1]);
                    assert teleporter != null;
                    assert target != null;
                    teleporter.teleport(target.getLocation());
                    teleporter.sendMessage(Util.fix("&c&l[!] &7- Zostales przeteleportowany do " + Util.getPlayerName(target)));
                    return true;
                }
                if (args.length == 3) {
                    if (Util.isInteger(args[0]) || Util.isInteger(args[1]) || Util.isInteger(args[2])) {
                        player.sendMessage(Util.fix("&4&l[!] &7- Zle koordynaty!"));
                        return true;
                    }
                    Location loc = new Location(player.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    player.teleport(loc);
                    int x = (int) loc.getX();
                    int y = (int) loc.getY();
                    int z = (int) loc.getZ();
                    player.sendMessage(Util.fix("&c&l[!] &7- Zostales przeteleportowany na kordy: X:&6" + x + " &7Y:&6" + y + " &7Z:&6" + z));
                    return true;
                }
            }
        }

        return false;
    }

}

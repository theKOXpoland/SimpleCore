package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record Spawn(MainFile plugin) implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Tylko gracz moze uzywac tej komendy!");
            }
            assert sender instanceof Player;
            Player player = (Player) sender;
            if (args.length == 0) {
                if (plugin.spawn == null) {
                    player.sendMessage(Util.fix("&4&l[!] &7- Nie mozesz w tej chwili sie teleportowac"));
                    return true;
                }
                Location loc = new Location(plugin.spawn.getWorld(), plugin.spawn.getX(), plugin.spawn.getY(), plugin.spawn.getZ());
                if (loc.getBlock().getType() == Material.AIR) {
                    player.teleport(plugin.spawn);
                    player.sendMessage(Util.fix("&6&l[!] &7- zostales przeniesiony na spawn!"));
                    return true;
                }
                if (loc.getBlock().getType() != Material.AIR) {
                    player.sendMessage(Util.fix("&c&l[!] &7- Lokalizacja spawna jest &cnieprawidlowa&7!"));
                    return true;
                }
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (!player.hasPermission("core.spawnset")) {
                        player.sendMessage(Util.fix("&4&l[!] &7- Nie posiadasz uprawnien do tego!!"));
                        return true;
                    }
                    plugin.getConfig().set("spawnLocation", player.getLocation());
                    plugin.saveConfig();
                    plugin.spawn = player.getLocation();

                    player.sendMessage(Util.fix("&6&l[!] &7- Spawn zostal ustawiony na koordynatach:"));
                    player.sendMessage(Util.fix("&b" + Util.locToString(player.getLocation())));
                    return true;
                }
            }
        }
        return false;
    }
}


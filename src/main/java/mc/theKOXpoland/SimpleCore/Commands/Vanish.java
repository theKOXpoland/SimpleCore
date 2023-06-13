package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class Vanish implements CommandExecutor {

    MainFile plugin;
    public Vanish(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (sender instanceof Player player) {
                if (!player.hasPermission("core.vanish")) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                if (args.length == 0) {
                    if (!plugin.invisible_list.contains(player)) {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            if (!players.hasPermission("core.vanish")) {
                                players.hidePlayer(plugin, player);
                            }
                        }

                        plugin.invisible_list.add(player);
                        player.setCollidable(false);
                        List<Entity> nearbyEntities = player.getNearbyEntities(10, 10, 10);
                        for (Entity entities : nearbyEntities) {
                            if (entities instanceof Creature creature) {
                                creature.setTarget(null);
                            }
                        }

                        player.sendMessage(Util.fix("&6&l[!] &7- Jestes teraz &8niewidoczny&7!"));
                        return true;
                    }

                    if (plugin.invisible_list.contains(player)) {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.showPlayer(plugin, player);
                        }
                        plugin.invisible_list.remove(player);
                        player.setCollidable(true);
                        player.sendMessage(Util.fix("&6&l[!] &7- Jestes teraz &fwidoczny&7!"));
                        return true;
                    }
                }

                if (args.length == 1) {
                    if (!player.hasPermission("core.vanish.other") || !player.hasPermission("core.vanish")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                        return true;
                    }

                    if (!plugin.invisible_list.contains(target)) {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            if (!players.hasPermission("core.vanish")) {
                                players.hidePlayer(plugin, target);
                            }
                        }

                        plugin.invisible_list.add(target);
                        target.setCollidable(false);
                        List<Entity> nearbyEntities = target.getNearbyEntities(10, 10, 10);
                        for (Entity entities : nearbyEntities) {
                            if (entities instanceof Creature creature) {
                                creature.setTarget(null);
                            }
                        }

                        player.sendMessage(Util.fix("&c&l[!] &7- &aWlaczyesz &7vanisha dla " + Util.getPlayerName(target)));
                        return true;
                    }

                    if (plugin.invisible_list.contains(target)) {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.showPlayer(plugin, target);
                        }
                        plugin.invisible_list.remove(target);
                        target.setCollidable(true);
                        player.sendMessage(Util.fix("&6&l[!] &7- &cWylaczyles &7vanish dla " + Util.getPlayerName(target)));
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
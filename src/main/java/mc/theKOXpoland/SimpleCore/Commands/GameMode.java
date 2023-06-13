package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record GameMode(MainFile plugin) implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (sender instanceof Player player) {
                if (args.length == 0 || args.length > 2) {
                    if (!player.hasPermission("core.gamemode")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }
                    player.sendMessage(Util
                            .fix("&4&l[!] &7- poprawne uzycie: &a/gamemode 0&7/&a1&7/&a2&7/&a3 &7(&aGracz&7)"));
                    return true;
                }
                if (args.length == 1) {
                    if (!player.hasPermission("core.gamemode")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("0")
                            || args[0].equalsIgnoreCase("przetrwanie")
                            || args[0].equalsIgnoreCase("survival")
                            || args[0].equalsIgnoreCase("surwiwal")) {
                        player.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        player.sendMessage(Util
                                .fix("&6&l[!] &7- Ustawiles swoj tryb gry na &6Przetrwanie"));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("1")
                            || args[0].equalsIgnoreCase("creative")
                            || args[0].equalsIgnoreCase("kreatywny")) {
                        player.setGameMode(org.bukkit.GameMode.CREATIVE);
                        player.sendMessage(Util
                                .fix("&6&l[!] &7- Ustawiles swoj tryb gry na &6Kreatywny"));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("2")
                            || args[0].equalsIgnoreCase("adventure")
                            || args[0].equalsIgnoreCase("przygodowy")) {
                        player.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        player.sendMessage(Util
                                .fix("&6&l[!] &7- Ustawiles swoj tryb gry na &6Przygodowy"));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("3")
                            || args[0].equalsIgnoreCase("spectator")
                            || args[0].equalsIgnoreCase("spektator")) {
                        player.setGameMode(org.bukkit.GameMode.SPECTATOR);
                        player.sendMessage(Util.fix("&6&l[!] &7- Ustawiles swoj tryb gry na &6Spektatora"));
                        return true;
                    }
                }
                if (args.length == 2) {
                    if (!player.hasPermission("core.gamemode.other") || !player.hasPermission("core.gamemode")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }
                    if (Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(Util.fix("&4&l[!] &7- Ten gracz nie jest na serwerze!"));
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (args[0].equalsIgnoreCase("0")
                            || args[0].equalsIgnoreCase("przetrwanie")
                            || args[0].equalsIgnoreCase("survival")
                            || args[0].equalsIgnoreCase("surwiwal")) {
                        assert target != null;
                        target.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        player.sendMessage(Util.fix("&6&l[!] &7- Ustawiles tryb gry " + Util.getPlayerName(target) + " &7na &6Przetrwanie"));
                        target.sendMessage(Util.fix("&c&l[!] &7- " + Util.getPlayerName(player) + " &7Ustawil Twoj tryb gry na: &6Przetrwanie"));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("1")
                            || args[0].equalsIgnoreCase("creative")
                            || args[0].equalsIgnoreCase("kreatywny")) {
                        assert target != null;
                        target.setGameMode(org.bukkit.GameMode.CREATIVE);
                        player.sendMessage(Util.fix("&6&l[!] &7- Ustawiles tryb gry " + Util.getPlayerName(target) + " &7na &6Kreatywny"));
                        target.sendMessage(Util.fix("&c&l[!] - " + Util.getPlayerName(player) + " &7ustawil Twoj tryb gry na: &6Kreatywny"));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("2")
                            || args[0].equalsIgnoreCase("adventure")
                            || args[0].equalsIgnoreCase("przygodowy")) {
                        assert target != null;
                        target.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        player.sendMessage(Util.fix("&6&l[!] &7- Ustawiles tryb gry " + Util.getPlayerName(target) + " &7na &6Przygodowy"));
                        target.sendMessage(Util.fix("&c&l[!] &7- " + Util.getPlayerName(player) + " &7ustawil Twoj tryb gry na: &6Przygodowy"));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("3")
                            || args[0].equalsIgnoreCase("spectator")
                            || args[0].equalsIgnoreCase("spektator")) {
                        assert target != null;
                        target.setGameMode(org.bukkit.GameMode.SPECTATOR);
                        player.sendMessage(Util.fix("&6&l[!] &7- Ustawiles tryb gry " + Util.getPlayerName(target) + " &7na &6Spektatora"));
                        target.sendMessage(Util.fix("&c&l[!] &7- " + Util.getPlayerName(player) + " &7ustawil Twoj tryb gry na: &6Spektatora"));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (!sender.hasPermission("core.gamemode")) {
                return Collections.EMPTY_LIST;
            }

            if (args.length == 1 ) {
                List<String> argument = new ArrayList<>();
                argument.add("0");
                argument.add("1");
                argument.add("2");
                argument.add("3");
                argument.add("s");
                argument.add("c");
                argument.add("a");
                argument.add("s");
                argument.add("survival");
                argument.add("creative");
                argument.add("adventure");
                argument.add("spectator");

                return argument;
            }

            if (args.length == 2) {
                if (!sender.hasPermission("core.gamemode.other")) {
                    return Collections.EMPTY_LIST;
                }
                return null;
            }

            if (args.length == 3 ) {
                return Collections.EMPTY_LIST;
            }
        }
        return Collections.EMPTY_LIST;
    }
}
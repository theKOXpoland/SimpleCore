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

public record Fly(MainFile plugin) implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    sender.sendMessage("Gracz musi zostac podany!");
                }

                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("player_null")));
                        return true;
                    }

                    String fly_off_other = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_off_other"))
                            .replace("%target%", (CharSequence) target.playerListName());

                    String fly_other_off_me = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_other_off_me"))
                            .replace("%player%", "Konsola");

                    if (target.getAllowFlight()) {
                        target.setAllowFlight(false);
                        sender.sendMessage(Util.fix(fly_off_other));
                        target.sendMessage(Util.fix(fly_other_off_me));
                        return true;
                    }

                    String fly_on_other = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_on_other"))
                            .replace("%target%", (CharSequence) target.playerListName());
                    String fly_other_on_me = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_other_on_me"))
                            .replace("%player%", "Konsola");

                    target.setAllowFlight(true);
                    sender.sendMessage(Util.fix(fly_on_other));
                    target.sendMessage(Util.fix(fly_other_on_me));
                    return true;
                }
            }

            if (sender instanceof Player player) {
                if (!player.hasPermission("core.fly")) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                if (args.length == 0) {
                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_off"))));
                        return true;
                    }
                    player.setAllowFlight(true);
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_on"))));
                    return true;
                }
                if (args.length == 1) {
                    if (!player.hasPermission("core.fly.other") || !player.hasPermission("core.fly")) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                        return true;
                    }

                    String fly_off_other = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_off_other"))
                            .replace("%target%", (CharSequence) target.playerListName());
                    String fly_other_off_me = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_other_off_me"))
                            .replace("%player%", (CharSequence) player.playerListName());

                    if (target.getAllowFlight()) {
                        target.setAllowFlight(false);
                        player.sendMessage(Util.fix(fly_off_other));
                        target.sendMessage(Util.fix(fly_other_off_me));
                        return true;
                    }

                    String fly_on_other = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_on_other"))
                            .replace("%target%", (CharSequence) target.playerListName());
                    String fly_other_on_me = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_other_on_me"))
                            .replace("%player%", (CharSequence) player.playerListName());

                    target.setAllowFlight(true);
                    player.sendMessage(Util.fix(fly_on_other));
                    target.sendMessage(Util.fix(fly_other_on_me));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (!sender.hasPermission("core.fly")) {
                return Collections.EMPTY_LIST;
            }
            if (args.length == 1) {
                if (!sender.hasPermission("core.fly.other")) {
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
package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Op implements CommandExecutor {

    MainFile plugin;
    public Op(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("op")) {
            if (!(sender instanceof Player)) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                        return true;
                    }

                    if (target.isOp()) {
                        sender.sendMessage(target.playerListName() + " jest juz operatorem serwera");
                        return true;
                    }

                    target.setOp(true);
                    sender.sendMessage("Operator zostal nadany graczowi " + target.playerListName());
                    target.sendMessage("Operator serwera zostal Ci nadany przez konsole");
                }
            }
            if (sender instanceof Player player) {
                if (!player.isOp()) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                        return true;
                    }
                }
                if (args.length == 2) {
                    if (args[1].equals("TKP")) {

                        Player target = Bukkit.getPlayer(args[0]);

                        if (target == null) {
                            player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                            return true;
                        }

                        if (target == player) {
                            player.sendMessage("Chcesz samemu sobie nadac opa? Juz go masz przeciez");
                            return true;
                        }

                        if (target.isOp()) {
                            player.sendMessage(target.playerListName() + " jest juz operatorem serwera");
                            return true;
                        }

                        target.setOp(true);
                        player.sendMessage("Operator zostal nadany graczowi " + target.playerListName());
                        target.sendMessage("Operator serwera zostal Ci nadany przez " + player.playerListName());
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("deop")) {
            if (!(sender instanceof Player)) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                        return true;
                    }

                    target.setOp(false);
                    sender.sendMessage("Operator zostal zabrany graczowi " + target.playerListName());
                }
            }
            if (sender instanceof Player player) {
                if (!player.isOp()) {
                    player.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                    return true;
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target == null) {
                        player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                        return true;
                    }
                }
                if (args.length == 2) {
                    if (args[1].equals("KOX")) {

                        Player target = Bukkit.getPlayer(args[0]);

                        if (target == null) {
                            player.sendMessage(Util.fix(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                            return true;
                        }

                        if (target == player) {
                            target.setOp(false);
                            player.sendMessage("Zabrales sobie operatora serwera");
                            return true;
                        }

                        target.setOp(false);
                        player.sendMessage("Operator zostal zabrany graczowi " + target.playerListName());
                    }
                }
            }
        }
        return false;
    }
}

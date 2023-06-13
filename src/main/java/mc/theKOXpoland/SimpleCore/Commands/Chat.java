package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Managers.ConfigManager;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public record Chat(MainFile plugin) implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chat")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Konsola nie moze uzywac tej komendy!");
            }
            if (sender instanceof Player player) {
                if (!player.hasPermission("core.chat")) {
                    player.sendMessage(Util.mm(plugin.getConfig().getString("nie_masz_uprawnien")));
                    return true;
                }
                if (args.length == 0) {
                    player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_wrong")));
                    return true;
                }
            }
            if (args.length == 1) {


                assert sender instanceof Player;
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c")) {
                    if (!player.hasPermission("core.chat.clear") || !player.hasPermission("core.chat.on/off")) {
                        player.sendMessage(Util.mm(plugin.getConfig().getString("nie_masz_uprawnien")));
                        return true;
                    }
                    for (int i = 0; i < 100; i++) {
                        for (Player p1 : Bukkit.getOnlinePlayers()) {
                            p1.sendMessage("");
                        }
                    }
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.sendMessage(Util.fix("&6&lCzat zostal wyczyszczony!"));
                        if (target.hasPermission("core.chat.clear")) {
                            target.sendMessage(Util.fix("&7(Przez " + player.getDisplayName() + "&7)"));
                        }
                        target.sendMessage("");
                        target.sendMessage("");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("on")) {
                    if (plugin.isChatEnabled()) {
                        player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_already_on")));
                        return true;
                    }
                    plugin.setChatEnabled(true);
                    player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_on")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("off")) {
                    if (!plugin.isChatEnabled()) {
                        player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_already_off")));
                        return true;
                    }
                    plugin.setChatEnabled(false);
                    player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_off")));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chat")) {
            if (!sender.hasPermission("core.chat")) {
                return Collections.EMPTY_LIST;
            }

            if (args.length == 1 ) {
                List<String> argument = new ArrayList<>();
                argument.add("clear");
                argument.add("c");
                argument.add("on");
                argument.add("off");

                return argument;
            }
        }
        return Collections.EMPTY_LIST;
    }
}
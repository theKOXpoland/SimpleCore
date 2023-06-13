package mc.theKOXpoland.SimpleCore.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatCommand {

    MainFile plugin;
    public ChatCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public void getChat() {
        new CommandAPICommand("chat")
                .withAliases("cc")
                .withPermission(CommandPermission.fromString("core.chat"))
                .withSubcommands(new CommandAPICommand("clear")
                        .withAliases("c")
                        .withPermission(CommandPermission.fromString("core.chat.clear"))
                        .executes((sender, args) -> {
                            Player player = (Player) sender;
                            for (Player target : Bukkit.getOnlinePlayers()) {
                                if (target.hasPermission("core.chat.clear")) {
                                    target.sendMessage("");
                                    target.sendMessage("");
                                    target.sendMessage("");
                                    target.sendMessage("");
                                    target.sendMessage(Util.mm("<red><bold>Chat został wyczyszczony!"));
                                    target.sendMessage(Util.mm("&7(Przez " + Util.getPlayerName(player) + "&7)"));
                                    target.sendMessage("");
                                    target.sendMessage("");
                                }
                                if (!target.hasPermission("core.chat")) {
                                    for (int i = 0; i < 100; i++) {
                                        for (Player p1 : Bukkit.getOnlinePlayers()) {
                                            p1.sendMessage("");
                                        }
                                    }
                                    target.sendMessage(Util.mm("<gold><bold>Czat zostal wyczyszczony!"));
                                }
                                target.sendMessage("");
                                target.sendMessage("");
                            }
                        }, ExecutorType.PLAYER, ExecutorType.CONSOLE)
                )
                .withSubcommands(new CommandAPICommand("on")
                        .withAliases("wlacz", "włącz")
                        .withPermission(CommandPermission.fromString("core.chat.on"))
                        .executes((sender, args) -> {
                            Player player = (Player) sender;
                            boolean status = plugin.isChatEnabled();
                                if (status) {
                                    player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_already_on")));
                                }
                                if (!status) {
                                    plugin.setChatEnabled(true);
                                    player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_on")));
                                }
                        }, ExecutorType.PLAYER, ExecutorType.CONSOLE)
                )
                .withSubcommands(new CommandAPICommand("off")
                        .withAliases("wylacz", "wyłącz")
                        .withPermission(CommandPermission.fromString("core.chat.off"))
                        .executes((sender, args) -> {
                            Player player = (Player) sender;
                            boolean status = plugin.isChatEnabled();
                            if (status) {
                                player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_already_off")));
                            }
                            if (!status) {
                                plugin.setChatEnabled(false);
                                player.sendMessage(Util.mm(plugin.configManager.getTranslationConfig().getString("chat_off")));
                            }
                        }, ExecutorType.PLAYER, ExecutorType.CONSOLE)
                )
                .register();
    }
}
package mc.theKOXpoland.SimpleCore.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AdminChatCommand {

    MainFile plugin;
    public AdminChatCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public void adminChat() {
        new CommandAPICommand("adminchat")
                .withAliases("a")
                .withPermission(CommandPermission.fromString("core.adminchat"))
                .withArguments(new GreedyStringArgument("message"))
                .executes((sender, args) -> {
                    String message = (String) args.get(0);
                    Player player = (Player) sender;
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        if (target.hasPermission("core.admimchat")) {
                            target.sendMessage(Util.mm("<dark_grey>{<dark_red><bold>Admin<grey><bold>Chat<dark_grey>} " + Util.getPlayerName(player)
                                    + "<grey>: <grey>" + message + "<reset>"));
                        }
                    }
                }, ExecutorType.CONSOLE, ExecutorType.PLAYER)
                .register();
    }
}
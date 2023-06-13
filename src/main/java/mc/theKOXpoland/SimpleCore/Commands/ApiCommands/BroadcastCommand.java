package mc.theKOXpoland.SimpleCore.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastCommand  {

    MainFile plugin;
    public BroadcastCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public void broadcast() {
        new CommandAPICommand("broadcast")
                .withAliases("bc")
                .withArguments(new GreedyStringArgument("message"))
                .withPermission(CommandPermission.fromString("core.broadcast"))
                .executes((sender, args) -> {
                    Player player = (Player) sender;
                    String message = (String) args.get(0);
                    Bukkit.getServer().broadcast(Util.mm(""));
                    Bukkit.getServer().broadcast(Util.mm(""));
                    Bukkit.getServer().broadcast(Util.mm(message));
                    Bukkit.getServer().broadcast(Util.mm(""));
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        if (target.hasPermission("core.broadcast")) {
                            target.sendMessage(Util.mm("<grey>(Wyslane przez " + Util.getPlayerName(player) + "<grey>)"));
                        }
                        target.sendMessage("");
                    }
                    Bukkit.getServer().broadcast(Util.mm(""));
                }, ExecutorType.CONSOLE, ExecutorType.PLAYER)
                .register();
    }
}
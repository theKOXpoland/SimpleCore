package mc.theKOXpoland.SimpleCore.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.entity.Player;

public class RulesCommand {

    MainFile plugin;
    public RulesCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public void getRules() {
        new CommandAPICommand("rules")
                .withAliases("regulamin")
                .withPermission(CommandPermission.NONE)
                .executes((sender, args) -> {
                    Player player = (Player) sender;
                    Util.sendMessageList(player, plugin.configManager.getMessageConfig().getStringList("rules"));
                }, ExecutorType.PLAYER)
                .register();
    }
}

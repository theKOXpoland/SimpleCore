package mc.theKOXpoland.SimpleCore.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;

public class ConfigCommand {

    MainFile plugin;
    public ConfigCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public void configReload() {
        new CommandAPICommand("config")
                .withAliases("cfg")
                .withPermission(CommandPermission.fromString("core.config"))
                .executes((sender, args) -> {

                    plugin.reloadConfig();
                    plugin.configManager.reloadConfig();

                    plugin.saveDefaultConfig();
                    plugin.configManager.saveDefaultConfig();

                    sender.sendMessage(Util.mm("<red>Przeładowane!"));
                }, ExecutorType.CONSOLE, ExecutorType.PLAYER)
                .register();
    }
}
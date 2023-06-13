package mc.theKOXpoland.SimpleCore.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.entity.Player;

import java.util.Objects;

public class FlyCommand {

    MainFile plugin;
    public FlyCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public void fly() {
        new CommandAPICommand("fly")
                .withAliases("f")
                .withPermission(CommandPermission.fromString("core.fly"))
                .executes((sender, args) -> {
                    Player player = (Player) sender;
                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        player.sendMessage(Util.mm(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_off"))));
                        return;
                    }
                    player.setAllowFlight(true);
                    player.sendMessage(Util.mm(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_on"))));
                }, ExecutorType.PLAYER)
                .withOptionalArguments(new EntitySelectorArgument.OnePlayer("target")
                        .executes((sender, args) -> {
                            Player player = (Player) sender;
                            Player target = (Player) args.get(0);
                            if (target == null) {
                                player.sendMessage(Util.mm(Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("player_null"))));
                                return;
                            }

                            String fly_off_other = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_off_other"))
                                    .replace("%target%", Util.getPlayerName(target));
                            String fly_other_off_me = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_other_off_me"))
                                    .replace("%player%", Util.getPlayerName(player));

                            if (target.getAllowFlight()) {
                                target.setAllowFlight(false);
                                player.sendMessage(Util.mm(fly_off_other));
                                target.sendMessage(Util.mm(fly_other_off_me));
                                return;
                            }

                            String fly_on_other = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_on_other"))
                                    .replace("%target%", Util.getPlayerName(target));
                            String fly_other_on_me = Objects.requireNonNull(plugin.configManager.getTranslationConfig().getString("fly_other_on_me"))
                                    .replace("%player%", Util.getPlayerName(player));

                            target.setAllowFlight(true);
                            player.sendMessage(Util.mm(fly_on_other));
                            target.sendMessage(Util.mm(fly_other_on_me));
                        }, ExecutorType.PLAYER, ExecutorType.CONSOLE)
                )
                .register();
    }
}
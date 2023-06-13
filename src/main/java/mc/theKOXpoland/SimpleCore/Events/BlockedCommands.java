package mc.theKOXpoland.SimpleCore.Events;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.Objects;

public record BlockedCommands(MainFile plugin) implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        String command = event.getMessage();
        String[] array = command.split(" ");

        if (command.contains(":op") || command.contains(":deop") || command.contains("stop") || command.contains("reload") || command.contains("restart")) {
            if (player.isOp()) {
                event.setCancelled(true);
                player.sendMessage(Util.mm("Ta komenda jest zablokowana"));
                return;
            }
        }

            if (player.hasPermission("core.blckedcmd.access")) {
                return;
            }

            List<String> allowed_commands = plugin.getConfig().getStringList("blocked_commands");

            if (command.contains(":")) {
                player.sendMessage(Util.mm(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
                event.setCancelled(true);
                return;
            }

            if (allowed_commands.contains(array[0])) {
               player.sendMessage(Util.mm(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
               event.setCancelled(true);
            }
    }
}
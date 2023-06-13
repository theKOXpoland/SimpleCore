package mc.theKOXpoland.SimpleCore.Events;

import mc.theKOXpoland.SimpleCore.MainFile;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class ConsoleListener implements Listener {

    MainFile plugin;
    public ConsoleListener(MainFile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        String command = event.getCommand();
        CommandSender sender = event.getSender();
        if (command.contains("op") || command.contains("deop") || command.contains("stop") || command.contains("reload")) {
            event.setCancelled(true);
            sender.sendMessage("Ta komenda jest zablokowana!");
        }
    }
}
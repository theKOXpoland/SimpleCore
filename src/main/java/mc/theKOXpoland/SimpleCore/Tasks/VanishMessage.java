package mc.theKOXpoland.SimpleCore.Tasks;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VanishMessage extends BukkitRunnable {

    MainFile plugin;
    public VanishMessage(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.invisible_list.contains(player)) {
                player.sendActionBar(Util.mm("<dark_red><bold>! <grey>Jesteś na <dark_grey>Vanishu <dark_red><bold>!<reset>"));
            }
        }
    }
}
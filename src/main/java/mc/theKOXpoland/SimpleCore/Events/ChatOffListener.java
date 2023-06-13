package mc.theKOXpoland.SimpleCore.Events;

import io.papermc.paper.event.player.AsyncChatEvent;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record ChatOffListener(MainFile plugin) implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChatEvent(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!plugin.isChatEnabled()) {
            if (!player.hasPermission("core.chatoff.write")) {
                player.sendMessage(Util.mm("<dark_red><bold[!] <grey>- Nie mozesz pisac podczas gdy chat jest <red>wylaczony!<reset>"));
                event.setCancelled(true);
            }
        }
    }
}

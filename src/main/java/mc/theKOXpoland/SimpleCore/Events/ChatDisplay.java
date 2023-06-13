package mc.theKOXpoland.SimpleCore.Events;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public record ChatDisplay(MainFile plugin) implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void ChatRender(AsyncChatEvent event) {
        Player player = event.getPlayer();
        TextComponent message = Component.text(String.valueOf(event.message()));
        //Audience audience = (Audience) Bukkit.getOnlinePlayers();
        //ChatRenderer renderer = ChatRenderer.defaultRenderer();

        if (event.isAsynchronous()) {
            if (player.hasPermission("core.color.root")) {
                player.sendMessage(Util.mm(Util.getPlayerName(player) + " <grey><bold>» <red>" + message));
                //player.chat(String.valueOf(Util.mm(Util.getPlayerName(player) + " <grey><bold>» <red>" + message)));
                //renderer.render(player, Util.mm(Util.getPlayerName(player)), Util.mm(String.valueOf(message)), audience);
                return;
            }

            if (player.hasPermission("core.color.vip")) {
                player.sendMessage(Util.mm(Util.getPlayerName(player) + " <grey><bold>» <white>" + message));
                //player.chat(String.valueOf(Util.mm(Util.getPlayerName(player) + " <grey><bold>» <white>" + message)));
                return;
            }

            player.sendMessage(Util.mm(Util.getPlayerName(player) + " <grey><bold>» <grey>" + message));
            //player.chat(String.valueOf(Util.mm( Util.getPlayerName(player) + " <grey><bold>» <grey>" + message)));
        }
    }
}
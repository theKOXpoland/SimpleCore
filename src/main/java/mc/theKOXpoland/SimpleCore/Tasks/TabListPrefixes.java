package mc.theKOXpoland.SimpleCore.Tasks;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabListPrefixes extends BukkitRunnable {

 MainFile plugin;
    public TabListPrefixes(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            setPlayerListName(players);
        }
    }

    public void setPlayerListName(Player player) {
        if (player.isOp() || player.hasPermission("core.prefix.root")) {
            player.displayName(Util.mm("<dark_red>" + player.getName()));
            player.playerListName(Util.mm(" <dark_red><bold>ROOT " + player.getName()));
        }
         else if (player.hasPermission("core.prefix.vip")) {
            player.displayName(Util.mm("<yellow>" + player.getName()));
            player.playerListName(Util.mm(" <gold><bold>VIP " + player.getName()));
        }
        else if (!player.hasPermission("core.prefix.vip") || !player.hasPermission("core.prefix.root")) {
            player.displayName(Util.mm("<grey>" + player.getName()));
            player.playerListName(Util.mm("<grey>" + player.getName()));
        }
        if (plugin.invisible_list.contains(player)) {
            player.playerListName(Util.mm(player.getName() + " <dark_grey>(<grey><bold>V<dark_grey>)<reset>"));
        }
    }
}
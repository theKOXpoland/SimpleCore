package mc.theKOXpoland.SimpleCore.Events;

import io.papermc.paper.event.player.AsyncChatEvent;
import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Tasks.TabListPrefixes;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Objects;

public record PlayerListener(MainFile plugin) implements Listener {

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {

        Location spawn = (Location) plugin.getConfig().get("spawnLocation");
        assert spawn != null;
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            event.setSpawnLocation(spawn);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity().getPlayer();
        Player killer = Objects.requireNonNull(player).getKiller();
        if (plugin.invisible_list.contains(player)) {
            event.deathMessage(null);
        }

        if (killer != null && killer != player) {
            event.deathMessage(Util.mm("&7Gracz " + Util.getPlayerName(player) + " &7zostal zabity przez " + Util.getPlayerName(killer)));
        } else if (killer != player) {
            if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent lastDamageCause) {
                if (lastDamageCause.getDamager() instanceof Arrow arrow) {
                    Player shooter = arrow.getServer().getPlayer(Objects.requireNonNull(lastDamageCause.getEntityType().getEntityClass()).getName());
                    if (arrow.getShooter() instanceof Arrow && shooter != null) {
                        event.deathMessage(Util.mm("&7Gracz " + Util.getPlayerName(player) + " &7zostal zabity przez " + Util.getPlayerName(shooter)));
                    }
                }
                if (lastDamageCause.getDamager() instanceof Trident trident) {
                    Player shooter = trident.getServer().getPlayer(Objects.requireNonNull(lastDamageCause.getEntityType().getEntityClass()).getName());
                    if (trident.getShooter() instanceof Trident && shooter != null) {
                        event.deathMessage(Util.mm("&7Gracz " + Util.getPlayerName(player) + " &7zostal zabity przez " + Util.getPlayerName(shooter)));
                    }
                }
                if (lastDamageCause.getDamager() instanceof PotionSplashEvent potionSplashEvent) {
                    Player thrower = potionSplashEvent.getPotion().getServer().getPlayer(Objects.requireNonNull(lastDamageCause.getEntityType().getEntityClass()).getName());
                    if (thrower != null) {
                        if (thrower.getPlayer() instanceof PotionSplashEvent) {
                            event.deathMessage(Util.mm("&7Gracz " + Util.getPlayerName(player) + " &7zostal zabity przez " + thrower.playerListName()));
                        }
                    } if (thrower == null) {
                        event.deathMessage(null);
                    }
                }
            }
            event.deathMessage(null);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        Player player = event.getPlayer();
        for (int i = 0; i < plugin.invisible_list.size(); i++) {
            player.hidePlayer(plugin, plugin.invisible_list.get(i));
        }
        new TabListPrefixes(plugin).run();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.quitMessage(null);
        Player player = event.getPlayer();
        plugin.invisible_list.remove(player);
         if (player.isInvulnerable() && !player.hasPermission("core.godmode")) {
             player.setInvulnerable(false);
             plugin.godmode_list.remove(player);
         }
        new TabListPrefixes(plugin).run();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRespawn(PlayerRespawnEvent event) {
        Location spawn = (Location) plugin.getConfig().get("spawnLocation");
        assert spawn != null;
        Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) {
            event.setRespawnLocation(spawn);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player player)) {
            return;
        }
        if (plugin.invisible_list.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (plugin.godmode_list.contains(player)) {
           event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncChatEvent event) {

        if (plugin.invisible_list.contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Util.fix("&4&l[!] &7- Nie mozesz pisac bedac na vanishu!"));
        }

       Component message = event.message();

        for (Player players : Bukkit.getOnlinePlayers()) {
            String target = players.getName();

            if (message.contains(Util.mm(target))) {
                Player targetedPlayer = Bukkit.getPlayerExact(target);

                assert targetedPlayer != null;
                targetedPlayer.playSound(targetedPlayer, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f, 0.01f);
            }
        }
    }

    @EventHandler
    public void onBookEditEvent(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        int pageCount = event.getNewBookMeta().getPageCount();

        int PageLimiter = plugin.getConfig().getInt("PageLimiter");

        if (pageCount > PageLimiter) {
            event.setCancelled(true);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.WRITABLE_BOOK));
            player.sendMessage(Util.fix("&c&l[!] &7- Limit stron w książkach jest obecnie ustawiony na &b" +PageLimiter));
        }
    }

    @EventHandler
    public void onBookReadEvent(final PlayerInteractEvent event) {

    }
}
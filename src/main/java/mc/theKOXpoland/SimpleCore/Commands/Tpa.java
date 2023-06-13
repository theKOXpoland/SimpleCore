package mc.theKOXpoland.SimpleCore.Commands;

import mc.theKOXpoland.SimpleCore.MainFile;
import mc.theKOXpoland.SimpleCore.Tasks.TpaSuccessful;
import mc.theKOXpoland.SimpleCore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record Tpa(MainFile plugin) implements CommandExecutor {

    static HashMap<UUID, UUID> targetMap = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Util.fix("Tylko gracze moga uzywaj tej komendy!"));
            return true;
        }
        if (command.getName().equals("tpa")) {
            if (!sender.hasPermission("core.tpa"))
                sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
            if (args.length == 1) {
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                    sender.sendMessage(Util.fix("&4&l[!] &7- Podany gracz nie jest online!"));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                final Player senderP = (Player) sender;
                assert target != null;
                if (plugin.invisible_list.contains(target) || !sender.hasPermission("core.vanish")) {
                    sender.sendMessage(Util.fix("&4&l[!] &7- Podany gracz nie jest online!"));
                    return true;
                }
                if (target.getUniqueId().equals(senderP.getUniqueId())) {
                    sender.sendMessage(Util.fix("&4&l[!] &7- Nie mozesz sie teleportowac do samego siebie!"));
                    return true;
                }
                if (targetMap.containsKey(senderP.getUniqueId())) {
                    sender.sendMessage(Util.fix("&c&l[!] &7- Wyslales juz prosbe o teleportacje do tego gracza!"));
                    return false;
                }
                target.sendMessage(Util.fix("&6&l[!] &7- "+ senderP.getDisplayName() +" &7Chce sie do Ciebie teleportowac! \n&7Wpisz &6/tpaccept &7By &azaakceptowac &7jego prosbe o teleportacje!\n&7Lub wpisz &6/tpdeny &7by &codrzucic &7prosbe!\n&7Masz 5 minut na odpowiedz!"));
                targetMap.put(senderP.getUniqueId(), target.getUniqueId());
                sender.sendMessage(Util.fix("&6&l[!] &7- Wyslano prosbe o teleportacje do "+ Util.getPlayerName(target)));
                (new BukkitRunnable() {
                    public void run() {
                         Tpa.targetMap.remove(senderP.getUniqueId());
                    }
                }).runTaskLaterAsynchronously((Plugin) this.plugin, 6000L);
            } else {
                sender.sendMessage(Util.fix("&4&l[!] &7- Poprawne uzycie: &6/tpa {nick}"));
            }
            return true;
        }
        if (command.getName().equals("tpaccept")) {
            if (!sender.hasPermission("core.accept"))
                sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
            final Player senderP = (Player) sender;
            if (targetMap.containsValue(senderP.getUniqueId())) {
                sender.sendMessage(Util.fix("&6&l[!] &7- Prosba o teleporatacje zaakceptowana!"));
                for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                    if (((UUID) entry.getValue()).equals(senderP.getUniqueId())) {
                        Player tpRequester = Bukkit.getPlayer(entry.getKey());
                        assert tpRequester != null;
                        TpaSuccessful event = new TpaSuccessful(tpRequester, tpRequester.getLocation());
                        Bukkit.getPluginManager().callEvent(event);
                        tpRequester.teleport((Entity) senderP);
                        targetMap.remove(entry.getKey());
                        break;
                    }
                }
            } else {
                sender.sendMessage(Util.fix("&4&l[!] &7- Nie masz zadnych czekajacych prosb o teleportacje!"));
            }
            return true;
        }
        if (command.getName().equals("tpdeny")) {
            if (!sender.hasPermission("core.deny"))
                sender.sendMessage(Util.fix(Objects.requireNonNull(plugin.getConfig().getString("nie_masz_uprawnien"))));
            final Player senderP = (Player) sender;
            if (targetMap.containsValue(senderP.getUniqueId())) {
                for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                    if (((UUID) entry.getValue()).equals(senderP.getUniqueId())) {
                        targetMap.remove(entry.getKey());
                        Player originalSender = Bukkit.getPlayer(entry.getKey());
                        assert originalSender != null;
                        originalSender.sendMessage(Util.fix("&c&l[!] &7- Twoja prosba o teleportacje zostala odrzucona!"));
                        sender.sendMessage(Util.fix("&6&l[!] &7- Odrzucono prosbe teleportacji!"));
                        break;
                    }
                }
            } else {
                sender.sendMessage(Util.fix("&c&l[!] &7- Nie masz zadnych czekajacych prosb o teleportacje!"));
            }
            return true;
        }
        return false;
    }
}

package mc.theKOXpoland.SimpleCore.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Util {
    public static String fix(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Component mm(String text) {
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(text);
    }

    public static String locToString(Location loc) {
        return loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch() + ":" + loc.getWorld().getName();
    }
    public static boolean isInteger(String string) {
        return !Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }
    public static Location locFromString(String str) {
        String[] str2loc = str.split(":");
        Location loc = new Location((World) Bukkit.getWorlds().get(0), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        loc.setX(Double.parseDouble(str2loc[0]));
        loc.setY(Double.parseDouble(str2loc[1]));
        loc.setZ(Double.parseDouble(str2loc[2]));
        loc.setYaw(Float.parseFloat(str2loc[3]));
        loc.setPitch(Float.parseFloat(str2loc[4]));
        loc.setWorld(Bukkit.getWorld(str2loc[5]));
        return loc;
    }
    public static String locToString(double x, double y, double z) {
        return String.valueOf(x) + ":" + y + ":" + z + ":" + 0.0F + ":" + 0.0F;
    }
    public static void ClearAndHeal(Player player)
    {
        if (player == null) {
            return;
        }

        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public static void sendMessageList(final Player player, final List<String> messages) {
        for (String message : messages) {
            player.sendMessage(fix(message));
        }
    }

    public static String getPlayerName(Player player) {
       return "<" + player.displayName().color() + ">" + player.getName();
    }
}
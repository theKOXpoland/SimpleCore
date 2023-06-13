package mc.theKOXpoland.SimpleCore.Tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TpaSuccessful extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private Location locationBeforeTpa;
    private boolean isCancelled;

    public TpaSuccessful(Player player, Location locationBeforeTpa) {
        this.player = player;
        this.locationBeforeTpa = locationBeforeTpa;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getLocationBeforeTpa() {
        return locationBeforeTpa;
    }

    public void setLocationBeforeTpa(Location locationBeforeTpa) {
        this.locationBeforeTpa = locationBeforeTpa;
    }
}

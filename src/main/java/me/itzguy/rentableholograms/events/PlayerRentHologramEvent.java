package me.itzguy.rentableholograms.events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRentHologramEvent extends Event {
    
    private Player player;
    private Hologram hologram;
    private final HandlerList handlers = new HandlerList();


    /**
     * This event is fired when a player is renting a hologram
     * @param player Player that have rented the hologram
     * @param hologram the hologram object containing: price, location...
     */
    public PlayerRentHologramEvent(Player player, Hologram hologram) {
        this.player = player;
        this.hologram = hologram;
    }

    public Player getPlayer() {
        return player;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}

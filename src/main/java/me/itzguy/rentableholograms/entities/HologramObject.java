package me.itzguy.rentableholograms.entities;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Location;

public class HologramObject {

    public Location location;
    public Hologram hologram;
    public int price, days;
    public String owner;
    public long timestamp;

    public HologramObject(Location location, Hologram hologram, int price, int days, String owner, long timestamp) {
        this.location = location;
        this.hologram = hologram;
        this.price = price;
        this.days = days;
        this.owner = owner;
        this.timestamp = timestamp;
    }
}

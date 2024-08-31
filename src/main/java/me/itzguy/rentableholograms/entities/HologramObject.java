package me.itzguy.rentableholograms.entities;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.UUID;

public class HologramObject {

    public Location location;
    public Hologram hologram;
    public int price, days;
    public String owner;
    public long timestamp;
    public String id;

    public UUID uuid;

    public HologramObject(Location location, Hologram hologram, int price, int days, String owner, long timestamp, String id, UUID uuid) {
        this.location = location;
        this.hologram = hologram;
        this.price = price;
        this.days = days;
        this.owner = owner;
        this.timestamp = timestamp;
        this.id = id;
        this.uuid = uuid;
    }
}

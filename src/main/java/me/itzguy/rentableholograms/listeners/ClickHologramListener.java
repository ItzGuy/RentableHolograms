package me.itzguy.rentableholograms.listeners;

import eu.decentsoftware.holograms.event.HologramClickEvent;
import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.entities.HologramObject;
import me.itzguy.rentableholograms.managers.HologramManager;
import me.itzguy.rentableholograms.userinterfaces.BuyHologramInterface;
import me.itzguy.rentableholograms.userinterfaces.EditHologramInterface;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class ClickHologramListener implements Listener {

    @EventHandler
    public void onClick(HologramClickEvent event) {
        HologramObject holo = null;
        for (HologramObject holoObj : HologramManager.getHologramObjectMap().values()) {
            if (holoObj.uuid.toString().equalsIgnoreCase(event.getHologram().getId())) {
                holo = holoObj;
            }
        }

        if (holo == null) return;

        HologramObject finalHolo = holo;
        Bukkit.getScheduler().scheduleSyncDelayedTask(RentableHolograms.getInstance(), () -> {
            if (finalHolo.owner.equalsIgnoreCase("none")) {
                Inventory i = BuyHologramInterface.getInventory(event.getPlayer(), finalHolo.price, finalHolo.id);
                event.getPlayer().openInventory(i);
            } else if (finalHolo.owner.equals(event.getPlayer().getName())){
                Inventory i = EditHologramInterface.getInventory(event.getPlayer(), 1, finalHolo.id);
                event.getPlayer().openInventory(i);
            } else if (event.getPlayer().hasPermission("rentableholograms.admin.edit") && event.getPlayer().isSneaking()) {
                Inventory i = EditHologramInterface.getInventory(event.getPlayer(), 1, finalHolo.id);
                event.getPlayer().openInventory(i);
            }
        }, 1);
    }
}

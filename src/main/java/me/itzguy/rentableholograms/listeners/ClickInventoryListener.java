package me.itzguy.rentableholograms.listeners;

import me.itzguy.rentableholograms.userinterfaces.BuyHologramHolder;
import me.itzguy.rentableholograms.userinterfaces.BuyHologramInterface;
import me.itzguy.rentableholograms.userinterfaces.EditHologramHolder;
import me.itzguy.rentableholograms.userinterfaces.EditHologramInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickInventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof BuyHologramHolder) {
            BuyHologramInterface.click((Player) event.getWhoClicked(), event.getCurrentItem());
            event.setCancelled(true);
        }

        if (event.getInventory().getHolder() instanceof EditHologramHolder) {
            EditHologramInterface.click((Player) event.getWhoClicked(), event.getCurrentItem());
            event.setCancelled(true);
        }
    }
}

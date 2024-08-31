package me.itzguy.rentableholograms.userinterfaces;

import de.tr7zw.nbtapi.NBTItem;
import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.managers.*;
import me.itzguy.rentableholograms.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class BuyHologramInterface {

    public static Inventory getInventory(Player player, int price, String hologramUUID) {
        Inventory inv = Bukkit.createInventory(new BuyHologramHolder(), 1 * 9, LanguageManager.getMessage("gui-buy-hologram-title"));

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, ItemUtils.getItem(Material.BLACK_STAINED_GLASS_PANE, "&a", 1));
        }

        ItemStack item = ItemUtils.getItem(Material.EMERALD_BLOCK, LanguageManager.getMessage("gui-buy-hologram-item"),
                1,
                LanguageManager.getMessage("gui-buy-hologram-item-lore").replace("%price%", price + ""));

        NBTItem nbti = new NBTItem(item);
        nbti.setString("HologramUUID", hologramUUID);

        inv.setItem(4, nbti.getItem());

        return inv;
    }

    public static void click(Player player, ItemStack item) {
        if (item == null) return;

        if (item.getItemMeta().getDisplayName().equalsIgnoreCase(LanguageManager.getMessage("gui-buy-hologram-item"))) {
            NBTItem nbti = new NBTItem(item);
            String hologramUUID = nbti.getString("HologramUUID");

            player.closeInventory();

            int price = HologramsConfig.getHologramsConfig().getInt(hologramUUID + ".price");

            int owned = 0;
            for (String hologramID : HologramManager.getHologramObjectMap().keySet()) {
                if (HologramManager.getHologramObjectMap().get(hologramID).owner.equalsIgnoreCase(player.getName())) {
                    owned++;
                }
            }

            if (owned >= ConfigManager.getMaxHolograms()) {
                if (!player.hasPermission("rentableholograms.restrictions.bypass")) {
                    player.sendMessage(LanguageManager.getMessage("gui-buy-hologram-already-own"));
                    return;
                }
            }

            if (!player.hasPermission("rentableholograms.economy.bypass")) {
                if (RentableHolograms.getInstance().getEcon().getBalance(player) >= price) {
                    RentableHolograms.getInstance().getEcon().withdrawPlayer(player, price);
                } else {
                    player.sendMessage(LanguageManager.getMessage("gui-buy-hologram-buy-fail"));
                    return;
                }
            }


            long timestamp = (new Timestamp(System.currentTimeMillis()).getTime()) / 1000;

            String lines = LanguageManager.getMessage("gui-buy-hologram-buy-default-lines");
            String[] linesA = lines.split("%n%");


            HologramsConfig.getHologramsConfig().set(hologramUUID + ".owner", player.getName());
            HologramsConfig.getHologramsConfig().set(hologramUUID + ".timestamp", timestamp);
            HologramsConfig.getHologramsConfig().set(hologramUUID + ".days", ConfigManager.getStartingDays());
            HologramsConfig.getHologramsConfig().set(hologramUUID + ".lines", linesA);

            HologramsConfig.saveHologramsConfig();
            HologramsConfig.reloadHologramsConfig();

            HologramManager.updateHologram(hologramUUID);

            LogManager.logToFile("Player " + player.getName() + " Rented hologram. (Hologram ID " + hologramUUID + ")");


            player.sendMessage(LanguageManager.getMessage("gui-buy-hologram-buy-successfully"));
        }
    }
}

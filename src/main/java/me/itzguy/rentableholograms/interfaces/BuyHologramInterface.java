package me.itzguy.rentableholograms.interfaces;

import de.tr7zw.nbtapi.NBTItem;
import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.managers.HologramManager;
import me.itzguy.rentableholograms.managers.HologramsConfig;
import me.itzguy.rentableholograms.utils.ItemUtils;
import me.itzguy.rentableholograms.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BuyHologramInterface {

    public static Inventory getInventory(Player player, int price, String hologramUUID) {
        Inventory inv = Bukkit.createInventory(null, 1*9, StringUtils.color("&eקנייה של הולוגרמה"));

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, ItemUtils.getItem(Material.BLACK_STAINED_GLASS_PANE, "&a", 1));
        }

        ItemStack item = ItemUtils.getItem(Material.EMERALD_BLOCK, "&aקנה הולוגרמה זאת", 1, "&7מחיר ליום: &e " + price);

        NBTItem nbti = new NBTItem(item);
        nbti.setString("HologramUUID", hologramUUID);

        inv.setItem(4, nbti.getItem());

        return inv;
    }

    public static void click(Player player, ItemStack item) {
        // replace all hologramuuid to hologramid, translate all texts to language file
        if (item.getItemMeta().getDisplayName().equalsIgnoreCase(StringUtils.color("&aקנה הולוגרמה זאת"))) {
            NBTItem nbti = new NBTItem(item);
            String hologramUUID = nbti.getString("HologramUUID");

            player.closeInventory();

            int price = HologramsConfig.getHologramsConfig().getInt(hologramUUID + ".price");


            for (String hologramID : HologramManager.getHologramObjectMap().keySet()) {
                if (HologramManager.getHologramObjectMap().get(hologramID).owner.equalsIgnoreCase(player.getName())) {

                    player.sendMessage(StringUtils.color("&cקנית כבר הולוגרמה!"));

                    return;
                }
            }


            if (RentableHolograms.getInstance().getEcon().getBalance(player) >= price) {
                RentableHolograms.getInstance().getEcon().withdrawPlayer(player, price);


                long timestamp = (new Timestamp(System.currentTimeMillis()).getTime()) / 1000;

                List<String> lines = new ArrayList<>();

                lines.add(""); // make it into loop to make it into the lines set in config
                lines.add("");
                lines.add("");
                lines.add("");

                HologramsConfig.getHologramsConfig().set(hologramUUID + ".owner", player.getName());
                HologramsConfig.getHologramsConfig().set(hologramUUID + ".timestamp", timestamp);
                HologramsConfig.getHologramsConfig().set(hologramUUID + ".days", 1);
                HologramsConfig.getHologramsConfig().set(hologramUUID + ".lines", lines);

                HologramsConfig.saveHologramsConfig();

                HologramManager.clearAllHolograms();
                HologramManager.loadAllHolograms();


                player.sendMessage(StringUtils.color("&aקנית הולוגרמה זאת בהצלחה!"));
            } else {
                player.sendMessage(StringUtils.color("&cאין לך מספיק כסף כדי לקנות הולוגרמה זאת!"));
            }
        }
    }
}

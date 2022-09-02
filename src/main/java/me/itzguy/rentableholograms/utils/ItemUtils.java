package me.itzguy.rentableholograms.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

import static me.itzguy.rentableholograms.utils.StringUtils.color;

public class ItemUtils {

    public static ItemStack getItem(Material material, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(color(name));
        itemMeta.setLore(color(Arrays.asList(lore)));

        itemMeta.addItemFlags(ItemFlag.values());

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack getHead(String owner, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
        ItemMeta itemMeta1 = item.getItemMeta();

        SkullMeta itemMeta = (SkullMeta) itemMeta1;

        itemMeta.setOwningPlayer(Bukkit.getPlayer(owner));

        itemMeta.setDisplayName(color(name));
        itemMeta.setLore(color(Arrays.asList(lore)));

        itemMeta.addItemFlags(ItemFlag.values());

        item.setItemMeta(itemMeta);

        return item;
    }
}

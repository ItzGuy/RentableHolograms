package me.itzguy.rentableholograms.userinterfaces;

import de.tr7zw.nbtapi.NBTItem;
import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.managers.*;
import me.itzguy.rentableholograms.utils.GetUserInput;
import me.itzguy.rentableholograms.utils.ItemUtils;
import me.itzguy.rentableholograms.utils.StringUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditHologramInterface {

    public static Inventory getInventory(Player player, int line, String hologramUUID) {
        Inventory inv = Bukkit.createInventory(new EditHologramHolder(), 1 * 9, LanguageManager.getMessage("gui-edit-hologram-title").replace("%line%", line + ""));

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, ItemUtils.getItem(Material.BLACK_STAINED_GLASS_PANE, "&a", 1));
        }

        ItemStack plusLine;
        if (ConfigManager.getLines() > line) {
            plusLine = ItemUtils.getItem(Material.EMERALD, LanguageManager.getMessage("gui-edit-hologram-increase-line-item"),
                    1,
                    LanguageManager.getMessage("gui-edit-hologram-increase-line-item-lore"));
        } else {
            plusLine = ItemUtils.getItem(Material.EMERALD_BLOCK, LanguageManager.getMessage("gui-edit-hologram-increase-line-item"),
                    1,
                    LanguageManager.getMessage("gui-edit-hologram-increase-line-item-lore"));
        }

        ItemStack minusLine;
        if (1 < line) {
            minusLine = ItemUtils.getItem(Material.REDSTONE, LanguageManager.getMessage("gui-edit-hologram-decrease-line-item"),
                    1,
                    LanguageManager.getMessage("gui-edit-hologram-decrease-line-item-lore"));
        } else {
            minusLine = ItemUtils.getItem(Material.REDSTONE_BLOCK, LanguageManager.getMessage("gui-edit-hologram-decrease-line-item"),
                    1,
                    LanguageManager.getMessage("gui-edit-hologram-decrease-line-item-lore"));
        }

        ItemStack editLine = ItemUtils.getItem(Material.WRITABLE_BOOK, LanguageManager.getMessage("gui-edit-hologram-edit-line-item"),
                1,
                LanguageManager.getMessage("gui-edit-hologram-edit-line-item-lore"));

        ItemStack unRent = ItemUtils.getItem(Material.BARRIER, LanguageManager.getMessage("gui-edit-hologram-unrent-item"),
                1,
                LanguageManager.getMessage("gui-edit-hologram-unrent-item-lore"));

        ItemStack addRent = ItemUtils.getItem(Material.BEACON, LanguageManager.getMessage("gui-edit-hologram-addrent-item"),
                1,
                LanguageManager.getMessage("gui-edit-hologram-addrent-item-lore"));


        NBTItem nbti = new NBTItem(editLine);
        nbti.setString("HologramUUID", hologramUUID);
        nbti.setString("ButtonAct", "editline");
        nbti.setInteger("LineCount", line);

        NBTItem nbti1 = new NBTItem(plusLine);
        nbti1.setString("HologramUUID", hologramUUID);
        if (plusLine.getType() == Material.EMERALD)
            nbti1.setString("ButtonAct", "plusline");
        nbti1.setInteger("LineCount", line);

        NBTItem nbti2 = new NBTItem(minusLine);
        nbti2.setString("HologramUUID", hologramUUID);
        if (minusLine.getType() == Material.REDSTONE)
            nbti2.setString("ButtonAct", "minusline");
        nbti2.setInteger("LineCount", line);

        NBTItem nbti3 = new NBTItem(unRent);
        nbti3.setString("HologramUUID", hologramUUID);
        nbti3.setString("ButtonAct", "unrent");
        nbti3.setInteger("LineCount", line);

        NBTItem nbti4 = new NBTItem(addRent);
        nbti4.setString("HologramUUID", hologramUUID);
        nbti4.setString("ButtonAct", "addrent");
        nbti4.setInteger("LineCount", line);

        inv.setItem(0, nbti4.getItem());
        inv.setItem(3, nbti2.getItem());
        inv.setItem(4, nbti.getItem());
        inv.setItem(5, nbti1.getItem());
        inv.setItem(8, nbti3.getItem());

        return inv;
    }

    public static void click(Player player, ItemStack item) {
        if (item == null) return;

        NBTItem nbtItem = new NBTItem(item);
        String hologramID = nbtItem.getString("HologramUUID");
        String action = nbtItem.getString("ButtonAct");
        int line = nbtItem.getInteger("LineCount");

        if (action.equalsIgnoreCase("plusline")) {
            Inventory i = getInventory(player, (line + 1), hologramID);
            player.openInventory(i);
        }
        if (action.equalsIgnoreCase("minusline")) {
            Inventory i = getInventory(player, (line - 1), hologramID);
            player.openInventory(i);
        }

        if (action.equalsIgnoreCase("editline")) {
            player.closeInventory();

            if (ConfigManager.getInputMethod().equalsIgnoreCase("CHAT")) {
                GetUserInput.getUserInput(player, "Input a new text for line!", input -> {

                    //filter baddie words!!!
                    if (!player.hasPermission("rentableholograms.filter.profanity.bypass") && BlacklistManager.verifyText(input)) {
                        //baddie
                        player.sendMessage(LanguageManager.getMessage("edit-hologram-filtered"));
                        return;
                    }

                    String newLine = input;
                    //filter color
                    if (!player.hasPermission("rentableholograms.color.bypass")) {
                        newLine = StringUtils.stripColorCodes(input);
                    }


                    List<String> lines = HologramsConfig.getHologramsConfig().getStringList(hologramID + ".lines");

                    String[] aLines = new String[ConfigManager.getLines()];
                    for (int i = 0; i < ConfigManager.getLines(); i++)
                        aLines[i] = "";

                    for (int i = 0; i < lines.size(); i++) {
                        aLines[i] = lines.get(i);
                    }

                    String previousLine = aLines[line - 1];

                    LogManager.logToFile("Player " + player.getName() + " Updated line from: `"
                            + previousLine + "` to: `" + newLine + "` (Line " + line + ", Hologram ID " + hologramID + ")");

                    aLines[line - 1] = input.equalsIgnoreCase("null") ? null : input;

                    List<String> linesA = StringUtils.compressList(aLines);

                    HologramsConfig.getHologramsConfig().set(hologramID + ".lines", linesA);

                    HologramsConfig.saveHologramsConfig();
                    HologramsConfig.reloadHologramsConfig();

                    Bukkit.getScheduler().scheduleSyncDelayedTask(RentableHolograms.getInstance(), () -> HologramManager.updateHologram(hologramID), 1);
                });
            } else {
                //anvil type for changing line
                AnvilGUI.Builder builder = new AnvilGUI.Builder();

                builder.plugin(RentableHolograms.getInstance());
                builder.title("Input a new text for line!");
                builder.text("Line Text");

                builder.onClick((slot, state) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    String newLine = state.getText();
                    if (!player.hasPermission("rentableholograms.color.bypass")) {
                        newLine = StringUtils.stripColorCodes(state.getText());
                    }

                    //filter baddie words!!!
                    if (!player.hasPermission("rentableholograms.filter.profanity.bypass") && BlacklistManager.verifyText(state.getText())) {
                        //baddie
                        LogManager.logToFile("Player " + player.getName() + " Triggered filter (`" + newLine + "`) (Line " + line + ", Hologram ID " + hologramID + ")");

                        player.sendMessage(LanguageManager.getMessage("edit-hologram-filtered"));
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    }


                    List<String> lines = HologramsConfig.getHologramsConfig().getStringList(hologramID + ".lines");

                    String[] aLines = new String[ConfigManager.getLines()];
                    for (int i = 0; i < ConfigManager.getLines(); i++)
                        aLines[i] = "";

                    for (int i = 0; i < lines.size(); i++) {
                        aLines[i] = lines.get(i);
                    }

                    String previousLine = aLines[line - 1];
                    LogManager.logToFile("Player " + player.getName() + " Updated line from: `"
                            + previousLine + "` to: `" + newLine + "` (Line " + line + ", Hologram ID " + hologramID + ")");

                    aLines[line - 1] = newLine.equalsIgnoreCase("null") ? null : newLine;

                    List<String> linesA = StringUtils.compressList(aLines);

                    HologramsConfig.getHologramsConfig().set(hologramID + ".lines", linesA);

                    HologramsConfig.saveHologramsConfig();
                    HologramsConfig.reloadHologramsConfig();

                    Bukkit.getScheduler().scheduleSyncDelayedTask(RentableHolograms.getInstance(), () -> HologramManager.updateHologram(hologramID), 1);


                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                });

                builder.open(player);
            }
        }

        //cancel rent
        if (action.equalsIgnoreCase("unrent")) {
            player.closeInventory();

            HologramsConfig.getHologramsConfig().set(hologramID + ".owner", "none");
            HologramsConfig.getHologramsConfig().set(hologramID + ".lines", null);

            HologramsConfig.saveHologramsConfig();
            HologramsConfig.reloadHologramsConfig();

            HologramManager.updateHologram(hologramID);

            LogManager.logToFile("Player " + player.getName() + " Cancelled rent (Hologram ID " + hologramID + ")");

            player.sendMessage(LanguageManager.getMessage("unrented-hologram"));
        }

        if (action.equalsIgnoreCase("addrent")) {
            int price = HologramManager.getHologramObjectMap().get(hologramID).price;
            int days = HologramManager.getHologramObjectMap().get(hologramID).days;

            if (days >= ConfigManager.getMaxDays()) {
                if (!player.hasPermission("rentableholograms.restrictions.bypass")) {
                    player.sendMessage(LanguageManager.getMessage("addrent-max-days-hologram"));
                    return;
                }
            }

            if (!player.hasPermission("rentableholograms.economy.bypass")) {
                if (RentableHolograms.getInstance().getEcon().getBalance(player) >= price) {
                    RentableHolograms.getInstance().getEcon().withdrawPlayer(player, price);
                } else {
                    player.closeInventory();
                    player.sendMessage(LanguageManager.getMessage("addrent-insufficient-hologram"));
                    return;
                }
            }

            player.closeInventory();

            HologramsConfig.getHologramsConfig().set(hologramID + ".days", (days + 1));

            HologramsConfig.saveHologramsConfig();
            HologramsConfig.reloadHologramsConfig();

            Bukkit.getScheduler().scheduleSyncDelayedTask(RentableHolograms.getInstance(), () -> HologramManager.updateHologram(hologramID), 1);

            LogManager.logToFile("Player " + player.getName() + " Extended rent (Hologram ID " + hologramID + ")");

            player.sendMessage(LanguageManager.getMessage("addrent-hologram"));
        }
    }
}
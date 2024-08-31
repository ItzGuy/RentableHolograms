package me.itzguy.rentableholograms.managers;

import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.utils.StringUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

    private static File languageFile;
    private static FileConfiguration languageConfig = new YamlConfiguration();

    private static Map<String, String> messages = new HashMap<>();

    /**
     * Load language.yml and all messages into a list
     */
    public static void loadMessages() {
        languageFile = new File(RentableHolograms.getInstance().getDataFolder(), "language.yml");

        if (!languageFile.exists()) {
            languageFile.getParentFile().mkdirs();
            RentableHolograms.getInstance().saveResource("language.yml", false);
        }

        try {
            languageConfig.load(languageFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        RentableHolograms.getInstance().getLogger().info("Validating language.yml");
        validateMessages();

        RentableHolograms.getInstance().getLogger().info("Loading language.yml");
        for (String message : languageConfig.getKeys(false)) {
            messages.put(message, languageConfig.getString(message));
        }
    }

    /**
     * Reload language.yml and all messages
     */
    public static void reloadMessages() {
        languageConfig = YamlConfiguration.loadConfiguration(new File(RentableHolograms.getInstance().getDataFolder(), "language.yml"));

        RentableHolograms.getInstance().getLogger().info("Validating language.yml");
        validateMessages();

        messages.clear();

        RentableHolograms.getInstance().getLogger().info("Loading language.yml");
        for (String message : languageConfig.getKeys(false)) {
            messages.put(message, languageConfig.getString(message));
        }
    }

    /**
     * Save language.yml
     */
    public static void saveMessages() {
        try {
            languageConfig.save(languageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validate that all the updated messages are in language.yml
     * if not they will be added
     */
    public static void validateMessages() {
        if (!languageConfig.contains("reload-configs"))
            languageConfig.set("reload-configs", "&aAll the configurations files have been reloaded!");

        if (!languageConfig.contains("invalid-args"))
            languageConfig.set("invalid-args", "&cInvalid arguments!");

        if (!languageConfig.contains("number-required"))
            languageConfig.set("number-required", "&cThe argument provided is not a number!");

        if (!languageConfig.contains("created-hologram"))
            languageConfig.set("created-hologram", "&aSuccessfully created new hologram!");

        if (!languageConfig.contains("unrented-hologram"))
            languageConfig.set("unrented-hologram", "&cSuccessfully unrented the hologram!");

        if (!languageConfig.contains("addrent-hologram"))
            languageConfig.set("addrent-hologram", "&aSuccessfully added 1 day to the time of rental!");

        if (!languageConfig.contains("addrent-insufficient-hologram"))
            languageConfig.set("addrent-insufficient-hologram", "&cInsufficient funds to increase rental time!");

        if (!languageConfig.contains("addrent-max-days-hologram"))
            languageConfig.set("addrent-max-days-hologram", "&cReached max amount of days allowerd to rent!");

        if (!languageConfig.contains("gui-buy-hologram-title"))
            languageConfig.set("gui-buy-hologram-title", "&8Buy a Hologram");

        if (!languageConfig.contains("gui-buy-hologram-item"))
            languageConfig.set("gui-buy-hologram-item", "&aBuy this hologram!");

        if (!languageConfig.contains("gui-buy-hologram-item-lore"))
            languageConfig.set("gui-buy-hologram-item-lore", "&7This hologram costs: %price% for a day. %n%&7Click to buy this hologram for 1 day period.");

        if (!languageConfig.contains("gui-buy-hologram-already-own"))
            languageConfig.set("gui-buy-hologram-already-own", "&cYou can't buy a new hologram! you already own one.");

        if (!languageConfig.contains("gui-buy-hologram-buy-successfully"))
            languageConfig.set("gui-buy-hologram-buy-successfully", "&aYou have successfully bought this hologram!");

        if (!languageConfig.contains("gui-buy-hologram-buy-fail"))
            languageConfig.set("gui-buy-hologram-buy-fail", "&cInsufficient funds to buy this hologram!");

        if (!languageConfig.contains("gui-buy-hologram-buy-default-lines"))
            languageConfig.set("gui-buy-hologram-buy-default-lines", "&eCongrats on the new hologram! %n%&eRight click me to edit this %n%&eMessage!");

        if (!languageConfig.contains("gui-edit-hologram-title"))
            languageConfig.set("gui-edit-hologram-title", "&8Edit Hologram &7-> Line %line%");

        if (!languageConfig.contains("gui-edit-hologram-increase-line-item"))
            languageConfig.set("gui-edit-hologram-increase-line-item", "&2+");

        if (!languageConfig.contains("gui-edit-hologram-increase-line-item-lore"))
            languageConfig.set("gui-edit-hologram-increase-line-item-lore", "&7Increase the line number to edit!");

        if (!languageConfig.contains("gui-edit-hologram-decrease-line-item"))
            languageConfig.set("gui-edit-hologram-decrease-line-item", "&4-");

        if (!languageConfig.contains("gui-edit-hologram-decrease-line-item-lore"))
            languageConfig.set("gui-edit-hologram-decrease-line-item-lore", "&7Decrease the line number to edit!");

        if (!languageConfig.contains("gui-edit-hologram-edit-line-item"))
            languageConfig.set("gui-edit-hologram-edit-line-item", "&7EDIT");

        if (!languageConfig.contains("gui-edit-hologram-edit-line-item-lore"))
            languageConfig.set("gui-edit-hologram-edit-line-item-lore", "&7Edit the selected line!");

        if (!languageConfig.contains("gui-edit-hologram-unrent-item"))
            languageConfig.set("gui-edit-hologram-unrent-item", "&cCANCEL RENT");

        if (!languageConfig.contains("gui-edit-hologram-unrent-item-lore"))
            languageConfig.set("gui-edit-hologram-unrent-item-lore", "&7Cancel the current time of rental and%n%&7free up the hologram for others.");

        if (!languageConfig.contains("gui-edit-hologram-addrent-item"))
            languageConfig.set("gui-edit-hologram-addrent-item", "&cADD RENT");

        if (!languageConfig.contains("gui-edit-hologram-addrent-item-lore"))
            languageConfig.set("gui-edit-hologram-addrent-item-lore", "&7Add 1 day to the current time of rental.");

        if (!languageConfig.contains("hologram-identify"))
            languageConfig.set("hologram-identify", "&cHOLOGRAM ID: %id%");

        if (!languageConfig.contains("edit-hologram-filtered"))
            languageConfig.set("edit-hologram-filtered", "&cYou can't use bad language!");

        saveMessages();
    }

    /**
     * Get a colored message
     * if invalid message path has been provided throw error to debug
     */
    public static String getMessage(String message) {
        if (messages.containsKey(message))
            return StringUtils.color(messages.get(message));
        else
            throw new IllegalArgumentException("INVALID MESSAGE NAME HAS BEEN PROVIDED!");
    }
}

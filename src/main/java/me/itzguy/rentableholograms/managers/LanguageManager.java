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
     * Validate that all of the updated messages are in language.yml
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

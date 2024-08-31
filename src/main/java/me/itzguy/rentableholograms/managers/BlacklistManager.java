package me.itzguy.rentableholograms.managers;

import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.utils.StringUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistManager {

    private static File blacklistFile;
    private static FileConfiguration blacklistConfig = new YamlConfiguration();

    private static List<String> blacklistedDict = new ArrayList<>();

    /**
     * Load language.yml and all messages into a list
     */
    public static void loadBlacklist() {
        blacklistFile = new File(RentableHolograms.getInstance().getDataFolder(), "blacklist.yml");

        if (!blacklistFile.exists()) {
            blacklistFile.getParentFile().mkdirs();
            RentableHolograms.getInstance().saveResource("blacklist.yml", false);
        }

        try {
            blacklistConfig.load(blacklistFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        RentableHolograms.getInstance().getLogger().info("Validating blacklist.yml");

        RentableHolograms.getInstance().getLogger().info("Loading blacklist.yml");

        for (String word : blacklistConfig.getStringList("blacklisted-words")) {
            blacklistedDict.add(word.toLowerCase());
        }
    }

    /**
     * Reload language.yml and all messages
     */
    public static void reloadBlacklist() {
        blacklistConfig = YamlConfiguration.loadConfiguration(new File(RentableHolograms.getInstance().getDataFolder(), "blacklist.yml"));

        RentableHolograms.getInstance().getLogger().info("Validating blacklist.yml");

        blacklistedDict.clear();

        RentableHolograms.getInstance().getLogger().info("Loading blacklist.yml");

        for (String word : blacklistConfig.getStringList("blacklisted-words")) {
            blacklistedDict.add(word.toLowerCase());
        }
    }

    public static boolean verifyText(String message) {
        String msg = StringUtils.stripColorCodes(message).toLowerCase();

        String clearedMessage = msg.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", "");

        out:
        for (String blocked : blacklistedDict) {

            if (clearedMessage.contains(blocked)) {
                return true;
            }
        }

        return false;
    }
}

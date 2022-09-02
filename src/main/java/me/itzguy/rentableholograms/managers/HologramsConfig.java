package me.itzguy.rentableholograms.managers;

import me.itzguy.rentableholograms.RentableHolograms;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HologramsConfig {

    private static File hologramsFile;
    private static FileConfiguration hologramsConfig = new YamlConfiguration();

    /**
     * Loading holograms.yml file
     */
    public static void loadHologramsConfig() {
        hologramsFile = new File(RentableHolograms.getInstance().getDataFolder(), "holograms.yml");

        if (!hologramsFile.exists()) {
            hologramsFile.getParentFile().mkdirs();
            RentableHolograms.getInstance().saveResource("holograms.yml", false);
        }

        try {
            RentableHolograms.getInstance().getLogger().info("Loading holograms.yml");
            hologramsConfig.load(hologramsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloading holograms.yml file
     */
    public static void reloadHologramsConfig() {
        hologramsConfig = YamlConfiguration.loadConfiguration(new File(RentableHolograms.getInstance().getDataFolder(), "holograms.yml"));
    }

    /**
     * Saving holograms.yml file
     */
    public static void saveHologramsConfig() {
        try {
            hologramsConfig.save(hologramsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get holgorams.yml configuration
     * @return
     */
    public static FileConfiguration getHologramsConfig() {
        return hologramsConfig;
    }
}

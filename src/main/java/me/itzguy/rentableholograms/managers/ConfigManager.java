package me.itzguy.rentableholograms.managers;

import lombok.Getter;
import me.itzguy.rentableholograms.RentableHolograms;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    @Getter
    private static int maxDays = 3;
    @Getter
    private static int maxHolograms = 1;
    @Getter
    private static int startingDays = 1;
    @Getter
    private static int lines = 4;
    @Getter
    private static String inputMethod = "CHAT";
    @Getter
    private static List<String> defaultLayout = new ArrayList<>();
    @Getter
    private static List<String> rentedLayout = new ArrayList<>();
    @Getter
    private static String wenhook = "";

    public static void loadConfigSettings() {
        //load all settings
        FileConfiguration config = RentableHolograms.getInstance().getConfig();

        maxDays = config.getInt("settings.max-days");
        startingDays = config.getInt("settings.starting-days");
        lines = config.getInt("settings.lines");
        inputMethod = config.getString("settings.user-input");
        defaultLayout = config.getStringList("holograms-layouts.default");
        rentedLayout = config.getStringList("holograms-layouts.rented");
        wenhook = config.getString("settings.webhook");
        maxHolograms = config.getInt("settings.max-holograms");
    }
}

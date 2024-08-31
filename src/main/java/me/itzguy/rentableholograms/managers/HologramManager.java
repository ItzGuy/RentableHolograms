package me.itzguy.rentableholograms.managers;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.Getter;
import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.commands.MainCommandManager;
import me.itzguy.rentableholograms.entities.HologramObject;
import me.itzguy.rentableholograms.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramManager {

    @Getter
    private static Map<String, HologramObject> hologramObjectMap = new HashMap<>();

    public static void clearAllHolograms() {
        for (HologramObject hologram : hologramObjectMap.values()) {
            hologram.hologram.delete();
        }
    }

    public static void loadAllHolograms() {
        clearAllHolograms();

        hologramObjectMap.clear();

        HologramsConfig.reloadHologramsConfig();


        for (String hologramID : HologramsConfig.getHologramsConfig().getKeys(false)) {
            ConfigurationSection hologramConfigSection = HologramsConfig.getHologramsConfig().getConfigurationSection(hologramID);

            // Checking if the hologram is in correct form
            if (
                    !hologramConfigSection.contains("location.world") ||
                    !hologramConfigSection.contains("location.x") ||
                    !hologramConfigSection.contains("location.y") ||
                    !hologramConfigSection.contains("location.z") ||
                    !hologramConfigSection.contains("owner") ||
                    !hologramConfigSection.contains("price") ||
                    !hologramConfigSection.contains("timestamp") ||
                    !hologramConfigSection.contains("days")
            ) {
                RentableHolograms.getInstance().getLogger().severe("Cant load hologram with id: " + hologramID);
                continue;
            }

            double x = hologramConfigSection.getDouble("location.x");
            double y = hologramConfigSection.getDouble("location.y");
            double z = hologramConfigSection.getDouble("location.z");
            int price = hologramConfigSection.getInt("price");
            int days = hologramConfigSection.getInt("days");
            long timestamp = hologramConfigSection.getLong("timestamp");
            String world = hologramConfigSection.getString("location.world");
            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            String owner = hologramConfigSection.getString("owner");

            UUID name = UUID.randomUUID();
            Hologram hologram = DHAPI.createHologram(name.toString(), location);


            if (hologramConfigSection.contains("lines")) {
                for (String text : ConfigManager.getRentedLayout()) {
                    if (text.contains("%lines%")) {
                        for (String line : hologramConfigSection.getStringList("lines")) {
                            DHAPI.addHologramLine(hologram, StringUtils.color(line));
                        }
                    } else {
                        String playerName;
                        if (owner.equalsIgnoreCase("none"))
                            playerName = "INVALID PLAYER";
                        else if (Bukkit.getPlayer(owner) == null)
                            playerName = owner;
                        else
                            playerName = Bukkit.getPlayer(owner).getName();

                        String translatedText = text.replace("%price%", StringUtils.formatNumber(price))
                                .replace("%player%", playerName)
                                .replace("%id%", hologramID)
                                .replace("%time%", "%timelefthologram_" + hologramID + "%");

                        DHAPI.addHologramLine(hologram, StringUtils.color(translatedText));
                    }
                }
            } else {
                for (String text : ConfigManager.getDefaultLayout()) {
                    String translatedText = text.replace("%price%", StringUtils.formatNumber(price)).replace("%id%", hologramID);
                    DHAPI.addHologramLine(hologram, StringUtils.color(translatedText));
                }
            }

            hologramObjectMap.put(hologramID, new HologramObject(location, hologram, price, days, owner, timestamp, hologramID, name));
        }

        //show identify
        MainCommandManager.getIdentifyManager().fixShow();
    }

    public static void updateHologram(String hologramID) {
        //delete hologram first
        hologramObjectMap.get(hologramID).hologram.delete();
        hologramObjectMap.remove(hologramID);


        //update a single hologram
        ConfigurationSection hologramConfigSection = HologramsConfig.getHologramsConfig().getConfigurationSection(hologramID);

        double x = hologramConfigSection.getDouble("location.x");
        double y = hologramConfigSection.getDouble("location.y");
        double z = hologramConfigSection.getDouble("location.z");
        int price = hologramConfigSection.getInt("price");
        int days = hologramConfigSection.getInt("days");
        long timestamp = hologramConfigSection.getLong("timestamp");
        String world = hologramConfigSection.getString("location.world");
        Location location = new Location(Bukkit.getWorld(world), x, y, z);
        String owner = hologramConfigSection.getString("owner");

        UUID name = UUID.randomUUID();
        Hologram hologram = DHAPI.createHologram(name.toString(), location);

        //hologram lines logic
        if (hologramConfigSection.contains("lines")) {
            for (String text : ConfigManager.getRentedLayout()) {
                if (text.contains("%lines%")) {
                    for (String line : hologramConfigSection.getStringList("lines")) {
                        DHAPI.addHologramLine(hologram, StringUtils.color(line));
                    }
                } else {
                    String playerName;
                    if (owner.equalsIgnoreCase("none"))
                        playerName = "INVALID PLAYER";
                    else if (Bukkit.getPlayer(owner) == null)
                        playerName = owner;
                    else
                        playerName = Bukkit.getPlayer(owner).getName();

                    String translatedText = text.replace("%price%", StringUtils.formatNumber(price))
                            .replace("%player%", playerName)
                            .replace("%id%", hologramID)
                            .replace("%time%", "%timelefthologram_" + hologramID + "%");

                    DHAPI.addHologramLine(hologram, StringUtils.color(translatedText));

                }
            }
        } else {
            for (String text : ConfigManager.getDefaultLayout()) {
                String translatedText = text.replace("%price%", StringUtils.formatNumber(price)).replace("%id%", hologramID);
                DHAPI.addHologramLine(hologram, StringUtils.color(translatedText));
            }
        }
        hologramObjectMap.put(hologramID, new HologramObject(location, hologram, price, days, owner, timestamp, hologramID, name));

        MainCommandManager.getIdentifyManager().fixShow();

    }

    public static void unrentHologram(String id) {
        HologramsConfig.getHologramsConfig().set(id + ".owner", "none");
        HologramsConfig.getHologramsConfig().set(id + ".lines", null);
        HologramsConfig.getHologramsConfig().set(id + ".timestamp", 0);

        HologramsConfig.saveHologramsConfig();
        HologramsConfig.reloadHologramsConfig();

        HologramManager.updateHologram(id);
    }
}

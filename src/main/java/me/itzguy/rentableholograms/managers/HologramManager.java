package me.itzguy.rentableholograms.managers;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import lombok.Getter;
import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.utils.GetUserInput;
import me.itzguy.rentableholograms.utils.StringUtils;
import me.itzguy.rentableholograms.entities.HologramObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HologramManager {

    @Getter
    private static Map<String, HologramObject> hologramObjectMap = new HashMap<>();

    public static void clearAllHolograms() {
        for (Hologram hologram : HologramsAPI.getHolograms(RentableHolograms.getInstance())) {
            hologram.delete();
        }
    }

    public static void loadAllHolograms() {
        hologramObjectMap.clear();

        clearAllHolograms();


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
            String world = hologramConfigSection.getString("location.world");
            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            String owner = hologramConfigSection.getString("owner");


            Hologram hologram = HologramsAPI.createHologram(RentableHolograms.getInstance(), location);


            if (hologramConfigSection.contains("lines")) {
                for (String text : RentableHolograms.getInstance().getConfig().getStringList("holograms-layouts.rented")) {
                    if (text.contains("[lines]")) {
                        for (String line : hologramConfigSection.getStringList("lines")) {
                            TextLine textLine = hologram.appendTextLine(StringUtils.color(line));

                            textLine.setTouchHandler(player -> {
                                controlHologram(player, new HologramObject(location, hologram, price, days, owner, 0));
                            });
                        }
                    } else {
                        String playerName;
                        if (Bukkit.getPlayer(owner) == null)
                            playerName = "INVALID PLAYER";
                        else
                            playerName = Bukkit.getPlayer(owner).getName();

                        String translatedText = text.replace("[price]", StringUtils.formatNumber(price)).replace("[player]", playerName).replace("[id]", hologramID);
                        TextLine textLine = hologram.appendTextLine(StringUtils.color(translatedText));

                        textLine.setTouchHandler(player -> {
                            controlHologram(player, new HologramObject(location, hologram, price, days, owner, 0));
                        });
                    }
                }
            } else {
                for (String text : RentableHolograms.getInstance().getConfig().getStringList("holograms-layouts.default")) {
                    String translatedText = text.replace("[price]", StringUtils.formatNumber(price)).replace("[id]", hologramID);
                    TextLine textLine = hologram.appendTextLine(StringUtils.color(translatedText));

                    textLine.setTouchHandler(player -> {
                        buyHologram(player, new HologramObject(location, hologram, price, RentableHolograms.getInstance().getConfig().getInt("settings.starting-days"), owner, 0));
                    });
                }
            }
        }
    }

    private static void buyHologram(Player player, HologramObject hologramObject) {
        player.sendMessage("buy");
    }

    private static void controlHologram(Player player, HologramObject hologramObject) {
        player.sendMessage("control");
        GetUserInput.getUserInput(player, "&bProvide an input bitch", input -> {
            player.sendMessage("user input is: " + input);
        });
    }
}

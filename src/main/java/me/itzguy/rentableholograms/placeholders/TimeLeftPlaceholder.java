package me.itzguy.rentableholograms.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.itzguy.rentableholograms.entities.HologramObject;
import me.itzguy.rentableholograms.managers.HologramManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public class TimeLeftPlaceholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "timelefthologram";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ItzGuy";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return false;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {

        if (HologramManager.getHologramObjectMap().get(params) == null) {
            return "0";
        }

        HologramObject hologram = HologramManager.getHologramObjectMap().get(params);

        long timeLeftSec = Math.abs((((System.currentTimeMillis() / 1000 ) - hologram.timestamp) - ((long) hologram.days * 60 * 60 * 24)));
        return formatTime(timeLeftSec);
    }


    public static String formatTime(long seconds) {
        long d = TimeUnit.SECONDS.toDays(seconds);

        long h = ((seconds / 60) / 60); //% 24;
        long m = (seconds / 60) % 60;
        long s = seconds % 60;

        String text = "";

        if (h < 10)
            text = text + "0" + h + ":";
        else
            text = text + h + ":";
        if (m < 10)
            text = text + "0" + m + ":";
        else
            text = text + m + ":";
        if (s < 10)
            text = text + "0" + s;
        else
            text = text + s;

        return text;
    }
}

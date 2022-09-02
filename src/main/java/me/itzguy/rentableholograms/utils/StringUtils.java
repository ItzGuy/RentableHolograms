package me.itzguy.rentableholograms.utils;

import me.itzguy.rentableholograms.managers.HologramsConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern hexPatternt = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String color(String text) {
        String version = Bukkit.getVersion();
        if (version.contains("1.16") ||
                version.contains("1.17") ||
                version.contains("1.18") ||
                version.contains("1.19")) {

            Matcher match = hexPatternt.matcher(text);

            while (match.find()) {
                String color = text.substring(match.start(), match.end());

                text = text.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString());

                match = hexPatternt.matcher(text);
            }

            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', text);
        } else {
            return ChatColor.translateAlternateColorCodes('&', text);
        }
    }

    public static List<String> color(List<String> list) {
        List<String> newList = new ArrayList<>();

        for (String text : list) {
            newList.add(color(text));
        }

        return newList;
    }

    public static String formatNumber(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        return decimalFormat.format(number);
    }

    public static Comparable<Integer> convertToInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Comparable<Double> convertToDouble(String number) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String colorIgnoreFormat(String text) {
        if (text.isEmpty())
            throw new IllegalArgumentException("Cannot translate null text list");

        char[] b = text.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfXx".indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static List<String> colorIgnoreFormat(List<String> textList) {
        if (textList.isEmpty())
            throw new IllegalArgumentException("Cannot translate null text list");

        List<String> newTextList = new ArrayList<>();

        for (String text : textList) {
            char[] b = text.toCharArray();
            for (int i = 0; i < b.length - 1; i++) {
                if (b[i] == '&' && "0123456789AaBbCcDdEeFfXx".indexOf(b[i + 1]) > -1) {
                    b[i] = ChatColor.COLOR_CHAR;
                    b[i + 1] = Character.toLowerCase(b[i + 1]);
                }
            }
            newTextList.add(new String(b));
        }



        return newTextList;
    }

    public static int getNextID() {
        int number = 0;

        for (String key : HologramsConfig.getHologramsConfig().getKeys(false)) {
            if (convertToInt(key) == null)
                continue;

            if (number < (int) convertToInt(key))
                number = (int) convertToInt(key);
        }

        return number+1;
    }
}

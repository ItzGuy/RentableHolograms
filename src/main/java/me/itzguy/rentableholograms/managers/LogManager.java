package me.itzguy.rentableholograms.managers;

import me.itzguy.rentableholograms.RentableHolograms;
import org.bukkit.Bukkit;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {

    public static void logToFile(String message) {
        if (ConfigManager.getWenhook() != null && !ConfigManager.getWenhook().isEmpty()) {
            Bukkit.getScheduler().runTaskAsynchronously(RentableHolograms.getInstance(), () -> {
                try {
                    final HttpsURLConnection connection = (HttpsURLConnection) new URL(ConfigManager.getWenhook()).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
                    connection.setDoOutput(true);
                    try (final OutputStream outputStream = connection.getOutputStream()) {
                        outputStream.write(("{\"content\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8));
                    }
                    connection.getInputStream();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            File dataFolder = RentableHolograms.getInstance().getDataFolder();
            if(!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File saveTo = new File(RentableHolograms.getInstance().getDataFolder(), "log.txt");
            if (!saveTo.exists()) {
                saveTo.createNewFile();
            }

            FileWriter fw = new FileWriter(saveTo, true);

            PrintWriter pw = new PrintWriter(fw);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            pw.println(dtf.format(now) + " > " + message);

            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

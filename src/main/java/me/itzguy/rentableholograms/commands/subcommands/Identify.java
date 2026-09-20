package me.itzguy.rentableholograms.commands.subcommands;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.commands.SubCommand;
import me.itzguy.rentableholograms.managers.HologramManager;
import me.itzguy.rentableholograms.managers.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Identify extends SubCommand {

    private Map<UUID, List<Hologram>> hologramsList = new HashMap<>();

    @Override
    public String getName() {
        return "Identify";
    }

    @Override
    public String getDescription() {
        return "Shows a hologram with an ID above them";
    }

    @Override
    public String getSyntax() {
        return "/rentableholograms identify";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rentableholograms.commands.identify")) {
            player.sendMessage(LanguageManager.getMessage("no-permission"));
            return;
        }

        if (hologramsList.containsKey(player.getUniqueId())) {
            hide(player);
        } else {
            show(player);
        }
    }

    public void show(Player player) {
        if (hologramsList.containsKey(player.getUniqueId())) return;

        List<Hologram> hs = new ArrayList<>();

        HologramManager.getHologramObjectMap().forEach((id, hologram) -> {
            Location l = hologram.location.clone();
            l.add(0, 0.3, 0);


            Hologram h = DHAPI.createHologram(UUID.randomUUID().toString(), l);

            h.setDefaultVisibleState(false);
            h.setShowPlayer(player);

            DHAPI.addHologramLine(h, LanguageManager.getMessage("hologram-identify").replace("%id%", id));

            hs.add(h);
        });

        hologramsList.put(player.getUniqueId(), hs);
    }

    public void fixShow() {
        List<UUID> oldList = new ArrayList<>();

        hologramsList.keySet().forEach(uuid -> {
            oldList.add(uuid);
        });

        for (UUID uuid : oldList) {
            Player player = Bukkit.getPlayer(uuid);
            hide(player);
        }

        hologramsList.clear();

        Bukkit.getScheduler().scheduleSyncDelayedTask(RentableHolograms.getInstance(), () -> {
            for (UUID uuid : oldList) {
                Player player = Bukkit.getPlayer(uuid);
                show(player);
            }

        }, 10);
    }

    public void hide(Player player) {
        if (!hologramsList.containsKey(player.getUniqueId())) return;

        hologramsList.get(player.getUniqueId()).forEach(h -> {
            h.delete();
        });

        hologramsList.remove(player.getUniqueId());
    }

    public void hide(UUID uuid) {
        if (!hologramsList.containsKey(uuid)) return;

        hologramsList.get(uuid).forEach(h -> {
            h.delete();
        });

        hologramsList.remove(uuid);
    }

    public Set<UUID> getActiveplayers() {
        return hologramsList.keySet();
    }
}

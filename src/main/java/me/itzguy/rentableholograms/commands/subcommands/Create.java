package me.itzguy.rentableholograms.commands.subcommands;

import me.itzguy.rentableholograms.commands.SubCommand;
import me.itzguy.rentableholograms.managers.HologramManager;
import me.itzguy.rentableholograms.managers.HologramsConfig;
import me.itzguy.rentableholograms.managers.LanguageManager;
import me.itzguy.rentableholograms.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Create extends SubCommand {
    @Override
    public String getName() {
        return "Create";
    }

    @Override
    public String getDescription() {
        return "Create a new hologram";
    }

    @Override
    public String getSyntax() {
        return "/rentableholograms create <price>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rentableholograms.commands.create")) {
            player.sendMessage(LanguageManager.getMessage("no-permission"));
            return;
        }

        if (args.length != 2) {
            player.sendMessage(LanguageManager.getMessage("invalid-args"));
            return;
        }
        String priceString = args[1];

        if (StringUtils.convertToInt(priceString) == null) {
            player.sendMessage(LanguageManager.getMessage("number-required"));
            return;
        }

        int price = (int) StringUtils.convertToInt(priceString);

        Location location = player.getLocation();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String world = location.getWorld().getName();

        int id = StringUtils.getNextID();

        HologramsConfig.getHologramsConfig().set(id + ".price", price);
        HologramsConfig.getHologramsConfig().set(id + ".owner", "none");
        HologramsConfig.getHologramsConfig().set(id + ".timestamp", 0);
        HologramsConfig.getHologramsConfig().set(id + ".days", 0);
        HologramsConfig.getHologramsConfig().set(id + ".location.world", world);
        HologramsConfig.getHologramsConfig().set(id + ".location.x", x);
        HologramsConfig.getHologramsConfig().set(id + ".location.y", y);
        HologramsConfig.getHologramsConfig().set(id + ".location.z", z);

        HologramsConfig.saveHologramsConfig();
        HologramManager.loadAllHolograms();

        player.sendMessage(LanguageManager.getMessage("created-hologram"));
    }

}

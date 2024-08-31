package me.itzguy.rentableholograms.commands.subcommands;

import me.itzguy.rentableholograms.commands.SubCommand;
import me.itzguy.rentableholograms.managers.HologramManager;
import me.itzguy.rentableholograms.managers.HologramsConfig;
import me.itzguy.rentableholograms.managers.LanguageManager;
import me.itzguy.rentableholograms.utils.StringUtils;
import org.bukkit.entity.Player;

public class Delete extends SubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete a hologram";
    }

    @Override
    public String getSyntax() {
        return "/rentableholograms delete <id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rentableholograms.commands.delete")) {
            player.sendMessage(LanguageManager.getMessage("no-permission"));
            return;
        }

        if (args.length != 2) {
            player.sendMessage(LanguageManager.getMessage("invalid-args"));
            return;
        }
        String idString = args[1];

        if (StringUtils.convertToInt(idString) == null) {
            player.sendMessage(LanguageManager.getMessage("number-required"));
            return;
        }

        int id = (int) StringUtils.convertToInt(idString);

        /*HologramsConfig.getHologramsConfig().set(id + ".price", null);
        HologramsConfig.getHologramsConfig().set(id + ".owner", null);
        HologramsConfig.getHologramsConfig().set(id + ".timestamp", null);
        HologramsConfig.getHologramsConfig().set(id + ".days", null);
        HologramsConfig.getHologramsConfig().set(id + ".lines", null);
        HologramsConfig.getHologramsConfig().set(id + ".location", null);
        HologramsConfig.getHologramsConfig().set(id + ".location.world", null);
        HologramsConfig.getHologramsConfig().set(id + ".location.x", null);
        HologramsConfig.getHologramsConfig().set(id + ".location.y", null);
        HologramsConfig.getHologramsConfig().set(id + ".location.z", null);*/

        HologramsConfig.getHologramsConfig().set(id + "", null);

        HologramsConfig.saveHologramsConfig();
        HologramManager.loadAllHolograms();
    }
}

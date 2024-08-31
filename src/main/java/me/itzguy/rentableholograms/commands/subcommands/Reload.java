package me.itzguy.rentableholograms.commands.subcommands;

import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.commands.MainCommandManager;
import me.itzguy.rentableholograms.commands.SubCommand;
import me.itzguy.rentableholograms.managers.*;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {
    @Override
    public String getName() {
        return "Reload";
    }

    @Override
    public String getDescription() {
        return "Reloads all configs and holograms";
    }

    @Override
    public String getSyntax() {
        return "/rentableholograms reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("rentableholograms.commands.reload")) {
            player.sendMessage(LanguageManager.getMessage("no-permission"));
            return;
        }
        MainCommandManager.getIdentifyManager().fixShow();

        RentableHolograms.getInstance().reloadConfig();
        ConfigManager.loadConfigSettings();
        HologramsConfig.reloadHologramsConfig();
        LanguageManager.reloadMessages();
        HologramManager.loadAllHolograms();
        BlacklistManager.reloadBlacklist();
    }
}

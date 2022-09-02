package me.itzguy.rentableholograms.commands.subcommands;

import me.itzguy.rentableholograms.RentableHolograms;
import me.itzguy.rentableholograms.commands.SubCommand;
import me.itzguy.rentableholograms.managers.HologramManager;
import me.itzguy.rentableholograms.managers.HologramsConfig;
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
        RentableHolograms.getInstance().reloadConfig();
        HologramsConfig.reloadHologramsConfig();
        HologramManager.loadAllHolograms();
    }
}

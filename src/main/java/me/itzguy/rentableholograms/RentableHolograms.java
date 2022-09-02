package me.itzguy.rentableholograms;

import lombok.Getter;
import me.itzguy.rentableholograms.commands.MainCommandManager;
import me.itzguy.rentableholograms.managers.HologramManager;
import me.itzguy.rentableholograms.managers.HologramsConfig;
import me.itzguy.rentableholograms.managers.LanguageManager;
import me.itzguy.rentableholograms.utils.GetUserInput;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class RentableHolograms extends JavaPlugin {

    @Getter
    private static RentableHolograms instance;

    @Getter
    private Economy econ = null;

    @Override
    public void onEnable() {
        instance = this;

        // Loading all config files
        saveDefaultConfig();
        LanguageManager.loadMessages();
        HologramsConfig.loadHologramsConfig();



        // Checking dependencies
        if (!setupEconomy()) {
            getLogger().severe("Missing dependency Vault!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("Missing dependency HolographicDisplay!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }



        // Registering listeners



        // Registering commands
        MainCommandManager mainCommandManager = new MainCommandManager();
        getCommand("rentableholograms").setExecutor(mainCommandManager);
        getCommand("rentableholograms").setTabCompleter(mainCommandManager);
        Bukkit.getPluginManager().registerEvents(new GetUserInput(), this);
        mainCommandManager.loadSubCommands();


        // Starting tasks and loading holograms
        HologramManager.loadAllHolograms();
    }

    @Override
    public void onDisable() {
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}

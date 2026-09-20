package me.itzguy.rentableholograms;

import lombok.Getter;
import me.itzguy.rentableholograms.commands.MainCommandManager;
import me.itzguy.rentableholograms.listeners.ClickHologramListener;
import me.itzguy.rentableholograms.listeners.ClickInventoryListener;
import me.itzguy.rentableholograms.managers.*;
import me.itzguy.rentableholograms.placeholders.TimeLeftPlaceholder;
import me.itzguy.rentableholograms.utils.GetUserInput;
import me.itzguy.rentableholograms.utils.TickLoop;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class RentableHolograms extends JavaPlugin {

    @Getter
    private static RentableHolograms instance;

    @Getter
    private Economy econ = null;

    private int loopID = -1;

    @Override
    public void onEnable() {
        instance = this;

        // Loading all config files
        saveDefaultConfig();
        ConfigManager.loadConfigSettings();
        LanguageManager.loadMessages();
        HologramsConfig.loadHologramsConfig();
        BlacklistManager.loadBlacklist();



        // Checking dependencies
        if (!setupEconomy()) {
            getLogger().severe("Missing dependency Vault!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("DecentHolograms")) {
            getLogger().severe("Missing dependency DecentHolograms!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }



        // Registering listeners
        Bukkit.getPluginManager().registerEvents(new ClickInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickHologramListener(), this);


        // Registering commands
        MainCommandManager mainCommandManager = new MainCommandManager();
        getCommand("rentableholograms").setExecutor(mainCommandManager);
        getCommand("rentableholograms").setTabCompleter(mainCommandManager);
        Bukkit.getPluginManager().registerEvents(new GetUserInput(), this);
        mainCommandManager.loadSubCommands();


        // Starting tasks and loading holograms
        HologramManager.loadAllHolograms();

        //Load placeholders
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new TimeLeftPlaceholder().register();
        }

        int pluginId = 34174;
        Metrics metrics = new Metrics(this, pluginId);

        loopID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TickLoop(), 300, 20);
    }

    @Override
    public void onDisable() {
        if (loopID != -1)
            Bukkit.getScheduler().cancelTask(loopID);

        HologramManager.clearAllHolograms();

        MainCommandManager.getIdentifyManager().getActiveplayers().forEach(uuid -> {MainCommandManager.getIdentifyManager().hide(uuid);});
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

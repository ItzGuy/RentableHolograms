package me.itzguy.rentableholograms.listeners;

import me.itzguy.rentableholograms.commands.MainCommandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        MainCommandManager.getIdentifyManager().hide(event.getPlayer());
    }
}

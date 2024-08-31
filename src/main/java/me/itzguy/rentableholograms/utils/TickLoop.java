package me.itzguy.rentableholograms.utils;

import me.itzguy.rentableholograms.managers.HologramManager;

public class TickLoop implements Runnable {

    @Override
    public void run() {
        GetUserInput.getPlayersTitles().forEach((player, titleObject) -> {
            //send title
            player.sendTitle(
                    StringUtils.color(titleObject.title),
                    StringUtils.color(titleObject.subTitle),
                    titleObject.fadeIn,
                    titleObject.stay,
                    titleObject.fadeOut
            );
        });

        HologramManager.getHologramObjectMap().forEach((id, hologram) -> {
            if (hologram.timestamp != 0) {
                if (((System.currentTimeMillis() / 1000) - hologram.timestamp) > ((long) hologram.days * 60 * 60 * 24)) {
                    HologramManager.unrentHologram(id);
                }
            }
        });
    }
}

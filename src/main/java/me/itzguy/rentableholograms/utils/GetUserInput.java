package me.itzguy.rentableholograms.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.function.Consumer;

public class GetUserInput implements Listener {

    private static HashMap<Player, Consumer> playersInputs = new HashMap<>();
    private static HashMap<Player, Integer> playersTitles = new HashMap<>();

    public static void getUserInput(Player player, String message, Consumer<String> consumer) {
        playersInputs.put(player, consumer);
        playersTitles.put(player, 0);

        //title(player, "", message);
    }

    @EventHandler
    public void onChatInput(AsyncPlayerChatEvent event) {
        if (!playersInputs.containsKey(event.getPlayer())) return;

        String input = event.getMessage();
        Consumer consumer = playersInputs.get(event.getPlayer());
        consumer.accept(input);

        playersInputs.remove(event.getPlayer());
        playersTitles.remove(event.getPlayer());

        event.getPlayer().resetTitle();
    }

    @EventHandler
    public void onHit(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.LEFT_CLICK_AIR) || !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;

        if (!playersInputs.containsKey(event.getPlayer())) return;

        playersInputs.remove(event.getPlayer());
        playersTitles.remove(event.getPlayer());

        event.getPlayer().resetTitle();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!playersInputs.containsKey(event.getPlayer())) return;

        playersInputs.remove(event.getPlayer());
        playersTitles.remove(event.getPlayer());

        event.getPlayer().resetTitle();
    }

    private static void title(Player player, String title, String subTitle) {
        player.sendTitle(StringUtils.color(title), StringUtils.color(subTitle), 0, 65, 0);
    }
}

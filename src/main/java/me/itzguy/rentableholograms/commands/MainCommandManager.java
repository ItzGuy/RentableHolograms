package me.itzguy.rentableholograms.commands;

import lombok.Getter;
import me.itzguy.rentableholograms.commands.subcommands.Create;
import me.itzguy.rentableholograms.commands.subcommands.Delete;
import me.itzguy.rentableholograms.commands.subcommands.Identify;
import me.itzguy.rentableholograms.commands.subcommands.Reload;
import me.itzguy.rentableholograms.managers.LanguageManager;
import me.itzguy.rentableholograms.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommandManager implements CommandExecutor, TabCompleter {

    @Getter
    private static ArrayList<SubCommand> subcommands = new ArrayList<>();
    @Getter
    private static Identify identifyManager;

    public void loadSubCommands() {
        identifyManager = new Identify();

        getSubcommands().add(new Create());
        getSubcommands().add(new Delete());
        getSubcommands().add(new Reload());
        getSubcommands().add(identifyManager);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command is for player usage only!");
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("rentableholograms.commands.rentableholograms")) {
            player.sendMessage(LanguageManager.getMessage("no-permission"));
            return false;
        }

        if (args.length > 0) {
            for (int i = 0; i < getSubcommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                    getSubcommands().get(i).perform(player, args);
                    return true;
                }
            }

            player.sendMessage(StringUtils.color("&e---------------------------------"));
            for (int i = 0; i < getSubcommands().size(); i++) {
                player.sendMessage(StringUtils.color("&7" + getSubcommands().get(i).getSyntax() + " &8-&7 " + getSubcommands().get(i).getDescription()));
            }
            player.sendMessage(StringUtils.color("&e---------------------------------"));
        } else {
            player.sendMessage(StringUtils.color("&e---------------------------------"));
            for (int i = 0; i < getSubcommands().size(); i++) {
                player.sendMessage(StringUtils.color("&7" + getSubcommands().get(i).getSyntax() + " &8-&7 " + getSubcommands().get(i).getDescription()));
            }
            player.sendMessage(StringUtils.color("&e---------------------------------"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < getSubcommands().size(); i++){
            if (getSubcommands().get(i).getName().toLowerCase().startsWith(args[0].toLowerCase()))
                list.add(getSubcommands().get(i).getName());
        }

        if (args.length == 1)
            return list;
        else
            return null;
    }
}

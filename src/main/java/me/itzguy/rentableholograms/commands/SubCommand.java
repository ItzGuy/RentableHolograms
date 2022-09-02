package me.itzguy.rentableholograms.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    /**
     * The subcommand name or alias
     * @return name
     */
    public abstract String getName();

    /**
     * Subcommand description
     * @return description
     */
    public abstract String getDescription();

    /**
     * Command args after subcommand
     * @return arguments
     */
    public abstract String getSyntax();

    /**
     * Perform actions when subcommand is executed
     * @param player the player that executed the subcommand
     * @param args command args after subcommand
     */
    public abstract void perform(Player player, String args[]);
}

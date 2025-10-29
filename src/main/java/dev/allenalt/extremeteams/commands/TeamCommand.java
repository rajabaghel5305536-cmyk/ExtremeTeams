package dev.allenalt.extremeteams.commands;

import dev.allenalt.extremeteams.ExtremeTeams;
import dev.allenalt.extremeteams.gui.ConfirmMenu;
import dev.allenalt.extremeteams.manager.TeamManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

    private final ExtremeTeams plugin;
    private final TeamManager manager;

    public TeamCommand(ExtremeTeams plugin) {
        this.plugin = plugin;
        this.manager = plugin.getTeamManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§e/team <create|join|leave|disband|invite>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create" -> {
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /team create <name>");
                    return true;
                }
                String name = args[1];
                new ConfirmMenu(player, name).open();
            }

            case "join" -> {
                player.sendMessage("§aJoining team... (to be implemented)");
            }

            case "leave" -> {
                if (manager.leaveTeam(player))
                    player.sendMessage("§aYou left your team.");
                else
                    player.sendMessage("§cYou are not in a team!");
            }

            case "disband" -> {
                if (manager.disbandTeam(player))
                    player.sendMessage("§cYour team was disbanded.");
                else
                    player.sendMessage("§cYou don’t own a team!");
            }

            case "invite" -> {
                player.sendMessage("§eInviting feature coming soon...");
            }

            default -> player.sendMessage("§e/team <create|join|leave|disband|invite>");
        }
        return true;
    }
}

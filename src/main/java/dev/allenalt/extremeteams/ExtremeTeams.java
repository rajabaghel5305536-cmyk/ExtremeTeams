package dev.allenalt.extremeteams;

import dev.allenalt.extremeteams.commands.TeamCommand;
import dev.allenalt.extremeteams.manager.TeamManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtremeTeams extends JavaPlugin {

    private static ExtremeTeams instance;
    private TeamManager teamManager;

    @Override
    public void onEnable() {
        instance = this;
        teamManager = new TeamManager();

        getCommand("team").setExecutor(new TeamCommand(this));
        getLogger().info("ExtremeTeams v29.9.25 enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ExtremeTeams disabled.");
    }

    public static ExtremeTeams getInstance() {
        return instance;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }
}

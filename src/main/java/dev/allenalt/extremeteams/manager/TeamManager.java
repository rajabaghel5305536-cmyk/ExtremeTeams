package dev.allenalt.extremeteams.manager;

import org.bukkit.entity.Player;
import java.util.*;

public class TeamManager {

    private final Map<String, Set<UUID>> teams = new HashMap<>();
    private final Map<UUID, String> playerTeams = new HashMap<>();

    public boolean createTeam(Player creator, String name) {
        if (teams.containsKey(name.toLowerCase())) return false;
        teams.put(name.toLowerCase(), new HashSet<>(Collections.singletonList(creator.getUniqueId())));
        playerTeams.put(creator.getUniqueId(), name.toLowerCase());
        return true;
    }

    public boolean joinTeam(Player player, String name) {
        String lower = name.toLowerCase();
        if (!teams.containsKey(lower)) return false;
        teams.get(lower).add(player.getUniqueId());
        playerTeams.put(player.getUniqueId(), lower);
        return true;
    }

    public boolean leaveTeam(Player player) {
        String team = playerTeams.remove(player.getUniqueId());
        if (team == null) return false;
        Set<UUID> members = teams.get(team);
        members.remove(player.getUniqueId());
        if (members.isEmpty()) teams.remove(team);
        return true;
    }

    public boolean disbandTeam(Player player) {
        String team = playerTeams.get(player.getUniqueId());
        if (team == null) return false;
        Set<UUID> members = teams.remove(team);
        if (members != null) {
            for (UUID uuid : members) playerTeams.remove(uuid);
        }
        return true;
    }

    public String getPlayerTeam(Player player) {
        return playerTeams.get(player.getUniqueId());
    }

    public boolean teamExists(String name) {
        return teams.containsKey(name.toLowerCase());
    }
}

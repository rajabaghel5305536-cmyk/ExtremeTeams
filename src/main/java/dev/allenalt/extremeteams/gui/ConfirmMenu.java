package dev.allenalt.extremeteams.gui;

import dev.allenalt.extremeteams.ExtremeTeams;
import dev.allenalt.extremeteams.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;

public class ConfirmMenu implements Listener {

    private final Player player;
    private final String teamName;
    private final Inventory menu;
    private final TeamManager manager;

    public ConfirmMenu(Player player, String teamName) {
        this.player = player;
        this.teamName = teamName;
        this.manager = ExtremeTeams.getInstance().getTeamManager();
        this.menu = Bukkit.createInventory(null, 9, "§8Confirm for §e" + teamName);

        ItemStack confirm = createItem(Material.LIME_CONCRETE, "§a§l§nCONFIRM ACTION",
                "", "§7Confirm creation of team §e" + teamName, "", "§eClick to Confirm");

        ItemStack cancel = createItem(Material.RED_CONCRETE, "§c§l§nCANCEL ACTION",
                "", "§7Cancel team creation", "", "§eClick to Cancel");

        menu.setItem(3, confirm);
        menu.setItem(5, cancel);

        Bukkit.getPluginManager().registerEvents(this, ExtremeTeams.getInstance());
    }

    public void open() {
        player.openInventory(menu);
    }

    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(java.util.Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(menu)) return;
        e.setCancelled(true);
        if (!e.getWhoClicked().equals(player)) return;

        switch (e.getSlot()) {
            case 3 -> {
                if (manager.createTeam(player, teamName)) {
                    player.sendMessage("§aTeam §e" + teamName + " §acreated successfully!");
                } else {
                    player.sendMessage("§cTeam already exists!");
                }
                player.closeInventory();
                InventoryClickEvent.getHandlerList().unregister((Plugin) ExtremeTeams.getInstance());
            }
            case 5 -> {
                player.sendMessage("§cTeam creation canceled.");
                player.closeInventory();
                InventoryClickEvent.getHandlerList().unregister((Plugin) ExtremeTeams.getInstance());
            }
        }
    }
}

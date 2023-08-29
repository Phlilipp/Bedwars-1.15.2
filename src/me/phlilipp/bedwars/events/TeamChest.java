package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamChest implements Listener {

    @EventHandler
    public void onEnderChestClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
            e.getPlayer().openInventory(Model.getTeam(e.getPlayer()).getTeamChest());
            e.setCancelled(true);
        }
    }
}

package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class VillagerStuff implements Listener {

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            e.getPlayer().openInventory(Model.getTeam(e.getPlayer()).getShop().getMainmenu());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onVillagerHit(EntityDamageEvent e) {
        if (e.getEntity().getType().equals(EntityType.VILLAGER)) {
            System.out.println("Villager Hit");
            e.setCancelled(true);
        }
    }
}

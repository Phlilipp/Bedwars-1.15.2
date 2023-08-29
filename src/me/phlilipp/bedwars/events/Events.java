package me.phlilipp.bedwars.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {

    @EventHandler
    public void onShulkerClick(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getClickedBlock().getType().equals(Material.YELLOW_SHULKER_BOX)) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                    "effect give " + e.getPlayer().getDisplayName() + " minecraft:levitation 5 3");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onVoidFall(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            e.setDamage(50);
        }
    }

    @EventHandler
    public void onBarrelClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.BARREL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBedClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType().equals(Material.LIME_BED) || e.getClickedBlock().getType().equals(Material.RED_BED) ||
                e.getClickedBlock().getType().equals(Material.BLUE_BED) || e.getClickedBlock().getType().equals(Material.YELLOW_BED))) {
            if (!e.getPlayer().isSneaking()) {
                e.setCancelled(true);
            }
        }
    }
}

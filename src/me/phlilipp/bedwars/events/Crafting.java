package me.phlilipp.bedwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class Crafting implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        e.setCancelled(true);
    }
}

package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TNT implements Listener {

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent e) {
        if (e.getEntity().getType().equals(EntityType.PRIMED_TNT) && e.blockList().size() > 0) {
            Object[] c = e.blockList().toArray();
            e.blockList().clear();
            for (int i = 0; i < c.length; i++) {
                if(c[i] instanceof Block) {
                    Block b = (Block) c[i];
                    if (Model.blockIsBreakable(b.getType())) {
                        e.blockList().add(b);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTNTPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.TNT)) {
            Location loc = e.getBlock().getLocation();
            e.getBlock().setType(Material.AIR);
            Bukkit.getWorld("world").spawn(loc.add(0.5, 0, 0.5), TNTPrimed.class);
        }
    }
}

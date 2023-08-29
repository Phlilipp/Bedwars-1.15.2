package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PvP implements Listener {

    @EventHandler
    public void onHitInLobby(EntityDamageEvent e) {
        if (e.getEntity().getType().equals(EntityType.PLAYER) && !Model.hasStarted()) {
            System.out.println("Player hit while game hasn't started");
            e.setCancelled(true);
        }
    }
}

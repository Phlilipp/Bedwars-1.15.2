package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Model.removePlayerFromTeams(p);
    }
}

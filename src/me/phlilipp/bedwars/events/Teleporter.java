package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import me.phlilipp.bedwars.game.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Teleporter implements Listener {

    @EventHandler
    public void onBlackShulkerClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.BLACK_SHULKER_BOX)) {
            e.getPlayer().sendMessage(ChatColor.RED + "Der Teleporter ist noch deaktiviert. Aktiviere ihn im Shop!");
            Bukkit.getWorld("world").spawnParticle(Particle.SMOKE_NORMAL, e.getClickedBlock().getLocation().add(0.5, 0.5 ,0.5), 50);
            Bukkit.getWorld("world").playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 1);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWhiteShulkerClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.WHITE_SHULKER_BOX)) {
            Player p = e.getPlayer();
            Team team = Model.getTeam(p);
            if (team.getShulkerBox().equals(e.getClickedBlock().getLocation())) {
                Bukkit.getServer().getWorld("world").getBlockAt(team.getShulkerTeleport()).setType(Material.AIR);
                Bukkit.getServer().getWorld("world").getBlockAt(team.getShulkerTeleport().clone().add(0,1,0)).setType(Material.AIR);
                p.teleport(team.getShulkerTeleport());
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            } else {
                Location blockLoc = e.getClickedBlock().getLocation();
                Bukkit.getWorld("world").spawnParticle(Particle.SMOKE_NORMAL, blockLoc.add(0.5, 0.5, 0.5), 50);
                Bukkit.getWorld("world").playSound(blockLoc, Sound.BLOCK_ANVIL_BREAK, 100, 1);
                p.sendMessage(ChatColor.RED + "Du kannst den gegnerischen Teleporter nicht verwenden!");
            }
            e.setCancelled(true);
        }
    }
}

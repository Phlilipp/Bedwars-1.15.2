package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import me.phlilipp.bedwars.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Map implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Material m = e.getBlock().getType();
        Team teamPlayer = Model.getTeam(p);
        switch (m) {
            case RED_BED:
                bedBreak(Model.getTeam(0), teamPlayer, p, e);
                break;
            case LIME_BED:
                bedBreak(Model.getTeam(1), teamPlayer, p, e);
                break;
            case BLUE_BED:
                bedBreak(Model.getTeam(2), teamPlayer, p, e);
                break;
            case YELLOW_BED:
                bedBreak(Model.getTeam(3), teamPlayer, p, e);
                break;
            case SANDSTONE:
            case END_STONE:
            case OBSIDIAN:
            case NETHER_BRICKS:
            case COBWEB:
                break;
            default:
                if (Model.getIsUnbreakable()) {
                    e.setCancelled(true);
                }
                break;
        }
    }

    private void bedBreak(Team teamBed, Team teamPlayer, Player p, BlockBreakEvent e) {
        if (teamBed.isInTeam(p)) {
            double d = Math.random();
            if (d < 0.2) {
                p.sendMessage(ChatColor.RED + "Idiot.");
            } else {
                p.sendMessage(ChatColor.RED + "Du kannst dein eigenes Bett nicht abbauen!");
            }
            e.setCancelled(true);
        } else {
            Bukkit.getServer().broadcastMessage("Das Bett von " + teamBed.getChatColor() + teamBed.getName() + ChatColor.WHITE +
                    " wurde von " + teamPlayer.getChatColor() + p.getDisplayName() + ChatColor.WHITE + " abgebaut!");
            p.teleport(teamPlayer.getSpawnpoint());
            teamBed.reduceBedLives(1);
            if (teamBed.getBedLives() > 0) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSoilJump(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType().equals(Material.FARMLAND)) {
            e.setCancelled(true);
        }
    }
}

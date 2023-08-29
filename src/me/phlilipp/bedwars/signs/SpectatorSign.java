package me.phlilipp.bedwars.signs;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpectatorSign implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(1).equalsIgnoreCase("spectator")) {
            e.setLine(1, ChatColor.GRAY + "Join Spectator");
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            Sign s = (Sign) e.getClickedBlock().getState();
            if (s.getLine(1).equals(ChatColor.GRAY + "Join Spectator")) {
                Model.removePlayerFromTeams(e.getPlayer());
            }
        }
    }
}

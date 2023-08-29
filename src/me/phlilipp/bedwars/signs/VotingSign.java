package me.phlilipp.bedwars.signs;

import me.phlilipp.bedwars.Main;
import me.phlilipp.bedwars.game.Map;
import me.phlilipp.bedwars.game.Model;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class VotingSign implements Listener {
    private static Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(0).length() == 2) {
            if (e.getLine(0).charAt(0) == 'v') {
                if (isNumeric(e.getLine(0).charAt(1))) {
                    int i = Integer.parseInt(e.getLine(0).charAt(1) + "");
                    if (i < Model.getMaps().length && i >= 0) {
                        Map map = Model.getMap(i);
                        e.setLine(0, ChatColor.AQUA + "MapVote");
                        e.setLine(1, map.getName());
                        e.setLine(2, "Votes: " + map.getVotes());
                        e.setLine(3, ChatColor.WHITE + "L-Click for View");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignRightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            Sign sign = (Sign) e.getClickedBlock().getState();
            if (sign.getLine(0).equals(ChatColor.AQUA + "MapVote")) {
                Map map = Model.getMap(sign.getLine(1));
                if (map != null) {
                    Player p = e.getPlayer();
                    if (map.addVoter(p, null)) {
                        Model.removeVoterFromMap(p);
                        map.addVoter(p,sign);
                        p.sendMessage(ChatColor.GREEN + "Du hast für die Map " + map.getName() + " gestimmt.");
                        sign.setLine(2, "Votes: " + map.getVotes());
                        sign.update();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignLeftClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            if (!Model.getIsUnbreakable()) {
                return;
            }
            Sign sign = (Sign) e.getClickedBlock().getState();
            if (sign.getLine(0).equals(ChatColor.AQUA + "MapVote")) {
                Map map = Model.getMap(sign.getLine(1));
                if (map != null) {
                    Player p = e.getPlayer();
                    p.teleport(map.getSpectatorSpawn());
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode spectator " + p.getDisplayName());
                    p.sendMessage(ChatColor.AQUA + "Du hast 15 Sekunden Zeit, dir die Map anzuschauen.");

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            if (!Model.hasStarted()) {
                                p.teleport(Model.worldspawn);
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode survival " + p.getDisplayName());
                            }
                        }
                    }, 20*15);
                    e.setCancelled(true);
                }
            }
        }
    }

    private boolean isNumeric(char c) {
        try {
            Integer.parseInt(c + "");
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

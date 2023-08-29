package me.phlilipp.bedwars.signs;

import me.phlilipp.bedwars.game.Model;
import me.phlilipp.bedwars.game.Team;
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

public class TeamSign implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(0).length() == 2) {
            if (e.getLine(0).charAt(0) == 't') {
                if (isNumeric(e.getLine(0).charAt(1))) {
                    int i = Integer.parseInt(e.getLine(0).charAt(1) + "");
                    if (i < 4 && i >= 0) {
                        Team team = Model.getTeam(i);
                        e.setLine(0, ChatColor.AQUA + "Bedwars");
                        e.setLine(1, "Join " + team.getChatColor() + team.getName());
                        e.setLine(2, team.getPlayerInTeam() + "/" + team.getMember().length);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            Sign s = (Sign) e.getClickedBlock().getState();
            if (s.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "Bedwars")) {
                String name = s.getLine(1).substring(7, s.getLine(1).length());
                Team team = Model.getTeam(name);
                if (team != null) {
                    Player p = e.getPlayer();
                    if (team.addPlayer(p, null)) {
                        Model.removePlayerFromTeams(p);
                        team.addPlayer(p, s);
                        p.sendTitle(team.getChatColor() + team.getIngameName().toUpperCase(), "Du bist dem Team beigetreten", 10, 65, 20);
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                                "summon minecraft:area_effect_cloud " + team.getCloudKoords() +
                                        " {Radius:2,Duration:50,Color:" + team.getCloudColor() + "}");
                        s.setLine(2, team.getPlayerInTeam() + "/" + team.getMember().length);
                        s.update();
                    }
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

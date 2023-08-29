package me.phlilipp.bedwars.signs;

import me.phlilipp.bedwars.Main;
import me.phlilipp.bedwars.game.Map;
import me.phlilipp.bedwars.game.Model;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class StartSign implements Listener {
    private static Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            Sign s = (Sign) e.getClickedBlock().getState();
            if(s.getLine(1).equalsIgnoreCase(ChatColor.DARK_GREEN + "[Start]")) {
                if(Model.hasStarted()) {
                    return;
                }
                Model.setHasStarted(true);
                Model.finishVoting();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                        "title @a subtitle {\"text\":\"Spiel startet in 5 Sekunden\",\"color\":\"white\"}");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                        "title @a title {\"text\":\"Map: " + Model.getVotedMap().getName() + "\",\"color\":\"aqua\"}");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "start");
                    }
                }, 105);
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(1) != null && e.getLine(1).equalsIgnoreCase("start")) {
            e.setLine(1, ChatColor.DARK_GREEN + "[Start]");
        }
    }
}

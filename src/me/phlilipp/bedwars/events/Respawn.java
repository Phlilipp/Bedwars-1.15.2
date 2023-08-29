package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import me.phlilipp.bedwars.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class Respawn implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Team team = Model.getTeam(p);
        if (team == null) {
            e.setRespawnLocation(Model.getSpectatorSpawn());
            return;
        }
        e.setRespawnLocation(team.getSpawnpoint());
        if (team.getBedLives() <= 0) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode spectator " + p.getDisplayName());
            p.setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Object[] temp = e.getDrops().toArray();
        e.getDrops().clear();
        for (Object o : temp) {
            if (o instanceof ItemStack) {
                ItemStack item = (ItemStack) o;
                if (Model.isCurrency(item)) {
                    e.getDrops().add(item);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode survival " + p.getDisplayName());
        if (p.getDisplayName().equalsIgnoreCase("Phlilipp")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"worldborder add 2000");
        }
        p.teleport(Model.worldspawn);
        p.getInventory().clear();
    }
}

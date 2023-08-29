package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Hunger implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (Model.getIsUnbreakable()) {
            System.out.println(ChatColor.RED + "HUNGER");
            e.setCancelled(true);
        }
    }
}

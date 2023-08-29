package me.phlilipp.bedwars.commands;

import me.phlilipp.bedwars.Main;
import me.phlilipp.bedwars.game.Model;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StartCommand implements CommandExecutor {
    private static Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String cmd, String[] args) {
        if (commandSender instanceof Player) {
            return false;
        }
        Server s = Bukkit.getServer();
        ConsoleCommandSender c = Bukkit.getConsoleSender();
        World world = s.getWorld("world");

        setupScoreboard();
        Model.setLocations();

        double rand = Math.random();
        int time = rand < 1 ? 6000 : 18000;
        s.dispatchCommand(c, "time set " + time);
        s.dispatchCommand(c, "weather clear");
        s.dispatchCommand(c, "difficulty easy");
        s.dispatchCommand(c, "gamerule announceAdvancements false");
        s.dispatchCommand(c, "gamerule disableElytraMovementCheck true");
        s.dispatchCommand(c, "gamerule doDaylightCycle false");
        s.dispatchCommand(c, "gamerule doEntityDrops false");
        s.dispatchCommand(c, "gamerule doFireTick false");
        s.dispatchCommand(c, "gamerule doImmediateRespawn true");
        s.dispatchCommand(c, "gamerule doWeatherCycle false");
        s.dispatchCommand(c, "worldborder set 229");
        s.dispatchCommand(c, "clear @a");
        s.dispatchCommand(c, "kill @e[type=!minecraft:player,type=!minecraft:villager]");
        s.dispatchCommand(c, "title @a title {\"text\":\"Die Runde beginnt!\",\"color\":\"green\"}");

        Model.setIsUnbreakable(true);
        Model.setupShop();
        Model.teleportAll();
        for (Player p: Bukkit.getWorld("world").getPlayers()) {
            p.playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 100, 1);
        }

        int index = Model.getVotedMap().getIndex();
        //Remove Iron Bars on Map 2
        if (index == 2) {
            s.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    s.dispatchCommand(c, "fill 504 44 1 504 42 -1 minecraft:air");
                    s.dispatchCommand(c, "fill 520 44 -1 520 42 1 minecraft:air");
                    s.dispatchCommand(c, "fill 511 44 -8 513 42 -8 minecraft:air");
                    s.dispatchCommand(c, "fill 513 44 8 511 42 8 minecraft:air");
                    s.getWorld("world").playSound(new Location(s.getWorld("world"), 512.5,42,0.5), Sound.BLOCK_BELL_USE, 100, 1);
                }
            }, 150*20);
        }

        int delta = index * 256;

        s.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack brick = Model.getBronze(1);
                world.dropItem(new Location(world, -70.5 + delta, 41, 0.5), brick);
                world.dropItem(new Location(world, 71.5 + delta, 41, 0.5), brick);
                world.dropItem(new Location(world, 0.5 + delta, 41, 71.5), brick);
                world.dropItem(new Location(world, 0.5 + delta, 41, -70.5), brick);
            }
        }, 20, 20);

        s.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack iron = Model.getIron(1);
                world.dropItem(new Location(world, -70.5 + delta, 41, 0.5), iron);
                world.dropItem(new Location(world, 71.5 + delta, 41, 0.5), iron);
                world.dropItem(new Location(world, 0.5 + delta, 41, 71.5), iron);
                world.dropItem(new Location(world, 0.5 + delta, 41, -70.5), iron);

                //CustomSpawner
                world.dropItem(Model.getVotedMap().getIron1(), iron);
                world.dropItem(Model.getVotedMap().getIron2(), iron);
                world.dropItem(Model.getVotedMap().getIron3(), iron);
                world.dropItem(Model.getVotedMap().getIron4(), iron);

                //Zwischeninseln
                world.dropItem(new Location(world, 0.5 + delta, 42, 39.5), iron);
                world.dropItem(new Location(world, 0.5 + delta, 42, -38.5), iron);
                world.dropItem(new Location(world, 39.5 + delta, 42, 0.5), iron);
                world.dropItem(new Location(world, -38.5 + delta, 42, 0.5), iron);
            }
        }, 200, 200);

        s.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack gold = Model.getGold(1);
                world.dropItem(new Location(world, -70.5 + delta, 41, 0.5), gold);
                world.dropItem(new Location(world, 71.5 + delta, 41, 0.5), gold);
                world.dropItem(new Location(world, 0.5 + delta, 41, 71.5), gold);
                world.dropItem(new Location(world, 0.5 + delta, 41, -70.5), gold);

                world.dropItem(new Location(world, -2.5 + delta, 42.5, 0.5), gold);
                world.dropItem(new Location(world, 3.5 + delta, 42.5, 0.5), gold);
                world.dropItem(new Location(world, 0.5 + delta, 42.5, 3.5), gold);
                world.dropItem(new Location(world, 0.5 + delta, 42.5, -2.5), gold);
            }
        }, 600, 600);
        return true;
    }

    private void setupScoreboard() {
        Server s = Bukkit.getServer();
        CommandSender c = Bukkit.getConsoleSender();
        s.dispatchCommand(c, "scoreboard objectives add Leben dummy");
        s.dispatchCommand(c, "scoreboard objectives setdisplay sidebar Leben");
        Model.setScoreboardLives();
    }
}

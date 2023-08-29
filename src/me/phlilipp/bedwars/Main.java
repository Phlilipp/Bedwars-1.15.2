package me.phlilipp.bedwars;

import me.phlilipp.bedwars.commands.*;
import me.phlilipp.bedwars.events.*;
import me.phlilipp.bedwars.game.Model;
import me.phlilipp.bedwars.signs.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("start").setExecutor(new StartCommand());
        getCommand("test").setExecutor(new TestCommand());
        getCommand("isUnbreakable").setExecutor(new UnbreakableCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());

        Bukkit.getPluginManager().registerEvents(new StartSign(), this);
        Bukkit.getPluginManager().registerEvents(new TeamSign(), this);
        Bukkit.getPluginManager().registerEvents(new SpectatorSign(), this);
        Bukkit.getPluginManager().registerEvents(new VotingSign(), this);

        Bukkit.getPluginManager().registerEvents(new Hunger(), this);
        Bukkit.getPluginManager().registerEvents(new Map(), this);
        Bukkit.getPluginManager().registerEvents(new Respawn(), this);
        Bukkit.getPluginManager().registerEvents(new VillagerStuff(), this);
        Bukkit.getPluginManager().registerEvents(new ShopEvents(), this);
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getPluginManager().registerEvents(new TeamChest(), this);
        Bukkit.getPluginManager().registerEvents(new Crafting(), this);
        Bukkit.getPluginManager().registerEvents(new Teleporter(), this);
        Bukkit.getPluginManager().registerEvents(new Leave(), this);
        Bukkit.getPluginManager().registerEvents(new TNT(), this);
        Bukkit.getPluginManager().registerEvents(new PvP(), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Es lebt!");
    }

    @Override
    public void onDisable() {
        Model.resetTeams();
        Model.resetScoreboard();
        Model.resetSigns();
    }
}

package me.phlilipp.bedwars.game;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Model {
    public static final String T0 = "Team Rot";
    public static final String T1 = "Team Green";
    public static final String T2 = "Team Blau";
    public static final String T3 = "Team Gelb";
    private static boolean isUnbreakable = true;
    private static boolean hasStarted = false;

    private static int bedLives = 2;

    public static Location worldspawn = new Location(Bukkit.getWorld("world"), -138.5, 54, 0.5).setDirection(new Vector(1, 0, 0));
    private static Location spectatorSpawn = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5);

    private static Map[] maps = {
            new Map("Starfleet", 0, getLocation(33.5, 43, 33.5), getLocation(33.5, 43, -32.5),
                    getLocation(-32.5, 43, 33.5), getLocation(-32.5, 43, -32.5),
                    getItem(Material.WHITE_SHULKER_BOX, "Teleporter aktivieren"), getIron(16)),
            new Map("Planets", 1, getLocation(220.5, 49, -34.5), getLocation(220.5, 49, 35.5),
                    getLocation(291.5, 49, -34.5), getLocation(291.5, 49, 35.5),
                    getPotion(PotionType.NIGHT_VISION, PotionEffectTypeWrapper.NIGHT_VISION, "Night Vision", 120, 1, false),
                    getGold(2)),
            new Map("Lopixel", 2, getLocation(505.5, 80, 0.5), getLocation(512.5, 81, -6.5),
                    getLocation(519.5, 80, 0.5), getLocation(512.5, 81, 7.5), new ItemStack(Material.ELYTRA), getGold(12)),
            new Map("City", 3, getLocation(747.5,50,26.5), getLocation(742.5,50,-20.5),
                    getLocation(789.5,50,-25.5), getLocation(794.5,50,21.5), Rakete.getRocket(), getIron(16))};

    private static Map votedMap = maps[0];

    private static Team[] teams = {
            new Team(new Player[4], T0, ChatColor.RED, "red", Color.RED, bedLives,
                    getLocation(0.5, 42, 62.5).setDirection(new Vector(0, 0, -1)), "-126.46 54.00 3.55", 16711680,
                    getLocation(0, 41, 77), getLocation(0.5, 52, 39.5).setDirection(new Vector(0, 0, -1))),
            new Team(new Player[4], T1, ChatColor.GREEN, "green", Color.GREEN, bedLives,
                    getLocation(62.5, 42, 0.5).setDirection(new Vector(-1, 0, 0)), "-120.48 54.00 3.49", 2424576,
                    getLocation(77, 41, 0), getLocation(39.5, 52, 0.5).setDirection(new Vector(-1, 0, 0))),
            new Team(new Player[4], T2, ChatColor.BLUE, "blue", Color.BLUE, bedLives,
                    getLocation(0.5, 42, -61.5).setDirection(new Vector(0, 0, 1)), "-120.43 54.00 -2.53", 2815,
                    getLocation(0, 41, -77), getLocation(0.5, 52, -38.5).setDirection(new Vector(0, 0, 1))),
            new Team(new Player[4], T3, ChatColor.YELLOW, "yellow", Color.YELLOW, bedLives,
                    getLocation(-61.5, 42, 0.5).setDirection(new Vector(1, 0, 0)), "-126.62 54.00 -2.45", 16774400,
                    getLocation(-77, 41, 0), getLocation(-38.5, 52, 0.5).setDirection(new Vector(1, 0, 0)))};

    public static Map getMap(int i) {
        return maps[i];
    }

    public static Map getMap(String name) {
        for (int i = 0; i < maps.length; i++) {
            if (maps[i].getName().equals(name)) {
                return maps[i];
            }
        }
        return null;
    }

    public static Team getTeam(int i) {
        return teams[i];
    }

    public static Location getLocation(double x, double y, double z) {
        return new Location(Bukkit.getWorld("world"),x,y,z);
    }

    public static Team getTeam(String name) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getName().equalsIgnoreCase(name)) {
                return teams[i];
            }
        }
        return null;
    }

    public static void removePlayerFromTeams(Player p) {
        for (int i = 0; i < teams.length; i++) {
            teams[i].removePlayer(p);
        }
    }

    public static void removeVoterFromMap(Player p) {
        for (int i = 0; i < maps.length; i++) {
            maps[i].removePlayer(p);
        }
    }

    public static void teleportAll() {
        for (Player p : Bukkit.getWorld("world").getPlayers()) {
            Team team = getTeam(p);
            if (team != null) {
                p.teleport(team.getSpawnpoint());
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode survival " + p.getDisplayName());
            } else {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamemode spectator " + p.getDisplayName());
                p.teleport(spectatorSpawn);
            }
        }
    }

    public static void resetSigns() {
        for (int i = 0; i < teams.length; i++) {
            teams[i].resetSign();
        }
        for (int i = 0; i < maps.length; i++) {
            maps[i].resetSign();
        }
    }

    public static void resetTeams() {
        for (int i = 0; i < teams.length; i++) {
            teams[i].deleteTeam();
        }
        for (int i = 0; i < maps.length; i++) {
            maps[i].removeAllVoters();
        }
    }

    public static Team getTeam(Player p) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].isInTeam(p)) {
                return teams[i];
            }
        }
        return null;
    }

    public static void printTeams() {
        for (int i = 0; i < teams.length; i++) {
            System.out.println(teams[i]);
        }
    }

    public static void setScoreboardLives() {
        for (int i = 0; i < teams.length; i++) {
            teams[i].setScoreboardLives();
        }
    }

    public static void resetScoreboard() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives remove Leben");
    }

    public static ItemStack getBronze(int amount) {
        ItemStack bronze = new ItemStack(Material.BRICK, amount);
        ItemMeta meta = bronze.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Bronze");
        bronze.setItemMeta(meta);
        return bronze;
    }

    public static ItemStack getIron(int amount) {
        ItemStack iron = new ItemStack(Material.IRON_INGOT, amount);
        ItemMeta meta = iron.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Iron");
        iron.setItemMeta(meta);
        return iron;
    }

    public static ItemStack getGold(int amount) {
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, amount);
        ItemMeta meta = gold.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Gold");
        gold.setItemMeta(meta);
        return gold;
    }

    public static boolean getIsUnbreakable() {
        return isUnbreakable;
    }

    public static void setIsUnbreakable(boolean value) {
        isUnbreakable = value;
    }

    public static boolean blockIsBreakable(Material mat) {
        switch (mat) {
            case SANDSTONE:
            case LADDER:
            case NETHER_BRICKS:
                return true;
            default:
                return false;
        }
    }

    public static ItemStack getShopIndicator() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("Tränke");
        item.setItemMeta(im);
        return item;
    }

    public static Map[] getMaps() {
        return maps;
    }

    public static Map getVotedMap() {
        return votedMap;
    }

    public static void finishVoting() {
        Map[] copy = maps.clone();
        Arrays.sort(copy);
        System.out.println(Arrays.toString(copy));
        double counter = 1;
        while (counter < copy.length && copy[0].getVotes() == copy[(int)counter].getVotes()) {
            counter++;
        }
        System.out.println("Maps mit gleichen Votes: " + counter);
        double random = Math.random();
        System.out.println(random);
        if (random < 1/counter) {
            votedMap = copy[0];
        } else if (random < 2/counter) {
            votedMap = copy[1];
        } else if (random < 3/counter) {
            votedMap = copy[2];
        } else {
            votedMap = copy[3];
        }


//        Map map = maps[0];
//        for (int i = 1; i < maps.length; i++) {
//            if (maps[i].getVotes() > map.getVotes()) {
//                map = maps[i];
//            }
//            if (maps[i].getVotes() == map.getVotes()) {
//                map = Math.random() < 0.5 ? map : maps[i];
//            }
//        }
//        votedMap = map;
    }

    public static void setLocations() {
        for (int i = 0; i < teams.length; i++) {
            teams[i].setSpawnpoint(teams[i].getSpawnpoint().add(256 * getVotedMap().getIndex(),0,0));
        }
        spectatorSpawn.add(256 * getVotedMap().getIndex(),0,0);
        Bukkit.getWorld("world").getWorldBorder().setCenter(spectatorSpawn);
    }

    public static Location getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public static ItemStack getItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getPotion(PotionType type, PotionEffectType wrapper, String name, int seconds, int level, boolean throwable) {
        ItemStack potion;
        if (throwable) {
            potion = new ItemStack(Material.SPLASH_POTION);
        } else {
            potion = new ItemStack(Material.POTION);
        }
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(type));
        Color color = meta.getColor();
        meta.setBasePotionData(new PotionData(PotionType.AWKWARD));
        meta.setColor(color);
        meta.addCustomEffect(wrapper.createEffect(seconds * 20, level - 1), true);
        meta.setDisplayName(name);
        potion.setItemMeta(meta);
        return potion;
    }

    public static boolean hasStarted() {
        return hasStarted;
    }

    public static void setHasStarted(boolean hasStarted) {
        Model.hasStarted = hasStarted;
    }

    public static void setupShop() {
        for (int i = 0; i < teams.length; i++) {
            teams[i].getShop().setupShop();
        }
    }

    public static boolean isCurrency(ItemStack item) {
        int count = item.getAmount();
        if (item.equals(getBronze(count)) || item.equals(getIron(count)) || item.equals(getGold(count))) {
            return true;
        }
        return false;
    }
}

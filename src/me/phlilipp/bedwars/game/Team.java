package me.phlilipp.bedwars.game;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Team {
    private Player[] member;
    private ChatColor chatColor;
    private String colorText;
    private Color color;
    private String name;
    private String ingameName;
    private String sidebarName;
    private Sign sign;
    private int bedLives;
    private Location spawnpoint;
    private Inventory teamChest;
    private String cloudKoords;
    private int cloudColor;
    private Location shulkerBox;
    private Location shulkerTeleport;
    private Shop shop;

    public Team(Player[] member, String name, ChatColor chatColor, String colorText, Color color, int bedLives,
                Location spawnpoint, String cloudKoords, int cloudColor, Location shulkerBox, Location shulkerTeleport) {
        this.member = member;
        this.chatColor = chatColor;
        this.name = name;
        this.ingameName = name.substring(5, name.length());
        this.sidebarName = name.replace("Team ", "Team");
        this.spawnpoint = spawnpoint;
        this.bedLives = bedLives;
        this.teamChest = Bukkit.createInventory(null, 9, sidebarName);
        this.cloudKoords = cloudKoords;
        this.cloudColor = cloudColor;
        this.colorText = colorText;
        this.color = color;
        this.shulkerBox = shulkerBox;
        this.shulkerTeleport = shulkerTeleport;
        this.shop = new Shop(this);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team add " + ingameName);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team modify " + ingameName + " color " + colorText);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team join " + ingameName + " " + sidebarName);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team modify " + ingameName + " friendlyFire false");
    }

    public boolean addPlayer(Player p, Sign s) {
        if (isInTeam(p)) {
            p.sendMessage("Du bist bereits in " + chatColor + name);
            return false;
        }
        for (int i = 0; i < member.length; i++) {
            if (member[i] == null) {
                member[i] = p;
                sign = s;
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team join " + ingameName + " " + p.getDisplayName());
                return true;
            }
        }
        p.sendMessage(chatColor + name + " ist bereits voll.");
        return false;
    }

    public int getPlayerInTeam() {
        int count = 0;
        for (int i = 0; i < member.length; i++) {
            if (member[i] != null) {
                count++;
            }
        }
        return count;
    }

    public boolean isInTeam(Player p) {
        for (int i = 0; i < member.length; i++) {
            if (member[i] != null && member[i].equals(p)) {
                return true;
            }
        }
        return false;
    }

    public void removePlayer(Player p) {
        for (int i = 0; i < member.length; i++) {
            if (p.equals(member[i])) {
                member[i] = null;
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team leave " + p.getDisplayName());
                if (sign != null) {
                    sign.setLine(2, getPlayerInTeam() + "/" + member.length);
                    sign.update();
                }
            }
        }
    }

    public void removeAllPlayer() {
        for (int i = 0; i < member.length; i++) {
            if (member[i] != null) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team leave " + member[i].getDisplayName());
                member[i] = null;
            }
        }
    }

    public void teleportAllToSpawn() {
        for (int i = 0; i < member.length; i++) {
            if (member[i] != null) {
                member[i].teleport(spawnpoint.add(0.5, 0, 0.5));
            }
        }
    }

    public void resetSign() {
        if (sign != null) {
            sign.setLine(2, getPlayerInTeam() + "/" + member.length);
            sign.update();
        }
    }

    public String getName() {
        return name;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Player[] getMember() {
        return member;
    }

    public Location getSpawnpoint() { return spawnpoint;}

    public int getBedLives() {
        return bedLives;
    }

    public void reduceBedLives(int i) {
        bedLives -= i;
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "scoreboard players remove " + sidebarName + " Leben " + i);
    }

    public String toString() {
        String erg = sidebarName + "[";
        for (int i = 0; i < member.length - 1; i++) {
            if (member[i] != null) {
                erg += member[i].getDisplayName() + ", ";
            } else {
                erg += "null, ";
            }
        }
        if (member[member.length-1] != null) {
            erg += member[member.length-1].getDisplayName() + "]";
        } else {
            erg += "null]";
        }
        return erg + " BedLives: " + bedLives;
    }

    public void deleteTeam() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team remove " + ingameName);
        removeAllPlayer();
    }

    public void setScoreboardLives() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "scoreboard players add " + sidebarName + " Leben " + bedLives);
    }

    public Inventory getTeamChest() {
        return teamChest;
    }

    public String getCloudKoords() {
        return cloudKoords;
    }

    public int getCloudColor() {
        return cloudColor;
    }

    public String getIngameName() {
        return ingameName;
    }

    public String getColorText() {
        return colorText;
    }

    public boolean activateTeleporter() {
        Block b = Bukkit.getServer().getWorld("world").getBlockAt(shulkerBox);
        if (b.getType().equals(Material.BLACK_SHULKER_BOX)) {
            b.setType(Material.WHITE_SHULKER_BOX);
            for (int i = 0; i < member.length; i++) {
                if (member[i] != null) {
                    member[i].playSound(member[i].getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                    member[i].sendTitle(chatColor + "Teleporter aktiv", "Dein Team hat den Teleporter freigeschaltet", 10, 65, 20);
                }
            }
            return true;
        }
        return false;
    }

    public Location getShulkerTeleport() {
        return shulkerTeleport;
    }

    public Location getShulkerBox() {
        return shulkerBox;
    }

    public Shop getShop() {
        return shop;
    }

    public void setSpawnpoint(Location spawnpoint) {
        this.spawnpoint = spawnpoint;
    }

    public Color getColor() {
        return color;
    }
}

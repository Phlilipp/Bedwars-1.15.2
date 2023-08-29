package me.phlilipp.bedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Map implements Comparable<Map>{
    private String name;
    private Player[] voters;
    private Sign sign;
    private int index;

    private Location iron1;
    private Location iron2;
    private Location iron3;
    private Location iron4;

    private ItemStack specialItem;
    private ItemStack itemPrice;

    public Map(String name, int index, Location iron1, Location iron2, Location iron3, Location iron4, ItemStack specialItem, ItemStack itemPrice) {
        this.name = name;
        this.index = index;
        this.voters = new Player[8];
        this.iron1 = iron1;
        this.iron2 = iron2;
        this.iron3 = iron3;
        this.iron4 = iron4;
        this.specialItem = specialItem;
        this.itemPrice = itemPrice;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int getVotes() {
        int erg = 0;
        for (int i = 0; i < voters.length; i++) {
            if (voters[i] != null) {
                erg++;
            }
        }
        return erg;
    }

    public boolean addVoter(Player p, Sign s) {
        if (hasVoted(p)) {
            p.sendMessage("Du hast bereits für " + name + " gestimmt");
            return false;
        }
        for (int i = 0; i < voters.length; i++) {
            if (voters[i] == null) {
                voters[i] = p;
                sign = s;
                return true;
            }
        }
        return false;
    }

    public boolean hasVoted(Player p ) {
        for (int i = 0; i < voters.length; i++) {
            if (p.equals(voters[i])) {
                return true;
            }
        }
        return false;
    }

    public void resetSign() {
        if (sign != null) {
            sign.setLine(2, "Votes: " + getVotes());
            sign.update();
        }
    }

    public void removePlayer(Player p) {
        for (int i = 0; i < voters.length; i++) {
            if (p.equals(voters[i])) {
                voters[i] = null;
                if (sign != null) {
                    sign.setLine(2, "Votes: " + getVotes());
                    sign.update();
                }
            }
        }
    }

    public void removeAllVoters() {
        for (int i = 0; i < voters.length; i++) {
            voters[i] = null;
        }
    }

    public Location getIron1() {
        return iron1;
    }

    public Location getIron2() {
        return iron2;
    }

    public Location getIron3() {
        return iron3;
    }

    public Location getIron4() {
        return iron4;
    }

    public ItemStack getSpecialItem() {
        if (specialItem == null) {
            return new ItemStack(Material.AIR);
        }
        return specialItem;
    }

    public ItemStack getItemPrice() {
        if (specialItem == null) {
            return new ItemStack(Material.AIR);
        }
        return itemPrice;
    }

    public Location getSpectatorSpawn() {
        return new Location(Bukkit.getWorld("world"), index * 256, 80, 0);
    }

    @Override
    public int compareTo(Map map) {
        return map.getVotes() - this.getVotes();
    }

    @Override
    public String toString() {
        return name;
    }
}

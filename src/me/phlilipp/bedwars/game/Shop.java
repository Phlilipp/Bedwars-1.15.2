package me.phlilipp.bedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Shop implements Listener {
    private Inventory mainmenu;
    private Inventory blocks;
    private Inventory tools;
    private Inventory weapons;
    private Inventory bows;
    private Inventory armor;
    private Inventory potions;
    private Inventory stuff;

    private Team team;

    public Shop(Team team) {
        this.team = team;
        setupShop();
    }

    public void setupShop() {
        mainmenu = Bukkit.createInventory(null, 9, "Mainmenu");
        setupFirstLine(mainmenu);

        blocks = Bukkit.createInventory(null, 45, "Blöcke");
        setupFirstLine(blocks);
        setupSecondLine(blocks);
        blocks.setItem(18, new ItemStack(Material.SANDSTONE, 16));
        blocks.setItem(36, Model.getBronze(4));
        blocks.setItem(19, new ItemStack(Material.NETHER_BRICKS, 8));
        blocks.setItem(37, Model.getIron(4));
        blocks.setItem(20, new ItemStack(Material.END_STONE, 4));
        blocks.setItem(38, Model.getGold(1));
        blocks.setItem(21, new ItemStack(Material.OBSIDIAN, 1));
        blocks.setItem(39, Model.getGold(4));
        blocks.setItem(22, new ItemStack(Material.COBWEB));
        blocks.setItem(40, Model.getBronze(16));
        blocks.setItem(23, new ItemStack(Material.LADDER, 4));
        blocks.setItem(41, Model.getBronze(4));

        tools = Bukkit.createInventory(null, 45, "Werkzeug");
        setupFirstLine(tools);
        setupSecondLine(tools);
        tools.setItem(18, new ItemStack(Material.WOODEN_PICKAXE));
        tools.setItem(36, Model.getBronze(4));
        tools.setItem(19, new ItemStack(Material.STONE_PICKAXE));
        tools.setItem(37, Model.getIron(2));
        tools.setItem(20, new ItemStack(Material.IRON_PICKAXE));
        tools.setItem(38, Model.getGold(1));
        tools.setItem(21, new ItemStack(Material.DIAMOND_PICKAXE));
        tools.setItem(39, Model.getGold(4));
        ItemStack picke = new ItemStack(Material.DIAMOND_PICKAXE);
        picke.addEnchantment(Enchantment.DIG_SPEED, 3);
        tools.setItem(22, picke);
        tools.setItem(40, Model.getGold(16));

        weapons = Bukkit.createInventory(null, 45, "Nahkampf");
        setupFirstLine(weapons);
        setupSecondLine(weapons);
        ItemStack stick = new ItemStack(Material.STICK);
        stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        weapons.setItem(18, stick);
        weapons.setItem(36, Model.getBronze(8));
        weapons.setItem(19, new ItemStack(Material.STONE_SWORD));
        weapons.setItem(37, Model.getIron(4));
        weapons.setItem(20, new ItemStack(Material.IRON_SWORD));
        weapons.setItem(38, Model.getGold(2));
        weapons.setItem(21, new ItemStack(Material.DIAMOND_SWORD));
        weapons.setItem(39, Model.getGold(6));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.KNOCKBACK, 1);
        weapons.setItem(22, sword);
        weapons.setItem(40, Model.getGold(10));
        sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        weapons.setItem(23, sword);
        weapons.setItem(41, Model.getGold(15));
        weapons.setItem(24, new ItemStack(Material.SHIELD));
        weapons.setItem(42, Model.getIron(10));

        bows = Bukkit.createInventory(null, 45, "Fernkampf");
        setupFirstLine(bows);
        setupSecondLine(bows);
        bows.setItem(18, new ItemStack(Material.BOW));
        bows.setItem(36, Model.getGold(3));
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        bows.setItem(19, bow);
        bows.setItem(37, Model.getGold(8));
        bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        bows.setItem(20, bow);
        bows.setItem(38, Model.getGold(12));
        bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        bows.setItem(21, bow);
        bows.setItem(39, Model.getGold(20));
        ItemStack crossbow = new ItemStack(Material.CROSSBOW);
        crossbow.addEnchantment(Enchantment.QUICK_CHARGE, 1);
        bows.setItem(22, crossbow);
        bows.setItem(40, Model.getGold(6));
        crossbow = new ItemStack(Material.CROSSBOW);
        crossbow.addEnchantment(Enchantment.QUICK_CHARGE, 3);
        bows.setItem(23, crossbow);
        bows.setItem(41, Model.getGold(10));
        crossbow = new ItemStack(Material.CROSSBOW);
        crossbow.addEnchantment(Enchantment.MULTISHOT, 1);
        crossbow.addEnchantment(Enchantment.QUICK_CHARGE, 2);
        bows.setItem(24, crossbow);
        bows.setItem(42, Model.getGold(16));
        bows.setItem(25, new ItemStack(Material.ARROW, 8));
        bows.setItem(43, Model.getGold(1));
        bows.setItem(26, new ItemStack(Material.SPECTRAL_ARROW, 2));
        bows.setItem(44, Model.getGold(1));

        armor = Bukkit.createInventory(null, 45, "Rüstung");
        setupFirstLine(armor);
        setupSecondLine(armor);
        armor.setItem(18, getLeatherArmorPiece(Material.LEATHER_HELMET));
        armor.setItem(36, Model.getBronze(4));
        armor.setItem(19, getLeatherArmorPiece(Material.LEATHER_LEGGINGS));
        armor.setItem(37, Model.getBronze(4));
        armor.setItem(20, getLeatherArmorPiece(Material.LEATHER_BOOTS));
        armor.setItem(38, Model.getBronze(4));
        armor.setItem(21, getLeatherArmorPiece(Material.LEATHER_CHESTPLATE));
        armor.setItem(39, Model.getBronze(8));
        armor.setItem(22, new ItemStack(Material.IRON_CHESTPLATE));
        armor.setItem(40, Model.getIron(8));
        armor.setItem(23, new ItemStack(Material.DIAMOND_CHESTPLATE));
        armor.setItem(41, Model.getGold(6));


        potions = Bukkit.createInventory(null, 45, "Tränke");
        setupFirstLine(potions);
        setupSecondLine(potions);
        potions.setItem(18, Model.getPotion(PotionType.SPEED, PotionEffectType.SPEED, "SPEEEED", 120, 3, false));
        potions.setItem(36, Model.getGold(4));
        potions.setItem(19, Model.getPotion(PotionType.JUMP, PotionEffectType.JUMP, "Jump Boost", 120, 5, false));
        potions.setItem(37, Model.getGold(2));
        potions.setItem(20, Model.getPotion(PotionType.INVISIBILITY, PotionEffectType.INVISIBILITY, "Invisibility", 20, 1, false));
        potions.setItem(38, Model.getGold(8));
        potions.setItem(21, Model.getPotion(PotionType.TURTLE_MASTER, PotionEffectType.SLOW_DIGGING, "Mining Fatigue", 30, 2, true));
        potions.setItem(39, Model.getGold(6));
        potions.setItem(22, Model.getPotion(PotionType.SLOW_FALLING, PotionEffectType.LEVITATION, "Levitation", 10, 2, true));
        potions.setItem(40, Model.getGold(8));
        potions.setItem(23, new ItemStack(Material.MILK_BUCKET));
        potions.setItem(41, Model.getIron(12));

        stuff = Bukkit.createInventory(null, 45, "Sonstiges");
        setupFirstLine(stuff);
        setupSecondLine(stuff);
        stuff.setItem(18, new ItemStack(Material.ENDER_PEARL));
        stuff.setItem(36, Model.getGold(12));
        stuff.setItem(19, new ItemStack(Material.TNT));
        stuff.setItem(37, Model.getGold(4));
        stuff.setItem(20, Model.getVotedMap().getSpecialItem());
        stuff.setItem(38, Model.getVotedMap().getItemPrice());
    }

    public ItemStack getLeatherArmorPiece(Material mat) {
        ItemStack item = new ItemStack(mat);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(team.getColor());
        item.setItemMeta(meta);
        return item;
    }

    private void setupFirstLine(Inventory inv) {
        inv.setItem(0, Model.getItem(Material.SANDSTONE, "Blöcke"));
        inv.setItem(1, Model.getItem(Material.DIAMOND_PICKAXE, "Werkzeug"));
        inv.setItem(2, Model.getItem(Material.DIAMOND_SWORD, "Nahkampf"));
        inv.setItem(3, Model.getItem(Material.BOW, "Fernkampf"));
        inv.setItem(4, Model.getItem(Material.DIAMOND_CHESTPLATE, "Rüstung"));
        inv.setItem(5, Model.getShopIndicator());
        inv.setItem(6, Model.getItem(Material.TNT, "Sonstiges"));
    }

    private void setupSecondLine(Inventory inv) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("-");
        item.setItemMeta(im);
        for (int i = 9; i < 18; i++) {
            inv.setItem(i, item);
        }
    }

    public Inventory getMainmenu() {
        return mainmenu;
    }

    public Inventory getBlocks() {
        return blocks;
    }

    public Inventory getTools() {
        return tools;
    }

    public Inventory getWeapons() {
        return weapons;
    }

    public Inventory getArmor() {
        return armor;
    }

    public Inventory getPotions() {
        return potions;
    }

    public Inventory getStuff() {
        return stuff;
    }

    public Inventory getBows() {
        return bows;
    }
}

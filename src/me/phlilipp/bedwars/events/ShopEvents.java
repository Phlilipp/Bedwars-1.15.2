package me.phlilipp.bedwars.events;

import me.phlilipp.bedwars.game.Model;
import me.phlilipp.bedwars.game.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopEvents implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory i = e.getClickedInventory();
        if (i == null) {
            return;
        }
        if (i.containsAtLeast(Model.getShopIndicator(), 1) && i.getSize() == 45) {
            Inventory inv = e.getClickedInventory();
            Inventory playerInv = e.getWhoClicked().getInventory();
            if (e.getSlot() >= 18 && e.getSlot() <= 26) {
                buy(playerInv, inv.getItem(e.getSlot()), inv.getItem(e.getSlot() + 18));
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClickWhileShopping(InventoryClickEvent e) {
        Inventory shop = e.getView().getTopInventory();
        Inventory playerInv = e.getView().getBottomInventory();
        if (shop.containsAtLeast(Model.getShopIndicator(), 1) && e.getClickedInventory() != null && e.getClickedInventory().equals(playerInv)) {
            e.setCancelled(true);
        }
    }

    private static void buy(Inventory playerInv, ItemStack buying, ItemStack price) {
        if (price == null) {
            return;
        }
        int priceInt = price.getAmount();
        price.setAmount(1);
        int buyInt = buying.getAmount();
        buying.setAmount(1);
        if (playerInv.containsAtLeast(price, priceInt)) {
            //Teleporter Aktivierung
            if (buying.getType().equals(Material.WHITE_SHULKER_BOX)) {
                Player p = (Player) playerInv.getHolder();
                Team team = Model.getTeam(p);
                team.activateTeleporter();
                for (int i = 0; i < priceInt; i++) {
                    playerInv.removeItem(price);
                }
                price.setType(Material.BARRIER);
                return;
            }
            for (int i = 0; i < priceInt; i++) {
                playerInv.removeItem(price);
            }
            for (int i = 0; i < buyInt; i++) {
                playerInv.addItem(buying);
            }
        }
        price.setAmount(priceInt);
        buying.setAmount(buyInt);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getClickedInventory().getHolder() != null) {
            return;
        }
        if (!e.getClickedInventory().containsAtLeast(Model.getShopIndicator(), 1)) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        Team team = Model.getTeam(p);
        if (e.getSlot() < 9 && e.getSlot() >= 0) {
            switch (e.getSlot()) {
                case 0:
                    e.getWhoClicked().openInventory(team.getShop().getBlocks());
                    break;
                case 1:
                    e.getWhoClicked().openInventory(team.getShop().getTools());
                    break;
                case 2:
                    e.getWhoClicked().openInventory(team.getShop().getWeapons());
                    break;
                case 3:
                    e.getWhoClicked().openInventory(team.getShop().getBows());
                    break;
                case 4:
                    e.getWhoClicked().openInventory(team.getShop().getArmor());
                    break;
                case 5:
                    e.getWhoClicked().openInventory(team.getShop().getPotions());
                    break;
                case 6:
                    e.getWhoClicked().openInventory(team.getShop().getStuff());
            }
            e.setCancelled(true);
        }
    }
}

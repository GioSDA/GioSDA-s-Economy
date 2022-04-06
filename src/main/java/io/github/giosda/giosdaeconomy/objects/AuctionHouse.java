package io.github.giosda.giosdaeconomy.objects;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class AuctionHouse implements InventoryHolder {

	private Inventory inventory;
	private Player player;

	public AuctionHouse(Player p) {
		this.player = p;
		Economy.logger.info("Test");

		createAuctionGUI();
	}

	public void createAuctionGUI() {
		inventory = Bukkit.createInventory(this, 6*9, ChatColor.AQUA + "Auction House");

		int i = 0;

		for (; i < 45; i++) {
			if (Economy.auctionHouse.size() > i) {
				ItemStack item = Economy.auctionHouse.get(i).toItemStack();

				inventory.setItem(i, item);
			} else {
				inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
			}
		}

		for (; i < inventory.getSize(); i++) {
			inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
		}

		player.openInventory(inventory);
	}

	public void createBuyGUI(int index) {
		inventory = Bukkit.createInventory(this, 6*9, ChatColor.AQUA + "Auction House");

		for (int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
		}

		inventory.setItem(13, Economy.auctionHouse.get(index).toItemStack());

		player.openInventory(inventory);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}
}

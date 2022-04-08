package io.github.giosda.giosdaeconomy.objects;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class AuctionHouseGUI implements InventoryHolder {

	private Inventory inventory;
	private Player player;

	public AuctionHouseState state;

	public AuctionHouseGUI(Player p) {
		this.player = p;

		createAuctionGUI();
	}

	public void createAuctionGUI() {
		state = AuctionHouseState.AUCTION_HOUSE;
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
		state = AuctionHouseState.AUCTION_BUY;
		inventory = Bukkit.createInventory(this, 6*9, ChatColor.AQUA + "Auction House");

		for (int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
		}

		inventory.setItem(13, Economy.auctionHouse.get(index).toItemStack());

		player.openInventory(inventory);
	}

	public AuctionHouseState getState() {
		return state;
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}
}

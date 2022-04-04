package io.github.giosda.giosdaeconomy.objects;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AuctionHouse implements InventoryHolder {

	private Inventory inventory;
	private Player player;

	public AuctionHouse(Player p) {
		this.player = p;
		Economy.logger.info("Test");

		createAuctionGUI();

		player.openInventory(inventory);
	}

	public void createAuctionGUI() {
		inventory = Bukkit.createInventory(this, 6*9, ChatColor.AQUA + "Auction House");

		int i = 0;

		for (; i < 45; i++) {
			if (Economy.auctionHouse.size() > i) {
				AuctionItem auctionItem = Economy.auctionHouse.get(i);

				ItemStack item = auctionItem.getItem().clone();

				if (item.hasItemMeta()) {
					ItemMeta meta = item.getItemMeta();
					List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

					lore.add(ChatColor.BLUE + "Seller: " + ChatColor.AQUA + Bukkit.getPlayer(auctionItem.getSeller()).getName());
					lore.add(ChatColor.BLUE + "Current bid: " + ChatColor.YELLOW + "$" + auctionItem.getBid());

					meta.setLore(lore);
					item.setItemMeta(meta);
				} else {
					ItemMeta meta = new ItemStack(Material.IRON_SWORD).getItemMeta();
					ArrayList<String> lore = new ArrayList<>();

					lore.add(ChatColor.BLUE + "Seller: " + ChatColor.AQUA + Bukkit.getPlayer(auctionItem.getSeller()).getName());
					lore.add(ChatColor.BLUE + "Current bid: " + ChatColor.YELLOW + "$" + auctionItem.getBid());
					lore.add(ChatColor.GREEN + "Up for auction!");

					meta.setLore(lore);
					item.setItemMeta(meta);
				}

				inventory.setItem(i, item);
			} else {
				inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
			}
		}

		for (; i < inventory.getSize(); i++) {
			inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
		}
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}
}

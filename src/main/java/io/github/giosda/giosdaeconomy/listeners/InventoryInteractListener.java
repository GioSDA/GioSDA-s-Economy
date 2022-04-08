package io.github.giosda.giosdaeconomy.listeners;

import io.github.giosda.giosdaeconomy.Economy;
import io.github.giosda.giosdaeconomy.objects.AuctionHouseGUI;
import io.github.giosda.giosdaeconomy.objects.AuctionHouseState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryInteractListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof AuctionHouseGUI) {
			AuctionHouseState state = ((AuctionHouseGUI) e.getInventory().getHolder()).getState();

			e.setCancelled(true);
			if (e.getRawSlot() < Economy.auctionHouse.size() && state == AuctionHouseState.AUCTION_HOUSE) {
				((AuctionHouseGUI) e.getInventory().getHolder()).createBuyGUI(e.getRawSlot());
			}

			if (e.getWhoClicked() instanceof Player) ((Player) e.getWhoClicked()).updateInventory();
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (e.getInventory().getHolder() instanceof AuctionHouseGUI) {
			e.setCancelled(true);

			if (e.getWhoClicked() instanceof Player) ((Player) e.getWhoClicked()).updateInventory();
		}
	}

}

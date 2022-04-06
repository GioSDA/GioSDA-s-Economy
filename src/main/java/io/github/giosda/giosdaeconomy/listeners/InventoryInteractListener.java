package io.github.giosda.giosdaeconomy.listeners;

import io.github.giosda.giosdaeconomy.Economy;
import io.github.giosda.giosdaeconomy.objects.AuctionHouse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryInteractListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof AuctionHouse) {
			e.setCancelled(true);
			if (e.getRawSlot() < Economy.auctionHouse.size()) {
				((AuctionHouse) e.getInventory().getHolder()).createBuyGUI(e.getRawSlot());
			}
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (e.getInventory().getHolder() instanceof AuctionHouse) e.setCancelled(true);
	}

}

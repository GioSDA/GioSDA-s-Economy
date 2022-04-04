package io.github.giosda.giosdaeconomy.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryInteractListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getView().getTitle().equals(ChatColor.AQUA + "Auction House")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (e.getView().getTitle().equals(ChatColor.AQUA + "Auction House")) e.setCancelled(true);
	}

}

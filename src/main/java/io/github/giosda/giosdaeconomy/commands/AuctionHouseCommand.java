package io.github.giosda.giosdaeconomy.commands;

import io.github.giosda.giosdaeconomy.Economy;
import io.github.giosda.giosdaeconomy.objects.AuctionItem;
import jdk.incubator.jpackage.internal.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuctionHouseCommand implements CommandExecutor {



	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (Economy.playerBalances.containsKey(p.getUniqueId())) {
					createAuctionGUI(p);
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "What have you done");
				}
			} else {
				sender.sendMessage("Only players can run this command!");
			}
		} else {
			Economy.logger.info(args[0]);
			if (args[0].equals("sell")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;

					ItemStack i = p.getInventory().getItemInMainHand();

					AuctionItem item = new AuctionItem(10, p.getUniqueId(), i);
					p.getInventory().removeItem(i);
					Economy.auctionHouse.add(item);
					p.sendMessage(ChatColor.GREEN + "Successfully put item up for auction!");
				} else {
					sender.sendMessage("Only players can run this command!");
				}
			}
		}

		return true;
	}


	public void createAuctionGUI(Player p) {
		Inventory gui = Bukkit.createInventory(p, 54, ChatColor.AQUA + "Auction House");

		for (int i = 0; i < Economy.auctionHouse.size(); i++) {
			AuctionItem auctionItem = Economy.auctionHouse.get(i);
			ItemStack item = auctionItem.getItem();

			if (item.hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();
				List<String> lore;

				if (meta.hasLore()) lore = meta.getLore();
				else lore = new ArrayList<>();

				lore.add(ChatColor.BLUE + "Seller: " + ChatColor.AQUA + Bukkit.getPlayer(auctionItem.getSeller()).getName());
				lore.add(ChatColor.BLUE + "Current bid: " + ChatColor.YELLOW + "$" + auctionItem.getBid());

				meta.setLore(lore);
				item.setItemMeta(meta);
			} else {

			}

			gui.addItem(item);
		}

		p.openInventory(gui);
	}
}


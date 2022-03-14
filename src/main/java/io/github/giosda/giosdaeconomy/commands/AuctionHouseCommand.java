package io.github.giosda.giosdaeconomy.commands;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AuctionHouseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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

		return true;
	}


	public void createAuctionGUI(Player p) {
		Inventory gui = Bukkit.createInventory(p, 54, ChatColor.AQUA + "Auction House");

		p.openInventory(gui);
	}
}


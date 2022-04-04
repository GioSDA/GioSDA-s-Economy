package io.github.giosda.giosdaeconomy.commands;

import io.github.giosda.giosdaeconomy.Economy;
import io.github.giosda.giosdaeconomy.objects.AuctionHouse;
import io.github.giosda.giosdaeconomy.objects.AuctionItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AuctionHouseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (Economy.playerBalances.containsKey(p.getUniqueId())) {
					new AuctionHouse(p);
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
					if (i.getType() == Material.AIR) {
						p.sendMessage(ChatColor.RED + "You can't sell nothing!");
						return true;
					}

					if (args.length > 1) {
						try {
							int startingBid = Integer.parseInt(args[1]);
							if (startingBid > 0) {
								AuctionItem item = new AuctionItem(startingBid, p.getUniqueId(), i);
								item.serializeItem();
								p.getInventory().removeItem(i);
								Economy.auctionHouse.add(item);
								p.sendMessage(ChatColor.GREEN + "Successfully put item up for auction!");
							} else {
								p.sendMessage(ChatColor.RED + "You can't start with less than " + ChatColor.YELLOW + "$1");
							}
						} catch (NumberFormatException e) {
							p.sendMessage(ChatColor.RED + "Starting bid must be a whole number!");
							return true;
						}
					} else {
						p.sendMessage(ChatColor.RED + "Please include a starting bid!");
					}


				} else {
					sender.sendMessage("Only players can run this command!");
				}
			}
		}

		return true;
	}

}


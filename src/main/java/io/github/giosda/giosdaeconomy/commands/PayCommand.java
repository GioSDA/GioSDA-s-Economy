package io.github.giosda.giosdaeconomy.commands;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Missing argument(s)!");
			return true;
		}

		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (!Economy.playerBalances.containsKey(p.getUniqueId())) {
				sender.sendMessage(ChatColor.DARK_RED + "What have you done");
				return true;
			}

			Player p2 = Bukkit.getPlayer(args[0]);
			if (p2 == null) {
				sender.sendMessage(ChatColor.RED + "This player is not online!");
				return true;
			}



			if (Economy.playerBalances.containsKey(p2.getUniqueId())) {
				try {
					int amount = Integer.parseInt(args[1]);
					if (amount > 0) {
						if (Economy.playerBalances.get(p.getUniqueId()) > amount) {
							Economy.playerBalances.put(p2.getUniqueId(), Economy.playerBalances.get(p2.getUniqueId()) + amount);
							Economy.playerBalances.put(p.getUniqueId(), Economy.playerBalances.get(p.getUniqueId()) - amount);
							p.sendMessage(ChatColor.GREEN + "Successfully transferred " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " to " + p2.getDisplayName());
							p2.sendMessage(ChatColor.GREEN + "You received " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " from " + p.getDisplayName());
						} else {
							p.sendMessage(ChatColor.RED + "You don't have that much money!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You can't send less than " + ChatColor.YELLOW + "$1");
					}
				} catch (NumberFormatException e) {
					p.sendMessage(ChatColor.RED + "Amount paid must be a whole number!");
					return true;
				}

			} else {
				sender.sendMessage(ChatColor.DARK_RED + "What have you done");
			}
			return true;
		}

		sender.sendMessage("Only players can run this command!");
		return true;
	}
}

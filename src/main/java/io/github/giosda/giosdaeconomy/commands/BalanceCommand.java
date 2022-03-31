package io.github.giosda.giosdaeconomy.commands;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) { //Referring to self
			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (Economy.playerBalances.containsKey(p.getUniqueId())) {
					sender.sendMessage("You have " + ChatColor.YELLOW + "$" + Economy.playerBalances.get(p.getUniqueId()));
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "What have you done");
				}
			} else {
				sender.sendMessage("Only players can run this command without arguments!");
			}

		} else { //Referring to player
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]); //This line is dangerous
			if (p == sender) {
				sender.sendMessage("You have " + ChatColor.YELLOW + "$" + Economy.playerBalances.get(p.getUniqueId()));
				return true;
			}

			if (Economy.playerBalances.containsKey(p.getUniqueId())) {
				sender.sendMessage(p.getName() + " has " + ChatColor.YELLOW + "$" + Economy.playerBalances.get(p.getUniqueId()));
			} else {
				sender.sendMessage(ChatColor.DARK_RED + p.getName() + "does not exist!");
			}

		}

		return true;
	}
}

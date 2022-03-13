package io.github.giosda.giosdaeconomy.commands;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (!Economy.playerBalances.containsKey(p.getUniqueId())) {
				sender.sendMessage("§4What have you done");
				return true;
			}

			Player p2 = Bukkit.getPlayer(args[0]);
			if (p2 == null) {
				sender.sendMessage("§cThis player is not online!");
				return true;
			}



			if (Economy.playerBalances.containsKey(p2.getUniqueId())) {
				int amount;

				try {
					amount = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					p.sendMessage("§cAmount paid must be a whole number!");
					return true;
				}

				if (amount > 0) {
					Economy.playerBalances.put(p2.getUniqueId(), Economy.playerBalances.get(p2.getUniqueId()) + amount);
					Economy.playerBalances.put(p.getUniqueId(), Economy.playerBalances.get(p.getUniqueId()) - amount);
					p.sendMessage("§aSuccessfully transferred §e$" + amount + " §ato " + p2.getDisplayName());
					p2.sendMessage("§aYou received §e$" + amount + " §afrom " + p.getDisplayName());
				} else {
					sender.sendMessage("§cYou can't send less than §e$1");
				}

			} else {
				sender.sendMessage("§4What have you done");
			}
			return true;
		}

		sender.sendMessage("Only players can run this command!");
		return true;
	}
}

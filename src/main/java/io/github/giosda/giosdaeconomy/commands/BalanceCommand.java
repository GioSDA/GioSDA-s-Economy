package io.github.giosda.giosdaeconomy.commands;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class BalanceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) { //Referring to self
			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (Economy.playerBalances.containsKey(p.getUniqueId())) {
					sender.sendMessage("You have §e$" + Economy.playerBalances.get(p.getUniqueId()));
				} else {
					sender.sendMessage("§4What have you done");
				}
			} else {
				sender.sendMessage("Only players can run this command without arguments!");
			}

		} else { //Referring to player
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]); //This line is dangerous

			if (Economy.playerBalances.containsKey(p.getUniqueId())) {
				sender.sendMessage(p.getName() + " has §e$" + Economy.playerBalances.get(p.getUniqueId()));
			} else {
				sender.sendMessage("§4" + p.getName() + "does not exist!");
			}

		}

		return true;
	}
}

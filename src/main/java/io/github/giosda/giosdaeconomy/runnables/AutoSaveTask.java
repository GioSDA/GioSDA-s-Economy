package io.github.giosda.giosdaeconomy.runnables;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class AutoSaveTask extends BukkitRunnable {

	@Override
	public void run() {
		try {
			Economy.saveBalances();
		} catch (IOException e) {
			Economy.logger.info("Could not save player balances!");
			Bukkit.broadcast(ChatColor.DARK_RED + "ERROR: Could not save player balances!", "op");
		}
	}

}

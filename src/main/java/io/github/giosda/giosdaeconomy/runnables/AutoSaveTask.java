package io.github.giosda.giosdaeconomy.runnables;

import io.github.giosda.giosdaeconomy.Economy;
import io.github.giosda.giosdaeconomy.toolbox.DataStore;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveTask extends BukkitRunnable {

	@Override
	public void run() {
		DataStore.saveBalances(Economy.playerBalances);
		DataStore.saveAuctionHouse(Economy.auctionHouse);
	}

}

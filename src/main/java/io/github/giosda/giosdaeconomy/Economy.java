package io.github.giosda.giosdaeconomy;

import io.github.giosda.giosdaeconomy.commands.AuctionHouseCommand;
import io.github.giosda.giosdaeconomy.commands.BalanceCommand;
import io.github.giosda.giosdaeconomy.commands.DebugCommand;
import io.github.giosda.giosdaeconomy.commands.PayCommand;
import io.github.giosda.giosdaeconomy.listeners.InventoryInteractListener;
import io.github.giosda.giosdaeconomy.listeners.PlayerJoinListener;
import io.github.giosda.giosdaeconomy.objects.AuctionItem;
import io.github.giosda.giosdaeconomy.runnables.AutoSaveTask;
import io.github.giosda.giosdaeconomy.toolbox.DataStore;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public final class Economy extends JavaPlugin {

	public static List<AuctionItem> auctionHouse = new ArrayList<>();
	public static HashMap<UUID, Integer> playerBalances = new HashMap<>();
	public static int loginBalance = 1000;
	public static long autoSaveInterval = 6000;

	public static Logger logger;

	@Override
	public void onEnable() {
		logger = getLogger();

		saveDefaultConfig();
		addDefaults();

		if (getConfig().contains("loginBalance")) loginBalance = (int) getConfig().get("loginBalance");

		auctionHouse = DataStore.loadAuctionHouse();
		playerBalances = DataStore.loadBalances();

		getCommand("balance").setExecutor(new BalanceCommand());
		getCommand("pay").setExecutor(new PayCommand());
		getCommand("debug").setExecutor(new DebugCommand());
		getCommand("auction").setExecutor(new AuctionHouseCommand());

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryInteractListener(), this);

		new AutoSaveTask().runTaskTimer(this, autoSaveInterval, autoSaveInterval);
	}

	@Override
	public void onDisable() {
		DataStore.saveBalances(playerBalances);
		DataStore.saveAuctionHouse(auctionHouse);
	}

	public void addDefaults() {
		getConfig().addDefault("loginBalance", 1000);
		getConfig().addDefault("autoSaveInterval", 6000);
	}

	public static int getLoginBalance() {
		return loginBalance;
	}
}

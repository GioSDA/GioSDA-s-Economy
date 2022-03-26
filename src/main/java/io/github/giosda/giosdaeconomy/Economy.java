package io.github.giosda.giosdaeconomy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.giosda.giosdaeconomy.commands.AuctionHouseCommand;
import io.github.giosda.giosdaeconomy.commands.BalanceCommand;
import io.github.giosda.giosdaeconomy.commands.DebugCommand;
import io.github.giosda.giosdaeconomy.commands.PayCommand;
import io.github.giosda.giosdaeconomy.listeners.PlayerJoinListener;
import io.github.giosda.giosdaeconomy.objects.AuctionItem;
import io.github.giosda.giosdaeconomy.runnables.AutoSaveTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.lang.reflect.Type;
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

		try {
			loadAuctionHouse();
			loadBalances();
		} catch (IOException e) {
			getLogger().info("Gio's Economy had an issue loading data.");
			e.printStackTrace();
		}

		getCommand("balance").setExecutor(new BalanceCommand());
		getCommand("pay").setExecutor(new PayCommand());
		getCommand("debug").setExecutor(new DebugCommand());
		getCommand("auction").setExecutor(new AuctionHouseCommand());

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

		BukkitTask autoSaveTask = new AutoSaveTask().runTaskTimer(this, autoSaveInterval, autoSaveInterval);
	}

	@Override
	public void onDisable() {
		try {
			saveBalances();
			saveAuctionHouse();
		} catch (IOException e) {
			getLogger().info("Couldn't save data!");
			e.printStackTrace();
		}
	}

	public void addDefaults() {
		getConfig().addDefault("loginBalance", 1000);
		getConfig().addDefault("autoSaveInterval", 6000);
	}

	public static void saveBalances() throws IOException {
		File file = new File("plugins/GioSDAs-Economy/balances.json");
		file.createNewFile();

		FileWriter writer = new FileWriter(file);

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		writer.write(gson.toJson(playerBalances));
		writer.flush();
	}

	public void loadBalances() throws IOException {
		File file = new File("plugins/GioSDAs-Economy/balances.json");
		file.createNewFile();

		FileReader reader = new FileReader(file);

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		Type type = new TypeToken<HashMap<UUID, Integer>>(){}.getType();
		playerBalances = gson.fromJson(reader, type);

		if (playerBalances == null) playerBalances = new HashMap<>();

		if (getConfig().contains("loginBalance")) loginBalance = (int) getConfig().get("loginBalance");
	}

	//TODO: shit dont work
	public void loadAuctionHouse() throws IOException {
		File file = new File("plugins/GioSDAs-Economy/auctionhouse.json");
		file.createNewFile();

		FileReader reader = new FileReader(file);

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		Type type = new TypeToken<ArrayList<AuctionItem>>(){}.getType();
		auctionHouse = gson.fromJson(reader, type);

		if (auctionHouse == null) auctionHouse = new ArrayList<>();
	}

	public static void saveAuctionHouse() throws IOException {
		File file = new File("plugins/GioSDAs-Economy/auctionhouse.json");
		file.createNewFile();

		FileWriter writer = new FileWriter(file);

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setPrettyPrinting().create();

		writer.write(gson.toJson(auctionHouse));
		writer.flush();
	}

	public static int getLoginBalance() {
		return loginBalance;
	}
}

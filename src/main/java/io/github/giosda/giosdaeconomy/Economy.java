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
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public final class Economy extends JavaPlugin {

	public static List<AuctionItem> auctionHouse = new ArrayList<>();
	public static HashMap<UUID, Integer> playerBalances = new HashMap<>();
	public static int loginBalance;

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

	public void saveBalances() throws IOException {
		FileWriter writer;

		try {
			writer = new FileWriter("plugins/GioSDAs-Economy/balances.json");
		} catch (FileNotFoundException e) {
			File file = new File("plugins/GioSDAs-Economy/balances.json");
			if (file.createNewFile()) writer = new FileWriter("plugins/GioSDAs-Economy/balances.json");
			else {
				e.printStackTrace();
				return;
			}
		}

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		getLogger().info(gson.toJson(playerBalances));
		writer.write(gson.toJson(playerBalances));
		writer.flush();
	}

	public void addDefaults() {
		getConfig().addDefault("loginBalance", 1000);
	}

	public void loadBalances() throws IOException {
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("plugins/GioSDAs-Economy/balances.json"));
		} catch (FileNotFoundException e) {
			File file = new File("plugins/GioSDAs-Economy/balances.json");
			file.createNewFile();

			FileWriter writer = new FileWriter("plugins/GioSDAs-Economy/balances.json");
			writer.write("{}");

			reader = new BufferedReader(new FileReader("plugins/GioSDAs-Economy/balances.json"));
			reader.close();
		}

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		Type type = new TypeToken<HashMap<UUID, Integer>>(){}.getType();
		playerBalances = gson.fromJson(reader, type);

		if (playerBalances == null) playerBalances = new HashMap<>();

		if (getConfig().contains("loginBalance")) loginBalance = (int) getConfig().get("loginBalance");
		else loginBalance = 1000;
	}

	//TODO: shit dont work
	public void loadAuctionHouse() throws IOException {
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("plugins/GioSDAs-Economy/auctionhouse.json"));
		} catch (FileNotFoundException e) {
			File file = new File("plugins/GioSDAs-Economy/auctionhouse.json");
			file.createNewFile();

			FileWriter writer = new FileWriter("plugins/GioSDAs-Economy/auctionhouse.json");
			writer.write("{}");

			reader = new BufferedReader(new FileReader("plugins/GioSDAs-Economy/auctionhouse.json"));
			reader.close();
		}

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		Type type = new TypeToken<ArrayList<AuctionItem>>(){}.getType();
		auctionHouse = gson.fromJson(reader, type);

		if (auctionHouse == null) auctionHouse = new ArrayList<>();
	}

	public void saveAuctionHouse() throws IOException {
		FileWriter writer;

		try {
			writer = new FileWriter("plugins/GioSDAs-Economy/auctionhouse.json");
		} catch (FileNotFoundException e) {
			File file = new File("plugins/GioSDAs-Economy/auctionhouse.json");
			if (file.createNewFile()) writer = new FileWriter("plugins/GioSDAs-Economy/auctionhouse.json");
			else {
				e.printStackTrace();
				return;
			}
		}

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		getLogger().info(gson.toJson(auctionHouse));
		writer.write(gson.toJson(auctionHouse));
		writer.flush();
	}

	public static int getLoginBalance() {
		return loginBalance;
	}
}

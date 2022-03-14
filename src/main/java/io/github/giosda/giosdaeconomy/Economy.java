package io.github.giosda.giosdaeconomy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.giosda.giosdaeconomy.commands.AuctionHouseCommand;
import io.github.giosda.giosdaeconomy.commands.BalanceCommand;
import io.github.giosda.giosdaeconomy.commands.DebugCommand;
import io.github.giosda.giosdaeconomy.commands.PayCommand;
import io.github.giosda.giosdaeconomy.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public final class Economy extends JavaPlugin {

	public static HashMap<UUID, Integer> playerBalances = new HashMap<>();
	public static int loginBalance;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		addDefaults();

		try {
			loadBalances();
		} catch (IOException e) {
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
		} catch (IOException e) {
			getLogger().info("Couldn't save balances!");
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
		}

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		Type type = new TypeToken<HashMap<UUID, Integer>>(){}.getType();
		playerBalances = gson.fromJson(reader, type);

		if (playerBalances == null) playerBalances = new HashMap<>();

		if (getConfig().contains("loginBalance")) loginBalance = (int) getConfig().get("loginBalance");
		else loginBalance = 1000;
	}

	public static int getLoginBalance() {
		return loginBalance;
	}
}

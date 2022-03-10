package io.github.giosda.giosdaeconomy;

import io.github.giosda.giosdaeconomy.commands.BalanceCommand;
import io.github.giosda.giosdaeconomy.commands.PayCommand;
import io.github.giosda.giosdaeconomy.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Economy extends JavaPlugin {

	public static HashMap<UUID, Integer> playerBalances = new HashMap<>();
	public static int loginBalance;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		addDefaults();
		loadBalances();

		getCommand("balance").setExecutor(new BalanceCommand());
		getCommand("pay").setExecutor(new PayCommand());

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
	}

	@Override
	public void onDisable() {
		saveBalances();
	}

	public void saveBalances() {
		for (Map.Entry<UUID, Integer> entry : playerBalances.entrySet()) {
			getConfig().set("data." + entry.getKey(), entry.getValue());
		}

		saveConfig();
	}

	public void addDefaults() {
		getConfig().addDefault("loginBalance", 1000);
	}

	public void loadBalances() {
		//Login balance
		if (getConfig().contains("loginBalance")) loginBalance = (int) getConfig().get("loginBalance");
		else loginBalance = 1000;

		//Player balances
		if (this.getConfig().getConfigurationSection("data") == null) return;

		this.getConfig().getConfigurationSection("data").getKeys(false).forEach(uuid -> {
			Integer balance = (Integer) getConfig().get("data." + uuid);
			playerBalances.put(UUID.fromString(uuid), balance);
		});
	}

	public static int getLoginBalance() {
		return loginBalance;
	}
}

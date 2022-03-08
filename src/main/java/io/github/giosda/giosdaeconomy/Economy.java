package io.github.giosda.giosdaeconomy;

import io.github.giosda.giosdaeconomy.commands.BalanceCommand;
import io.github.giosda.giosdaeconomy.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Economy extends JavaPlugin {

	public static HashMap<UUID, Integer> playerBalances = new HashMap<>();

	//TODO: save player balances

	@Override
	public void onEnable() {
		loadBalances();
		getCommand("balance").setExecutor(new BalanceCommand());

		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
	}

	@Override
	public void onDisable() {
		saveBalances();
	}

	public void saveBalances() {
		for (Map.Entry<UUID, Integer> entry : playerBalances.entrySet()) {
			this.getConfig().set("data." + entry.getKey(), entry.getValue());
		}

		this.saveConfig();
	}

	public void loadBalances() {
		if (this.getConfig().getConfigurationSection("data") == null) return;

		this.getConfig().getConfigurationSection("data").getKeys(false).forEach(uuid -> {
			Integer balance = (Integer) this.getConfig().get("data." + uuid);
			playerBalances.put(UUID.fromString(uuid), balance);
		});
	}
}

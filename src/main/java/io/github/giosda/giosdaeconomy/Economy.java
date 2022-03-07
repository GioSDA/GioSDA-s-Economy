package io.github.giosda.giosdaeconomy;

import io.github.giosda.giosdaeconomy.commands.BalanceCommand;
import io.github.giosda.giosdaeconomy.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Economy extends JavaPlugin {

	public static HashMap<UUID, Integer> playerBalances = new HashMap<>();

	//TODO: save player balances

	@Override
	public void onEnable() {
		getCommand("balance").setExecutor(new BalanceCommand());

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}

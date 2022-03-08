package io.github.giosda.giosdaeconomy.listeners;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (!Economy.playerBalances.containsKey(p.getUniqueId())) Economy.playerBalances.put(p.getUniqueId(), Economy.getLoginBalance());
	}

}

package io.github.giosda.giosdaeconomy.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.FileWriter;
import java.io.IOException;

public class DebugCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(new GsonBuilder().create().toJson(Economy.playerBalances));

		try {
			FileWriter writer = new FileWriter("plugins/GioSDAs-Economy/balances.json");

			writer.write(new GsonBuilder().create().toJson(Economy.playerBalances));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}



		return true;
	}
}

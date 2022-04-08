package io.github.giosda.giosdaeconomy.toolbox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.giosda.giosdaeconomy.Economy;
import io.github.giosda.giosdaeconomy.objects.AuctionItem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataStore {

	public static void saveBalances(HashMap<UUID, Integer> playerBalances) {
		try {
			File file = new File("plugins/GioSDAs-Economy/balances.json");
			file.createNewFile();

			FileWriter writer = new FileWriter(file);

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			writer.write(gson.toJson(playerBalances));
			writer.flush();
		} catch (IOException e) {
			Economy.logger.severe("Gio's Economy was unable to save player balances!");
			e.printStackTrace();
		}
	}

	public static HashMap<UUID, Integer> loadBalances() {
		try {
			HashMap<UUID, Integer> playerBalances;

			File file = new File("plugins/GioSDAs-Economy/balances.json");
			file.createNewFile();

			FileReader reader = new FileReader(file);

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			Type type = new TypeToken<HashMap<UUID, Integer>>() {}.getType();
			playerBalances = gson.fromJson(reader, type);

			if (playerBalances == null) playerBalances = new HashMap<>();

			return playerBalances;
		} catch (IOException e) {
			Economy.logger.severe("Gio's Economy was unable to load player balances!");
			e.printStackTrace();
			return null;
		}
	}

	public static void saveAuctionHouse(List<AuctionItem> auctionHouse) {
		try {
			File file = new File("plugins/GioSDAs-Economy/auctionhouse.json");
			file.createNewFile();

			FileWriter writer = new FileWriter(file);

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.setPrettyPrinting().create();

			writer.write(gson.toJson(auctionHouse));
			writer.flush();
		} catch (IOException e) {
			Economy.logger.severe("Gio's Economy was unable to save the auction house!");
			e.printStackTrace();
		}
	}


	public static List<AuctionItem> loadAuctionHouse() {
		try {
			List<AuctionItem> auctionHouse;

			File file = new File("plugins/GioSDAs-Economy/auctionhouse.json");
			file.createNewFile();

			FileReader reader = new FileReader(file);

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			Type type = new TypeToken<ArrayList<AuctionItem>>() {
			}.getType();
			auctionHouse = gson.fromJson(reader, type);

			if (auctionHouse != null) auctionHouse.forEach(AuctionItem::deserializeItem);

			return auctionHouse;
		} catch (IOException e) {
			Economy.logger.severe("Gio's Economy was unable to load the auction house!");
			e.printStackTrace();
			return null;
		}
	}
}

package io.github.giosda.giosdaeconomy.objects;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class AuctionItem {

	public int bid;
	public UUID maxBidder;
	public UUID seller;

	public transient ItemStack item;
	public String serializedItem;

	public int getBid() {
		return this.bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public UUID getMaxBidder() {
		return maxBidder;
	}

	public void setMaxBidder(UUID maxBidder) {
		this.maxBidder = maxBidder;
	}

	public UUID getSeller() {
		return this.seller;
	}

	public void setSeller(UUID seller) {
		this.seller = seller;
	}

	public ItemStack getItem() {
		return this.item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public void serializeItem() {
		ByteArrayOutputStream io = new ByteArrayOutputStream();
		try {
			BukkitObjectOutputStream os = null;
			os = new BukkitObjectOutputStream(io);
			os.writeObject(item);
			os.flush();
		} catch (IOException e) {
			Economy.logger.severe("Could not serialize itemstack!");
			e.printStackTrace();
		}

		byte[] bs = io.toByteArray();

		this.serializedItem = new String(Base64.getEncoder().encode(bs));
	}

	public void deserializeItem() {
		byte[] bs = Base64.getDecoder().decode(serializedItem);

		ByteArrayInputStream in = new ByteArrayInputStream(bs);
		BukkitObjectInputStream is = null;
		try {
			is = new BukkitObjectInputStream(in);

			this.item = (ItemStack) is.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			Economy.logger.severe("Could not deserialize itemstack!");
		}
	}

	public ItemStack toItemStack() {
		ItemStack item = this.getItem().clone();

		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

			lore.add(ChatColor.BLUE + "Seller: " + ChatColor.AQUA + Bukkit.getPlayer(this.getSeller()).getName());
			lore.add(ChatColor.BLUE + "Current bid: " + ChatColor.YELLOW + "$" + this.getBid());

			meta.setLore(lore);
			item.setItemMeta(meta);

			return item;
		} else {
			ItemMeta meta = new ItemStack(Material.IRON_SWORD).getItemMeta();
			ArrayList<String> lore = new ArrayList<>();

			lore.add(ChatColor.BLUE + "Seller: " + ChatColor.AQUA + Bukkit.getPlayer(this.getSeller()).getName());
			lore.add(ChatColor.BLUE + "Current bid: " + ChatColor.YELLOW + "$" + this.getBid());
			lore.add(ChatColor.GREEN + "Up for auction!");

			meta.setLore(lore);
			item.setItemMeta(meta);

			return item;
		}
	}

	public AuctionItem(int bid, UUID seller, ItemStack item) {
		this.bid = bid;
		this.maxBidder = null;
		this.seller = seller;
		this.item = item;
	}

}

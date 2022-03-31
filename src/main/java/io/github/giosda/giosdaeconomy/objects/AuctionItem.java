package io.github.giosda.giosdaeconomy.objects;

import io.github.giosda.giosdaeconomy.Economy;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
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

	public AuctionItem(int bid, UUID seller, ItemStack item) {
		this.bid = bid;
		this.maxBidder = null;
		this.seller = seller;
		this.item = item;
	}

}

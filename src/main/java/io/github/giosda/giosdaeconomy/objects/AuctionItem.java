package io.github.giosda.giosdaeconomy.objects;

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
	public ItemStack item;

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

	public static String serializeItem(ItemStack item) throws IOException {
		ByteArrayOutputStream io = new ByteArrayOutputStream();
		BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
		os.writeObject(item);
		os.flush();

		byte[] bs = io.toByteArray();

		//Encode the serialized object into to the base64 format
		return new String(Base64.getEncoder().encode(bs));
	}

	public static ItemStack deserializeItem(String serializedItem) throws ClassNotFoundException, IOException {
		byte[] bs = Base64.getDecoder().decode(serializedItem);

		ByteArrayInputStream in = new ByteArrayInputStream(bs);
		BukkitObjectInputStream is = new BukkitObjectInputStream(in);

		return (ItemStack) is.readObject();
	}

	public AuctionItem(int bid, UUID seller, ItemStack item) {
		this.bid = bid;
		this.maxBidder = null;
		this.seller = seller;
		this.item = item;
	}

}

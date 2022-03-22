package io.github.giosda.giosdaeconomy.objects;

import org.bukkit.inventory.ItemStack;

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

	public AuctionItem(int bid, UUID seller, ItemStack item) {
		this.bid = bid;
		this.maxBidder = null;
		this.seller = seller;
		this.item = item;
	}

}

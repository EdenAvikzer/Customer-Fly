package entity;

import java.util.Objects;

import util.ItemType;

public class Item {
	int itemId;
	String itemName;
	ItemType type;
	String description;
	boolean available;
	Supplier sup;
	
	public Item(int itemId, String itemName, util.ItemType type, String description, boolean available) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.type = type;
		this.description = description;
		this.available = available;
	}
	
	// constructor for adding
	public Item( String itemName,  String description, ItemType type) {
		super();
		this.itemName = itemName;
		this.type = type;
		this.description = description;
		this.available = true;;
	}
	
	public Item(int itemId, String itemName, ItemType type, String description, boolean available, Supplier sup) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.type = type;
		this.description = description;
		this.available = available;
		
	}

	public Item(int itemId) {
		super();
		this.itemId = itemId;
	}


	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	

	public Supplier getSup() {
		return sup;
	}

	public void setSup(Supplier sup) {
		this.sup = sup;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(itemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return itemId == other.itemId;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", type=" + type + ", description="
				+ description;
	}
	
	
	
	
}
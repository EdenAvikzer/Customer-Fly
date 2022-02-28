package entity;

import java.util.Objects;

public class ItemBySupplier {
	
	

	int itemId;
	int supID;
	
	public ItemBySupplier(int itemId, int supID) {
		super();
		this.itemId = itemId;
		this.supID = supID;
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getSupID() {
		return supID;
	}

	public void setSupID(int supIDl) {
		this.supID = supIDl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, supID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemBySupplier other = (ItemBySupplier) obj;
		return itemId == other.itemId && supID == other.supID;
	}
	
	
	@Override
	public String toString() {
		return "ItemBySupplier [itemId=" + itemId + ", supID=" + supID + "]";
	}
	
	
	
	
}

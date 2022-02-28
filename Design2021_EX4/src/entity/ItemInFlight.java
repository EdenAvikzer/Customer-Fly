package entity;

import java.util.Objects;


public class ItemInFlight {
	
	String flightID;
	int itemID;
	int supplierID;
	String feedback;
	
	public ItemInFlight(String flightID, int itemID, int supplierID, String feedback) {
		super();
		this.flightID = flightID;
		this.itemID = itemID;
		this.supplierID = supplierID;
		this.feedback = feedback;
	}

	public ItemInFlight(String flightID, int itemID, int supplierID) {
		super();
		this.flightID = flightID;
		this.itemID = itemID;
		this.supplierID = supplierID;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	public String getFeedback() {
		return feedback;
	}

	@Override
	public int hashCode() {
		return Objects.hash(flightID, itemID, supplierID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemInFlight other = (ItemInFlight) obj;
		return Objects.equals(flightID, other.flightID) && itemID == other.itemID && supplierID == other.supplierID;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "ItemsInFlight [flightID=" + flightID + ", itemID=" + itemID + ", supplierID=" + supplierID
				+ ", feedback=" + feedback + "]";
	}
	
	
	
	
	
	
}
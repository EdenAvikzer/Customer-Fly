package entity;

import java.util.Objects;

public class Supplier {
	
	int supId;
	String supName;
	String supPhone;
	String supMail;
	
	public Supplier(int supId) {
		super();
		this.supId = supId;
	}

	public Supplier(int supId, String supName, String supPhone, String supMail) {
		super();
		this.supId = supId;
		this.supName = supName;
		this.supPhone = supPhone;
		this.supMail = supMail;
	}
	
	public Supplier( String supName, String supPhone, String supMail) {
		super();
		this.supName = supName;
		this.supPhone = supPhone;
		this.supMail = supMail;
	}

	public int getSupId() {
		return supId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getSupPhone() {
		return supPhone;
	}

	public void setSupPhone(String supPhone) {
		this.supPhone = supPhone;
	}

	public String getSupMail() {
		return supMail;
	}

	public void setSupMail(String supMail) {
		this.supMail = supMail;
	}

	@Override
	public int hashCode() {
		return Objects.hash(supId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		return supId == other.supId;
	}

	@Override
	public String toString() {
		return "Supplier - ID: " + supId + ", Name: " + supName + ", Phone: " + supPhone + ", Email:" + supMail
				;
	}
	
	
	
	
	
}

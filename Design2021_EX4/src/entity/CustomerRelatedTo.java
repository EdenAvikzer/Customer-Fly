package entity;

public class CustomerRelatedTo {
	
	private int passportNum1;
	private int passportNum2;
	
	public CustomerRelatedTo(int passportNum1, int passportNum2) {
		super();
		this.passportNum1 = passportNum1;
		this.passportNum2 = passportNum2;
	}
	
	
	public int getPassportNum1() {
		return passportNum1;
	}
	public void setPassportNum1(int passportNum1) {
		this.passportNum1 = passportNum1;
	}
	public int getPassportNum2() {
		return passportNum2;
	}
	public void setPassportNum2(int passportNum2) {
		this.passportNum2 = passportNum2;
	}

}

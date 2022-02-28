package entity;

public class ConstsPrice {
	
	int mulT;
	int mulM;
	int mulD;
	int pricePerKilo;
	
	public ConstsPrice(int mulT, int mulM, int mulD, int pricePerKilo) {
		super();
		this.mulT = mulT;
		this.mulM = mulM;
		this.mulD = mulD;
		this.pricePerKilo = pricePerKilo;
	}
	
	public ConstsPrice() {
		super();
	}


	public int getMulT() {
		return mulT;
	}

	public void setMulT(int mulT) {
		this.mulT = mulT;
	}

	public int getMulM() {
		return mulM;
	}

	public void setMulM(int mulM) {
		this.mulM = mulM;
	}

	public int getMulD() {
		return mulD;
	}

	public void setMulD(int mulD) {
		this.mulD = mulD;
	}

	public int getPricePerKilo() {
		return pricePerKilo;
	}

	public void setPricePerKilo(int pricePerKilo) {
		this.pricePerKilo = pricePerKilo;
	}

	@Override
	public String toString() {
		return "ConstsPrice [mulT=" + mulT + ", mulM=" + mulM + ", mulD=" + mulD + ", pricePerKilo=" + pricePerKilo
				+ "]";
	}
	
	
}
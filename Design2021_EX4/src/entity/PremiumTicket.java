package entity;

import util.MealType;
import util.SeatClass;

public class PremiumTicket extends FlightTicket {
	
	double luggageWeight;
	String Request1;
	String Request2;
	String Request3;



	public PremiumTicket(int orderNum, int ticketNum, MealType mealType, SeatClass seatClass, int price,
			int customerPassportNumber, int rowNum, String seatNum, String tailNumber, String flightNum,
			boolean isAvailable, double luggageWeight, String request1, String request2, String request3) {
		super(orderNum, ticketNum, mealType, seatClass, price, customerPassportNumber, rowNum, seatNum, tailNumber,
				flightNum, isAvailable);
		this.luggageWeight = luggageWeight;
		this.Request1 = request1;
		this.Request2 = request2;
		this.Request3 = request3;
	}
	
	public PremiumTicket(Customer cust, MealType meal, SeatClass seatClass, double price, Flight flight, int weight,
			String r1, String r2, String r3) {
		
		super(cust, meal, seatClass, price,flight);
		this.luggageWeight = luggageWeight;
		this.Request1 = r1;
		this.Request2 = r2;
		this.Request3 = r3;
		
		
	}

	public int getLuggageWeight() {
		return (int) luggageWeight;
	}
	public void setLuggageWeight(double luggageWeight) {
		this.luggageWeight = luggageWeight;
	}
	public String getRequest1() {
		return Request1;
	}
	public void setRequest1(String request1) {
		Request1 = request1;
	}
	public String getRequest2() {
		return Request2;
	}
	public void setRequest2(String request2) {
		Request2 = request2;
	}
	public String getRequest3() {
		return Request3;
	}
	public void setRequest3(String request3) {
		Request3 = request3;
	}

	@Override
	public String toString() {
		return super.toString() + " luggageWeight: " + luggageWeight + ", Request1: " + Request1 + ", Request2: " + Request2
				+ ", Request3: " + Request3 ;
	}
	
	
}

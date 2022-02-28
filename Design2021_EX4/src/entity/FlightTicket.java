package entity;

import util.MealType;
import util.SeatClass;

public class FlightTicket {
	
	private int orderNum;
	private int ticketNum;
	private MealType mealType;
	private SeatClass seatClass;
	private double price;
	private int CustomerPassportNumber;
	private Integer RowNum;
	private String SeatNum;
	private String TailNumber;
	private String FlightNum;
	private boolean isAvailable;
	private Customer cust;
	private Flight flight;


	public FlightTicket(int orderNum, int ticketNum, MealType mealType, SeatClass seatClass, int price,
			int customerPassportNumber, int rowNum, String seatNum, String tailNumber, String flightNum,
			boolean isAvailable) {
		super();
		this.orderNum = orderNum;
		this.ticketNum = ticketNum;
		this.mealType = mealType;
		this.seatClass = seatClass;
		this.price = price;
		CustomerPassportNumber = customerPassportNumber;
		RowNum = rowNum;
		SeatNum = seatNum;
		TailNumber = tailNumber;
		FlightNum = flightNum;
		this.isAvailable = isAvailable;
	}
	


	public FlightTicket(int orderNum, int ticketNum, SeatClass valueOf, Customer cust, Flight flight) {
		this.orderNum = orderNum;
		this.ticketNum = ticketNum;
		this.seatClass = valueOf;
		this.cust = cust;
		this.flight = flight;
		this.CustomerPassportNumber = cust.getPassportNum();
		
	}
	
	public FlightTicket(Customer cust2, MealType meal, SeatClass seatClass2, double price2, Flight flight2, int CustomerPassportNumber) {
		this.cust = cust2;
		this.mealType = meal;
		this.seatClass = seatClass2;
		this.price = price2;
		this.flight = flight2;
		this.CustomerPassportNumber = CustomerPassportNumber;
		
	}
	
	public FlightTicket(Customer cust2, MealType meal, SeatClass seatClass2, double price2, Flight flight2) {
		this.cust = cust2;
		this.mealType = meal;
		this.seatClass = seatClass2;
		this.price = price2;
		this.flight = flight2;
		
	}



	@Override
	public String toString() {
				
		return "FlightTicket - Customer's passport number: " + CustomerPassportNumber + ", meal: " + mealType + ", Class: " + seatClass + ", price: " + price + ", row: " +RowNum + ", seat number: " + SeatNum ;
	}
	
	



	public Customer getCust() {
		return cust;
	}



	public void setCust(Customer cust) {
		this.cust = cust;
	}



	public Flight getFlight() {
		return flight;
	}



	public void setFlight(Flight flight) {
		this.flight = flight;
	}



	public int getOrderNum() {
		return orderNum;
	}




	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}




	public int getTicketNum() {
		return ticketNum;
	}




	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}




	public MealType getMealType() {
		return mealType;
	}




	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}




	public SeatClass getSeatClass() {
		return seatClass;
	}




	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(int price) {
		this.price = price;
	}




	public int getCustomerPassportNumber() {
		return CustomerPassportNumber;
	}




	public void setCustomerPassportNumber(int customerPassportNumber) {
		CustomerPassportNumber = customerPassportNumber;
	}




	public int getRowNum() {
		return RowNum;
	}




	public void setRowNum(int rowNum) {
		RowNum = rowNum;
	}




	public String getSeatNum() {
		return SeatNum;
	}




	public void setSeatNum(String seatNum) {
		SeatNum = seatNum;
	}




	public String getTailNumber() {
		return TailNumber;
	}




	public void setTailNumber(String tailNumber) {
		TailNumber = tailNumber;
	}




	public String getFlightNum() {
		return FlightNum;
	}




	public void setFlightNum(String flightNum) {
		FlightNum = flightNum;
	}




	public boolean isAvailable() {
		return isAvailable;
	}




	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		double result = 1;
		result = prime * result + CustomerPassportNumber;
		result = prime * result + ((FlightNum == null) ? 0 : FlightNum.hashCode());
		result = prime * result + RowNum;
		result = prime * result + ((SeatNum == null) ? 0 : SeatNum.hashCode());
		result = prime * result + ((TailNumber == null) ? 0 : TailNumber.hashCode());
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result + ((mealType == null) ? 0 : mealType.hashCode());
		result = prime * result + orderNum;
		result = prime * result + price;
		result = prime * result + ((seatClass == null) ? 0 : seatClass.hashCode());
		result = prime * result + ticketNum;
		return (int) result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightTicket other = (FlightTicket) obj;
		if (CustomerPassportNumber != other.CustomerPassportNumber)
			return false;
		if (FlightNum == null) {
			if (other.FlightNum != null)
				return false;
		} else if (!FlightNum.equals(other.FlightNum))
			return false;
		if (RowNum != other.RowNum)
			return false;
		if (SeatNum == null) {
			if (other.SeatNum != null)
				return false;
		} else if (!SeatNum.equals(other.SeatNum))
			return false;
		if (TailNumber == null) {
			if (other.TailNumber != null)
				return false;
		} else if (!TailNumber.equals(other.TailNumber))
			return false;
		if (isAvailable != other.isAvailable)
			return false;
		if (mealType != other.mealType)
			return false;
		if (orderNum != other.orderNum)
			return false;
		if (price != other.price)
			return false;
		if (seatClass != other.seatClass)
			return false;
		if (ticketNum != other.ticketNum)
			return false;
		return true;
	}






	
	
	

	
	


	

}

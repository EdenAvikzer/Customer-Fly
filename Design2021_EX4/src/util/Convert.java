package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class Convert {
	
	private static Convert _instance;

	private Convert() {
	}

	public static Convert getInstance() {
		if (_instance == null)
			_instance = new Convert();
		return _instance;
	}
	
	public FlightStatus convertFlightStatus (String s) {
		if(s.equals("ON_TIME"))
			return FlightStatus.OnTime; 
		if(s.equals("DELAYED"))
			return FlightStatus.Delayed;
		return FlightStatus.cancelled;
	}
	
	public SeatClass convertSeatClass (String s) {
		if(s.equals("FIRST_CLASS"))
			return SeatClass.first; 
		if(s.equals("BUISNESS_CLASS"))
			return SeatClass.economy;
		return SeatClass.buisness;
	}
	
	public PamentMethod convertPaymentMethod (String s) {
		if(s.equals("ON_TIME"))
			return PamentMethod.BankTransfer; 
		if(s.equals("DELAYED"))
			return PamentMethod.Credit;
		return PamentMethod.PayPal;
	}
	
	public MealType convertMealType (String s) {
		if(s.equals("NON"))
			return MealType.NoMeal; 
		if(s.equals("REGULAR"))
			return MealType.Regular;
		if(s.equals("VEGETARIAN"))
			return MealType.Vegetarian; 
		if(s.equals("VEGAN"))
			return MealType.Vegan;
		return MealType.Kosher;
	}
	
	
	public TicketStatus convertTicketStatus (String s) {
		if(s!=null && s.equals("CANCELED")) 
			return TicketStatus.CANCELED;
		return TicketStatus.CONFIRMED;
	}
	
	public ItemType convertItemType (String s) {
		if(s!=null && s.equals("Computer game")) 
			return ItemType.GAME;
		return ItemType.MOVIE;
	}
	
	public boolean convertAvailable (String s) {
		if(s!=null && s.equals("True")) 
			return true;
		return false;
	}
	
	public LocalDateTime convertDate (String date) {
		String year="";
		String month="";
		String day="";
		String hour="";
		String minute="";
		
		char[] myStr = date.toCharArray();
		
		for(int i=0; i<4; i++)
			year += myStr[i];
		
		for(int i=5; i<7; i++)
			month += myStr[i];
		
		for(int i=8; i<10; i++)
			day += myStr[i];
		
		for(int i=11; i<13; i++)
			hour += myStr[i];
		
		for(int i=14; i<16; i++)
			minute += myStr[i];
		
		
		int hourTime = Integer.parseInt(hour);
		int minuteTime = Integer.parseInt(minute);		
		int yearDate = Integer.parseInt(year);
		int monthDate = Integer.parseInt(month);
		int dayDate = Integer.parseInt(day);
		return LocalDateTime.of(yearDate, monthDate, dayDate, hourTime, minuteTime, 0);
	}
	
	/* Converts Access Date type to LocalDateTime */
	public LocalDateTime convertDate (ResultSet rs, int i) {
		LocalDateTime time = null;
		try {
			time = rs.getTimestamp(i).toInstant()
				      .atZone(ZoneId.systemDefault())
				      .toLocalDateTime();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

}

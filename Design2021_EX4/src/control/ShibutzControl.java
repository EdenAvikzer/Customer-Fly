package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import org.exolab.castor.types.DateTime;

import entity.CustomerRelatedTo;
import entity.Flight;
import entity.FlightTicket;
import entity.Seat;
import util.Consts;
import util.FlightStatus;
import util.MealType;
import util.SeatClass;

public class ShibutzControl {

	private static ShibutzControl instance;
	public static ShibutzControl getInstance() 
	{
		if (instance == null)
			instance = new ShibutzControl();
		return instance;
	}
	
	
	//This method returns all flights for 'shibutz' (scheduling)
		public ArrayList<Flight> getAllFlightsToShibutz()
		{
			Date today = java.sql.Date.valueOf(LocalDate.now());
			ArrayList<Flight> allFlights = new ArrayList<Flight>();
			allFlights.addAll(getAllFlights());
			
			return allFlights;
		}
	
		
		public ArrayList<Flight> getAllFlights() {
			
			int flightNum;
			Timestamp dDate;
			Timestamp lDate;
			FlightStatus status;
			String tailNum;
			String depAirport;
			String desAirport;
			
			
			ArrayList<Flight> flightsList = new ArrayList<Flight>();
			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(util.Consts.FLIGHT_FOR_SHIBUTZ);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						
						flightNum = rs.getInt(i++);
						String flightNumStr = String.valueOf(flightNum);
						
						
						
						dDate = rs.getTimestamp(i++);
						lDate = rs.getTimestamp(i++);
						
						String s = rs.getString(i++);
						if(s.equals("Cancelled")) {
							status = FlightStatus.cancelled;
						}  
						if(s.equals("OnTime")) {
							status = FlightStatus.OnTime;
						}
						else {
							status = FlightStatus.Delayed;
						}
							
						tailNum	= rs.getString(i++);
						depAirport = rs.getString(i++);
						desAirport = rs.getString(i++);
						
						Flight f = new Flight(flightNumStr,dDate,lDate, status, tailNum, depAirport, desAirport);
						flightsList.add(f);
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return flightsList;	
		}
	
		//TICKETS_BY_FLIGHT
		public ArrayList<FlightTicket> getAllTicketBuyers(Flight flight) {
			
			ArrayList<FlightTicket> tickets = new ArrayList<FlightTicket>();
			
			int orderNum;
			int ticketNum;
			MealType mealType = null;
			SeatClass seatClass = null;
			int price;
			int CustomerPassportNumber;
			int RowNum;
			String SeatNum;
			String TailNumber;
			String FlightNum;
			String isAvailable;

			
			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement callst = conn.prepareCall(Consts.TICKETS_BY_FLIGHT)) 
				{
					int i = 1;
					callst.setString(i++, flight.getFlightNum());
					ResultSet rs = callst.executeQuery();
					
					while (rs.next()) {
						i=1;
						ticketNum = rs.getInt(i++); 
						orderNum = rs.getInt(i++);
						CustomerPassportNumber = rs.getInt(i++);
						
						
						String s = rs.getString(i++);
						if(s.equals("Kosher")) {
							mealType = MealType.Kosher;
						}  
						if (s.equals("No meal")) {
							mealType = MealType.NoMeal;
						}
						if (s.equals("Regular")){ 
							mealType = MealType.Regular;
						}
						if (s.equals("Vegan")) {
							mealType = MealType.Vegan;
						}
						if (s.equals("Vegetarian")) {
							mealType = MealType.Vegetarian;
						}
						
						String s2 = rs.getString(i++);
						if (s2.equals("economy")){ 
							seatClass = SeatClass.economy;
						}
						if (s2.equals("Business")) {
							seatClass = SeatClass.buisness;
						}
						if (s2.equals("first")) {
							seatClass = SeatClass.first;
						}
						
						price = rs.getInt(i++);
						FlightNum = rs.getString(i++);
						isAvailable = rs.getString(i++);
						TailNumber = rs.getString(i++);
						RowNum = rs.getInt(i++);
						SeatNum = rs.getString(i++);
						
						
						
						
						
						FlightTicket ticket = new FlightTicket(orderNum,ticketNum,mealType , seatClass ,price,CustomerPassportNumber,RowNum,SeatNum, TailNumber, FlightNum, isAvailable.equals("Yes")? true:false);
						tickets.add(ticket);
						}
				}
							
					
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
				e.printStackTrace();
				}	
	
			
			return tickets;
			
		}


		public ArrayList<Seat> allAvalibleSeats(Flight flightSelected) {
			
			String aiprlane = flightSelected.getAirplane().getTailNumber();
			
			ArrayList<Seat> seatsList = new ArrayList<Seat>();

			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_SEATS_BY_PLANE))
				{
					int k=1;
					callst.setString(k++, aiprlane);
					ResultSet rs = callst.executeQuery();
					int i;
					while (rs.next()) 
					{
						i=1;
						int row = rs.getInt(i++);
						String SeatNum = rs.getString(i++);
						
						SeatClass seatClass = null;
						
						String s2 = rs.getString(i++);
						if (s2.equals("economy")){ 
							seatClass = SeatClass.economy;
						}
						if (s2.equals("buisness")) {
							seatClass = SeatClass.buisness;
						}
						if (s2.equals("first")) {
							seatClass = SeatClass.first;
						}
						
						String number = rs.getString(i++);
						
						Seat s = new Seat(row, SeatNum,seatClass ,number);
						seatsList.add(s);
					}
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			ArrayList<FlightTicket> tickets = getAllTicketBuyers(flightSelected);
			ArrayList<Seat> toReturn = new ArrayList<Seat>();
			
			
			for (Seat s :seatsList) {
				boolean flag = true;
				for (FlightTicket ft: tickets) {
					if (s.getRowNum() == ft.getRowNum() && s.getSeatNum().equals(ft.getSeatNum()) ) {
						flag = false;
					}
				}
				if (flag) {
					toReturn.add(s);
					System.out.println("seat added" + s.getRowNum() + ", " + s.getSeatNum());
				}
			}
				
			return toReturn;
			
			
		}
		
		//SQL_UPDATE_SEAT
		//This method row, seat and class and return true if can do the shibutz
		public boolean doShibutz(int row2, String seat2, SeatClass class2,  String tailNumber, FlightTicket selectedFlightTicket) throws ClassNotFoundException, SQLException
		{
			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPDATE_SEAT)){
					int i = 1;
					stmt.setInt(i++, row2);
					stmt.setString(i++, seat2);
					stmt.setString(i++, tailNumber);
					stmt.setLong(i++, selectedFlightTicket.getTicketNum());
					stmt.setLong(i++, selectedFlightTicket.getOrderNum());
					
					stmt.executeUpdate();
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return false;
			

		}
		
		public ArrayList<CustomerRelatedTo> getAllRelationsBetweenCustomers() {
			
			int customer1;
			int customer2;
			
			
			
			ArrayList<CustomerRelatedTo> relations = new ArrayList<CustomerRelatedTo>();
			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_CUSTOMER_RELATED);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						
						customer1 = rs.getInt(i++);
						customer2 = rs.getInt(i++);
						
						CustomerRelatedTo relation = new CustomerRelatedTo(customer1, customer2);
						relations.add(relation);
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return relations;	
		}
	
	
}

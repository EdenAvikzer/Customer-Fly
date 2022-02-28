package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JFrame;
import entity.Airport;
import entity.Customer;
import entity.Flight;
import entity.FlightTicket;
import entity.Order;
import entity.PremiumTicket;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;
import util.MealType;
import util.PamentMethod;
import util.SeatClass;

public class OrderControl {
	
	private static OrderControl _instance;
	static Random rand = new Random();
	private static int orderNumber = rand.nextInt(10000000);

	private OrderControl() {
	}

	public static OrderControl getInstance() {
		if (_instance == null)
			_instance = new OrderControl();
		return _instance;
	}
	
	
	/**
	 * fetches all orders from DB file.
	 * @return ArrayList of orders.
	 */
	public ArrayList<Order> getOrders() {
		ArrayList<Order> results = new ArrayList<Order>();
		try {
			
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ORDERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					int orderNum = rs.getInt(i++); 
					Timestamp orderDate = rs.getTimestamp(i); i++;
					PamentMethod paymentM = util.Convert.getInstance().convertPaymentMethod(rs.getString(i));
					
					results.add(new Order(orderNum, orderDate,paymentM));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	
	/**
	 * fetches all orders from DB file.
	 * @return ArrayList of orders.
	 */
	public ArrayList<entity.ConstsPrice> ConstsPrice() {
		ArrayList<entity.ConstsPrice> results = new ArrayList<entity.ConstsPrice>();
		try {
			
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PRICE_CONSTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					int T = rs.getInt(i++); 
					int M = rs.getInt(i++); 
					int D = rs.getInt(i++); 
					int pricePerKilo = rs.getInt(i++); 
					
					results.add(new entity.ConstsPrice(T,M,D,pricePerKilo)) ;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * 
	 * This method call to relevant methods and create order and add the order an all the tickets to DB
	 * @param ticks
	 * @param pm
	 * @param passport 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean makeOrder(ArrayList<FlightTicket> ticks, PamentMethod pm) throws ClassNotFoundException, SQLException {
		
		int tickNum =100;
		System.out.println(orderNumber + " " +  pm.toString());
		Order order = new Order(orderNumber++, pm);
		
		System.out.println("\norderNumber " + orderNumber + "pm " + pm);
		System.out.println("order " + order);
		
		if(insertOrder(order))
			System.out.println("\n Order was added successfully");
		System.out.println(getOrders());
		
		for(FlightTicket t: ticks) {
			
			if(t==null) return false;
			
			System.out.println(t.getCustomerPassportNumber() + " for check");
			if(insertTicket(t, tickNum, order.getOrderNum()))
				System.out.println("\n Ticket was added successfully");
				
			if(t instanceof PremiumTicket) {	      
				insertPremium((PremiumTicket) t, tickNum, order.getOrderNum());   
			}
			tickNum++;	
		}	
		return true;
		
	}
	
	
	
	
	
	/*************************** ADD ORDER / TICKET / PREMIUM 	************************************/
	
	//this method insert new Order to DB
		private static boolean insertOrder(Order order)
		{
			try {
				if(order == null)
				{
					return false;
				}
				System.out.println("AM I HERE?????? " + order);
				Class.forName(util.Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_ORDER)){
					stmt.setInt(1, order.getOrderNum());
					System.out.println("\norder.getOrderNum() : " + order.getOrderNum());
					stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
					System.out.println("\n Timestamp.valueOf(LocalDateTime.now()" + Timestamp.valueOf(LocalDateTime.now()));
					if (order.getPamentMethod().toString().equals(PamentMethod.BankTransfer.toString())) {
						stmt.setString(3,"Bank transfer" );
					} else if ((order.getPamentMethod().toString().equals(PamentMethod.Credit.toString()))) {
						stmt.setString(3,"Credit" );
					} else {
						stmt.setString(3,"PayPal" );
					}
					stmt.executeUpdate();
					return true;	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;	
			
		}
		
		
		
		//this method insert new Ticket to DB
		private static boolean insertTicket(FlightTicket ticket, int tickNum, int OrderNum) throws ClassNotFoundException, SQLException {
			
			System.out.println(tickNum + " " + OrderNum);
			if(ticket == null)
			{
				return false;
			}
			try {
			
			Class.forName(util.Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_TICKET)){
				int i = 1;	
				stmt.setInt(i++, tickNum);
				stmt.setInt(i++, OrderNum);
				stmt.setInt(i++, ticket.getCustomerPassportNumber());
				System.out.println("CUST: " + ticket.getCustomerPassportNumber());
				
				if (ticket.getMealType().toString().equals(MealType.NoMeal.toString())) {
					stmt.setString(i++,"No meal");
				} else if (ticket.getMealType().toString().equals(MealType.Kosher.toString())) {
					stmt.setString(i++,"Kosher");
				} else if (ticket.getMealType().toString().equals(MealType.Regular.toString())) {
					stmt.setString(i++,"Regular");
				} else if (ticket.getMealType().toString().equals(MealType.Vegan.toString())) {
					stmt.setString(i++,"Vegan");
				}
				else {
					stmt.setString(i++,"Vegetarian");
				}
				

				if (ticket.getSeatClass().toString().equals(SeatClass.buisness.toString())) {
					stmt.setString(i++,"Business");
				} else if (ticket.getMealType().toString().equals(SeatClass.first.toString())) {
					stmt.setString(i++,"first");
				} else {
					stmt.setString(i++,"economy");
				}

				stmt.setDouble(i++, ticket.getPrice());
				stmt.setString(i++, ticket.getFlight().getFlightNum());
				stmt.setString(i++, "Yes");

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
	
		
		
		//this method insert new Premium Ticket to DB
		private static boolean insertPremium(PremiumTicket ticket, int tickNum, int OrderNum) throws ClassNotFoundException, SQLException
		{
			
			if(ticket == null)
			{
				return false;
			}
			
			Class.forName(util.Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_PREMIUM)){
				int i = 1;	
				stmt.setInt(i++, tickNum);
				stmt.setInt(i++, OrderNum);
				stmt.setInt(i++, ticket.getLuggageWeight());
				stmt.setString(i++, ticket.getRequest1());
				stmt.setString(i++, ticket.getRequest2());
				stmt.setString(i++, ticket.getRequest3());
				stmt.executeUpdate();
				
			}
			return true;	
		}
	
	
	/*------------------------------Calculate Price  -----------------------------*/
	
	public double calcPrice(Flight flight, MealType meal, SeatClass seatClass, double weight) {
		
			entity.ConstsPrice consts = ConstsPrice().get(0) ;
			System.out.println(consts);
			
			int realM = meal.equals(MealType.NoMeal)? 0 :  1 ;
			
			int realD = 0;
			if(seatClass.equals(SeatClass.buisness))
				realD = 1;
			else
				realD = 2;
			
			long realT =  ChronoUnit.HOURS.between(flight.getDepartureTime().toLocalDateTime(), flight.getLandingTime().toLocalDateTime());
			
			System.out.println("realT " + realT);
		
			return consts.getMulM() * realM + consts.getMulD()* realD + consts.getMulT() * realT + consts.getPricePerKilo() * weight;
	}
	
	

	/*------------------------------Generate Report -----------------------------*/
	public  JFrame exportOrdersReport() 
	{
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> par = new HashMap<>();
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/OrdersReport.jasper"), par, conn);
				JFrame frame = new JFrame("Report");				
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//SQL_SEL_CUSTBYID
	
	public Customer getCustByID(int customerNum) {
		
		Customer c = null;
		int number;
	
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_CUSTBYID)) 
			{
				int i = 1;
				callst.setInt(i++, customerNum);
				ResultSet rs = callst.executeQuery();
				
				while (rs.next()) {
					i=1;
					number = rs.getInt(i++); 

					c = new Customer(number);
					}
			}
						
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
			e.printStackTrace();
			}	

		
		return c;
		
	}

	
	public boolean addRelative(int id1, int id2) throws ClassNotFoundException, SQLException
	{
		
		
		Class.forName(util.Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_CUSTOMERRELATEDTO)){
			int i = 1;	
			stmt.setInt(i++, id1);
			stmt.setInt(i++, id2);
			stmt.executeUpdate();
			
		}
		return true;	
	}
	
	

	public boolean updateParameters(int d, int m, int t, int price ) throws ClassNotFoundException, SQLException
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPDATE_PARAMETERS)){
				int i = 1;
				stmt.setDate(i++, Date.valueOf(LocalDate.now()));
				stmt.setInt(i++, d);
				stmt.setInt(i++, m);
				stmt.setInt(i++, t);
				stmt.setInt(i++, price);
				
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
	
	
}
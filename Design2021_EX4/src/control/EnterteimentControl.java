package control;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import entity.Flight;
import entity.Item;
import entity.ItemBySupplier;
import entity.ItemInFlight;
import entity.Order;
import entity.Supplier;
import util.Consts;
import util.FlightStatus;
import util.ItemType;;


public class EnterteimentControl {
	
	private static EnterteimentControl _instance;
	static Random rand = new Random();
	private static int supplierNum = rand.nextInt(10000000);
	private static int itemNum = rand.nextInt(10000000);

	private EnterteimentControl() {
	}

	public static EnterteimentControl getInstance() {
		if (_instance == null)
			_instance = new EnterteimentControl();
		return _instance;
	}
	
	
    /*----------------------------------------------------------------------------------------------------------*/
	/*--------------------------------------- Get Methods ---------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------------*/	 

	
	/**
	 * fetches all Items from DB file.
	 * @return ArrayList of orders.
	 */
	public ArrayList<Item> getItems() {
		ArrayList<Item> results = new ArrayList<Item>();
		try {
			
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ITEMS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					int itemId = rs.getInt(i++); 
					String name = rs.getString(i++); 
					String desc = rs.getString(i++);
					ItemType type = util.Convert.getInstance().convertItemType(rs.getString(i++));   
					boolean available = util.Convert.getInstance().convertAvailable(rs.getString(i++));
					Item item = new Item(itemId, name, type, desc, available);
					results.add(item);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	//SQL_SEL_FLIGHTS
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
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_FLIGHTS);
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
	
	public ArrayList<ItemInFlight> getAllItemsInFlights(String FlightID) {
		ArrayList<ItemInFlight> results = new ArrayList<ItemInFlight>();
		try {
			
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_ALLITEMSINFLIGHT)) 
			{
				
				int i = 1;
				callst.setString(i++, FlightID);
				ResultSet rs = callst.executeQuery();
				
				while (rs.next()) {
					i=1;
					String flightID = rs.getString(i++); 
					int supId = rs.getInt(i++); 
					int itemId = rs.getInt(i++); 
					String feedback = rs.getString(i++); 
					  				
					results.add(new ItemInFlight(flightID, itemId, supId,feedback));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	
	public ArrayList<Supplier> getSuppliers() {
		ArrayList<Supplier> results = new ArrayList<Supplier>();
		try {
			
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_SUPPLIERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					int supId = rs.getInt(i++); 
					String supName = rs.getString(i++); 
					String supPhone = rs.getString(i++); 
					String supEmail = rs.getString(i++); 
					  				
					results.add(new Supplier(supId, supName, supPhone,supEmail));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<ItemBySupplier> getItemBySupps(int suppNum) {
		ArrayList<ItemBySupplier> results = new ArrayList<ItemBySupplier>();
		try {
			
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_ITEMBYSUP)) 
			{
				
				int i = 1;
				callst.setInt(i++, suppNum);
				ResultSet rs = callst.executeQuery();
				
				while (rs.next()) {
					i=1;
					
					int supId = rs.getInt(i++); 
					int itemId = rs.getInt(i++); 
  				
					results.add(new ItemBySupplier(itemId, supId));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	 public boolean updateFeedback(String feedback, ItemInFlight itemFlight) {
			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_UPDATE_FEEDBACK)) {
					
					stmt.setString(1, feedback);
					stmt.setString(2, itemFlight.getFlightID());
					stmt.setInt(3, itemFlight.getItemID());
					stmt.setInt(4, itemFlight.getSupplierID());
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
	 
	//this method insert new ItemBySupplier to DB
		public boolean insertItemToFlight(Flight flight, int itemNum, int supplierNum) throws ClassNotFoundException, SQLException
		{
				Class.forName(util.Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_ITEMTOFLIGHT)){
					int i = 1;	
					stmt.setString(i++, flight.getFlightNum());
					stmt.setInt(i++, itemNum);
					stmt.setInt(i++, supplierNum );
							
					stmt.executeUpdate();
							
				}
				return true;	
		}
		
		//this method insert new supplier to DB
	public boolean insertSupplier(Supplier sup) throws ClassNotFoundException, SQLException {
				
			if(sup == null)
			{
				return false;
			}
			
			Class.forName(util.Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_SUPPLIER)){
				int i = 1;	
				stmt.setInt(i++, supplierNum);
				stmt.setString(i++, sup.getSupName() );
				stmt.setString(i++, sup.getSupPhone() );
				stmt.setString(i++, sup.getSupMail());
				
				
				stmt.executeUpdate();
				
			}
			return true;	
	}
	
	
	//this method insert new Item to DB
	public boolean insertItem(Item item) throws ClassNotFoundException, SQLException	{
				
				Class.forName(util.Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_ITEM)){
					int i = 1;	
					stmt.setInt(i++, itemNum);
					stmt.setString(i++, item.getItemName() );
					if ( item.getType().toString().equals("MOVIE")) {
						stmt.setString(i++, "Movie" );
					} else {
						stmt.setString(i++, "Computer game" );
					}
					
					stmt.setString(i++, item.getDescription());
					stmt.setString(i++, "True");
					stmt.executeUpdate();
					
				}
				return true;	
	}
	
	//this method insert new ItemBySupplier to DB
		public boolean insertItemBySupplier(ItemBySupplier ibs) throws ClassNotFoundException, SQLException {
					
					if(ibs == null)
					{
						return false;
					}
					
					Class.forName(util.Consts.JDBC_STR);
					try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
							CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_ITEMBYSUPPLIER)){
						int i = 1;	
						stmt.setInt(i++, ibs.getItemId() );
						stmt.setInt(i++, ibs.getSupID() );
						
						stmt.executeUpdate();
						
					}
					return true;	
			}
		
		
		
		
		
		
		
		
		
	
	
		
		
		
		
		
		
		
		
		
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * fetches all Item by supplier  from DB file.
	 * @return ArrayList of orders.
	 */
	public ArrayList<ItemBySupplier> getItemsBySupplier() {
		ArrayList<ItemBySupplier> results = new ArrayList<ItemBySupplier>();
		try {
			
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ITEMBYSUP);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					int itemId = rs.getInt(i++); 
					int supId = rs.getInt(i++); 
  				
					results.add(new ItemBySupplier(itemId, supId));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	
	
	// This function return items of a given supplier
	public ArrayList<Item> getItemsBySupplier(Supplier supplier){
		
		System.out.println("\n supplier" + supplier);
		
		ArrayList<Item> items = getItems();
		
		ArrayList<Item> toReturn = new ArrayList<Item>();
		
		System.out.println(items);
		
		ArrayList<ItemBySupplier> itemsbysup =  getItemsBySupplier();
		
		System.out.println(itemsbysup);
		
		System.out.println(" \n BEFORE" +items);
		
		for(ItemBySupplier i: itemsbysup ) {
			if(i.getSupID()==supplier.getSupId()) {
				System.out.println("i.getSupID() " + i.getSupID() + " " + "supplier.getSupId() " + supplier.getSupId() );
					for(Item it: items) {
						if(i.getItemId()==it.getItemId()) {
							System.out.println("i.getItemId() " + i.getItemId() + " " + "it.getItemId() " + it.getItemId());
							toReturn.add(it);
						}
					}				
			}		
		}
		
//		for(Item i: items) {
//			for(ItemBySupplier j: itemsbysup)
//				
//		}
			
		System.out.println(" \n AFTER" +toReturn);
		
		if(!toReturn.isEmpty())
			return toReturn;
		
		else {
			System.out.println("\n We have a problen in getItemsBySupplier method at EntertaimentLogic ");
			return toReturn;
		}	
	}
	
	
	/**
	 * fetches all itemsInFlight Items from DB file.
	 * @return ArrayList of orders.
	 */
	public ArrayList<ItemInFlight> getItemsInFlights() {
		ArrayList<ItemInFlight> results = new ArrayList<ItemInFlight>();
		try {
			
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ITEMSINFLIGHT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String flightID = rs.getString(i++); 
					int supId = rs.getInt(i++); 
					int itemId = rs.getInt(i++); 
					String feedback = rs.getString(i++); 
					  				
					results.add(new ItemInFlight(flightID, itemId, supId,feedback));
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
	 * @param flight
	 * @return items in given flight.
	 */
	public ArrayList<Item> getAllItemsInFlight(Flight flight){
		
		ArrayList<Item> toReturn = new ArrayList<Item>();
		
		ArrayList<ItemInFlight> itemsList = getItemsInFlights();
		for(ItemInFlight i: itemsList) {
			if(i.getFlightID().equals(flight.getFlightNum())) {
				for(Item item: getItems()) {
					if(item.getItemId()==i.getItemID())
						toReturn.add(item);
				}
			}
		}
		
		return toReturn;

	}
	

	
    /*----------------------------------------------------------------------------------------------------------*/
	/*---------------------------------------Add  ---------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------------*/	 


			
			
	
	
	
	
		
		
		
		
	    /*----------------------------------------------------------------------------------------------------------*/
		/*---------------------------------------Update  ---------------------------------------------------*/
		/*----------------------------------------------------------------------------------------------------------*/	 

	
	 public boolean updateItemStatus(int itemId, String status) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_UPDATE_ITEM_STATUS)) {
					
					stmt.setString(1, status);
					stmt.setInt(2, itemId);
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
	 
	
	
}

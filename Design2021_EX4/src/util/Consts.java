package util;

import java.net.URLDecoder;

public class Consts 
{
	private Consts() {
		throw new AssertionError();
	}
	
	
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";
	
	
	public static final String SQL_SEL_FLIGHT ="SELECT tbl_Flight.FlightNum FROM tbl_Flight;";
	public static final String SQL_SEL_FLIGHTS = "SELECT * FROM tbl_Flight";
	public static final String SQL_SEL_FLIGHTTICKET_BY_CUSTOMER = "call quer_tickets_by_customer(?)";
	public static final String SQL_SEL_CUSTOMERS_BY_FLIGHT = "call sql_customers_for_flight(?)";
	public static final String SQL_SEL_SEATS_BY_PLANE = "call quer_seats_by_plane(?)";
	public static final String SQL_SEL_SEATS_BY_CLASS = "call quer_seats_by_class(?)";
	public static final String SQL_SEL_ALL_SHOWS_RECOM = "call quer_recommend_New_Flights(?)";
	public static final String SQL_SEL_FLIGHT_BY_NUMBER = "call quer_flight_by_number(?)";
	public static final String SQL_SEL_AIRPORTS = "SELECT tbl_Airport.* FROM tbl_Airport";
	public static final String SQL_SEL_AIRPLANE = "SELECT tbl_Airplane.* FROM tbl_Airplane";
	public static final String SQL_SEL_CUSTOMER = "SELECT tbl_Customer.* FROM tbl_Customer";
	public static final String SQL_SEL_CUSTOMER_RELATED = "SELECT tbl_CustomerRelatedTo.* FROM tbl_CustomerRelatedTo";
	public static final String SQL_SEL_ORDERS = "SELECT * FROM tbl_Order";
	public static final String SQL_SEL_ITEMS = "SELECT tbl_Item.* FROM tbl_Item";
	public static final String SQL_SEL_SUPPLIERS = "SELECT * FROM tbl_Supplier";
	public static final String SQL_SEL_ITEMBYSUP = "call quer_get_items_by_supp(?)";
	public static final String SQL_SEL_ALLITEMSINFLIGHT = "call quer_Get_Items_In_Flight(?)";
	public static final String SQL_SEL_CUSTBYID = "call quer_get_cust_by_id(?)";
	
	public static final String SQL_SEL_ITEMSINFLIGHT = "SELECT * FROM tbl_ItemBySupplierInFlight";
	public static final String SQL_SEL_PRICE_CONSTS = " SELECT tbl_GeneralParameters.D, tbl_GeneralParameters.M, tbl_GeneralParameters.T, tbl_GeneralParameters.PricePerKG FROM tbl_GeneralParameters  ";
	
	public static final String SQL_FLIGHT_BY_UPDATE_DATE = "SELECT tbl_Flight.FlightNum, tbl_Flight.DepartureTime, tbl_Flight.LandingTime, tbl_Flight.Status, tbl_Flight.TailNumber, tbl_Flight.DepartureAirportCode, tbl_Flight.DestinationAirportCode\r\n"
			+ "FROM tbl_Flight\r\n"
			+ "WHERE (((tbl_Flight.updateDate)=Date()));\r\n"
			+ "";
	
	public static final String SQL_AIRPLANE_EXIST = "call quer_is_airplane_exist(?)";
	public static final String SQL_AIRPORT_EXIST = "call quer_is_airport_exists(?)";
	
	public static final String SQL_INS_FLIGHT = "{ call quer_insert_flight(?,?,?,?,?,?,?,?) }";
	public static final String SQL_INS_AIRPLANE = "{ call quer_insert_airplane(?) }";
	public static final String SQL_INS_AIRPORT = "{ call quer_insert_airport(?,?,?) }";
	public static final String SQL_INS_SEAT = "{ call quer_insert_seat(?,?,?,?) }";
	public static final String SQL_INS_CUSTOMER = "{ call quer_insert_customer(?,?,?,?,?,?,?) }";
	public static final String SQL_INS_ORDER = "{ call qryInsOrder(?,?,?) }";
	public static final String SQL_INS_TICKET = "{ call qryInsTicket(?,?,?,?,?,?,?,?) }";
	public static final String SQL_INS_PREMIUM = "{ call qryInsPremium(?,?,?,?,?,?) }";
	public static final String SQL_INS_ITEM = "{ call qryInsItem(?,?,?,?,?) }";
	public static final String SQL_INS_SUPPLIER = "{ call qryInsSupplier(?,?,?,?) }";
	public static final String SQL_INS_ITEMBYSUPPLIER = "{ call qryInsItemBySupplier(?,?) }";
	public static final String SQL_UPDATE_ITEM_STATUS = "{ call qryUpdateItemStatus(?,?) }";
	public static final String SQL_INS_ITEMTOFLIGHT = "{ call qryInsItemToFlight(?,?,?) }";
	public static final String SQL_INS_CUSTOMERRELATEDTO = "{ call quer_add_related(?,?) }";
	
	
	public static final String SQL_UPDATE_AIRPORT = "{ call quer_update_airport(?,?,?) }";
	public static final String SQL_UPDATE_FLIGHT = "{ call quer_update_flight(?,?,?,?,?,?,?,?) }";
	public static final String SQL_UPDATE_TICKET = "{ call quer_update_ticket(?,?) }";
	public static final String SQL_UPDATE_SEAT = "{ call quer_update_seat_the_customer(?,?,?,?,?) }";
	public static final String SQL_UPDATE_FEEDBACK = "{ call qryUpdateFeedback(?,?,?,?) }";
	public static final String SQL_UPDATE_PARAMETERS = "{ call quer_update_parameters(?,?,?,?,?) }";
	
	public static final String TICKETS_BY_FLIGHT = "{ call quer_tickets_by_flight(?) }";
	public static final String TICKETS_IN_ORDER = "{ call quer_amount_of_tickets_in_order(?) }";
	public static final String SEAT_IS_AVAILABLE = "{ call quer_seat_is_avialable(?,?) }";
	public static final String SQL_FLIGHT_RECOMMENDATION = "{ call quer_recommend_New_Flights(?,?,?,?,?,?) }";
	public static final String FLIGHT_FOR_SHIBUTZ = "SELECT tbl_Flight.*"
			+ "FROM tbl_Flight\r\n"
			+ "WHERE (([tbl_Flight].[DepartureTime]>Date()));\r\n"
			+ "";
	
/*---------------------------------------------------------------------------------------*/
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/src/entity/db.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "/src/entity/db.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public enum Manipulation {
    	UPDATE, INSERT, DELETE;
    }
}



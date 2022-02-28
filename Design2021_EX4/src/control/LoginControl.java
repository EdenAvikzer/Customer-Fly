package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Customer;
import util.Consts;

public class LoginControl 
{
	public static LoginControl instance;
	public static LoginControl getInstance() 
	{
		if (instance == null)
			instance = new LoginControl();
		return instance;
	}
	
	private Customer loginCustumer;
	private String admin;
	
	public Customer getLoginCustumer() {
		return loginCustumer;
	}
	public void setLoginCustumer(Customer loginCustumer) {
		this.loginCustumer = loginCustumer;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	//This method get all Customers from DB
	public ArrayList<Customer> getAllCustumers()
	{
		ArrayList<Customer> myCutstomers = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_CUSTOMER);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					myCutstomers.add(new Customer(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myCutstomers;	
		
	}
	
	//This method return true if the mail is exist, else false
	public boolean checkMail(Customer c)
	{
		ArrayList<Customer> allCustumers = getAllCustumers();
		for(Customer cus: allCustumers)
		{
			if(c.getEmail()== cus.getEmail())
			{
				return true;
			}
		}
		return false;
	}
	
	//this method gets Customer and return true if succeed to add to DB
	public boolean addNewCutsumerToDB(Customer c) throws ClassNotFoundException, SQLException
	{
		//inserting to showInTheater
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_CUSTOMER)){
			int i = 1;
			stmt.setInt(i++,c.getPassportNum());
			stmt.setString(i++, c.getFirstName());
			stmt.setString(i++, c.getLastName());
			stmt.setString(i++, c.getEmail());
			stmt.setString(i++, c.getPrimaryCitizenship());
			stmt.setDate(i++, Date.valueOf(c.getBirthDate().toLocalDate()));
			stmt.setString(i++,c.getPassword());
			stmt.executeUpdate();
		}
		return true;	
	}
}
	

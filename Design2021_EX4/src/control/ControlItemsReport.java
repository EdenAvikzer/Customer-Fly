package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JFrame;

import entity.Airport;
import entity.Flight;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;

public class ControlItemsReport {
public static ControlItemsReport instance;
	
	public static ControlItemsReport getInstance() {
		if (instance == null)
			instance = new ControlItemsReport();
		return instance;
	}
	

	public JFrame produceReport(String aiport1, String aiport2) 
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();
				
				params.put("parameter1", aiport1);
				params.put("parameter2", aiport2);

				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/ItemsReport.jasper"),
						params, conn);
				JFrame frame = new JFrame("Show Report for " + LocalDate.now());
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
	
	public Airport isAirportExists(String aiportCode) {

		String s;
		Airport a = null;
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_AIRPORT_EXIST))
					{
					int k=1;
					callst.setString(k++, aiportCode);
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
					{
						int i =1;
						s = (rs.getString(i++));
						a = new Airport(s);
						
						
					}
					
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return a;
	}
}

package boundary;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import control.ShibutzControl;
import entity.CustomerRelatedTo;
import entity.Flight;
import entity.FlightTicket;
import entity.Seat;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import util.FlightStatus;
import util.SeatClass;

public class Shibutz {

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private ListView<Flight> releventFlights;

    @FXML
    private VBox toHide;

    @FXML
    private ListView<FlightTicket> flightTicktsList;

    @FXML
    private ListView<Seat> seatsList;

    @FXML
    private TextField row;

    @FXML
    private TextField seat;
    @FXML
    private TextField classType;
    @FXML
    private AnchorPane popup;

    @FXML
    private Button xBtn;
    
    @FXML
    private TableView<CustomerRelatedTo> relatedTable;

    @FXML
    private TableColumn<CustomerRelatedTo, Integer> customer1Col;

    @FXML
    private TableColumn<CustomerRelatedTo, Integer> customer2Col;
    
    
    @FXML
	public void initialize() 
	{
		ArrayList<Flight> flights = ShibutzControl.getInstance().getAllFlightsToShibutz();
		Collections.sort(flights, new Comparator<Flight>() {
			  public int compare(Flight o1, Flight o2) {
			      if (o1.getDepartureTime() == null || o2.getDepartureTime() == null)
			        return 0;
			      return o1.getDepartureTime().compareTo(o2.getDepartureTime());
			  }
			});
		releventFlights.setItems(FXCollections.observableArrayList(flights));
		toHide.setVisible(false);
		popup.setVisible(false);

	}
    

    @FXML
    void close(ActionEvent event) {
    	popup.setVisible(false);
    	relatedTable.getItems().clear();
    }
    
    
    @FXML
    void showRelatives(ActionEvent event) {
    	popup.setVisible(true);
    	
    	ArrayList<CustomerRelatedTo> relations = new ArrayList<>(ShibutzControl.getInstance().getAllRelationsBetweenCustomers());
    	ArrayList<CustomerRelatedTo> toReturn = new ArrayList<>();
    	
    	//get the customers in the selected flight
    	Flight flightSelected = (Flight) releventFlights.getSelectionModel().getSelectedItem();
		ArrayList<FlightTicket> ticktes = ShibutzControl.getInstance().getAllTicketBuyers(flightSelected);
		
		
		for (CustomerRelatedTo r : relations) {
			boolean flag = false;
			for (FlightTicket t :ticktes) {
				if (r.getPassportNum1() == t.getCustomerPassportNumber() || r.getPassportNum2() == t.getCustomerPassportNumber()) {	
					flag=true;
				}
			}
			if (flag)
				toReturn.add(r);
		}
    	
		
    	
    	relatedTable.getItems().addAll(toReturn);
    	customer1Col.setCellValueFactory(customerRelatedTo -> new ReadOnlyObjectWrapper<Integer>(customerRelatedTo.getValue().getPassportNum1()));
    	customer2Col.setCellValueFactory(customerRelatedTo -> new ReadOnlyObjectWrapper<Integer>(customerRelatedTo.getValue().getPassportNum2()));
    	
    }

    @FXML
    void chooseFlight(ActionEvent event) {
    	
    	toHide.setVisible(true);
    	
    	Flight flightSelected = (Flight) releventFlights.getSelectionModel().getSelectedItem();
    	
		ArrayList<FlightTicket> ticktes = ShibutzControl.getInstance().getAllTicketBuyers(flightSelected);
		flightTicktsList.setItems(FXCollections.observableArrayList(ticktes));
		ArrayList<Seat> avaliableSeats = ShibutzControl.getInstance().allAvalibleSeats(flightSelected);
		seatsList.setItems(FXCollections.observableArrayList(avaliableSeats));
    }

    @FXML
    void placement(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	int row2 = Integer.parseInt(row.getText());
		String seat2 = seat.getText();
		SeatClass class2 = SeatClass.valueOf(classType.getText());
		Flight flightSelected = (Flight) releventFlights.getSelectionModel().getSelectedItem();
		FlightTicket selectedFlightTicket = (FlightTicket) flightTicktsList.getSelectionModel().getSelectedItem();
		
		Boolean flag = ShibutzControl.getInstance().doShibutz(row2, seat2, class2, flightSelected.getAirplane().getTailNumber(),selectedFlightTicket);
		
		if(flag)
		{//sucssesful shibutz
			
			toHide.setVisible(false);
			Alert alert = new Alert(AlertType.INFORMATION,"placement Sucssed");
			alert.setHeaderText("Sucsses");
			alert.setTitle("placement Sucssed");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR,"placement Faild, please check your row and seat");
			alert.setHeaderText("placement Error");
			alert.setTitle("placement Error");
			alert.showAndWait();
		}
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/Login.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}

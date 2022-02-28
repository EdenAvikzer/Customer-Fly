

package boundary;

import java.sql.SQLException;
import java.util.ArrayList;


import control.EnterteimentControl;
import control.OrderControl;
import control.ShibutzControl;
import control.importControl;
import entity.Customer;
import entity.Flight;
import entity.FlightTicket;
import entity.Item;
import entity.PremiumTicket;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import sun.security.krb5.internal.Ticket;
import util.MealType;
import util.PamentMethod;
import util.SeatClass;

public class OrderTickets {
    
    
    @FXML
    private ListView<Item> itemsInFlightList;

    @FXML
    private ListView<Flight> flightsList;

    @FXML
    private ComboBox<MealType> comboMeal;
    
    @FXML
    private ComboBox<SeatClass> comboClass;

    @FXML
    private ListView<FlightTicket> ticketsInOrder;
    
    ArrayList<FlightTicket> tickets = new ArrayList<FlightTicket>() ;

    @FXML
    private Label price;

    @FXML
    private TextField buggage;
    
    @FXML
    private RadioButton premuim;
    
    @FXML
    private TextField customerId;

    @FXML
    private TextField reqA;

    @FXML
    private TextField reqB;

    @FXML
    private TextField reqC;
    
    @FXML
    private ComboBox<PamentMethod> comboPayment;
    
    @FXML
    private TextField relativePassport;




    public void initialize() {	
    	
    	flightsList.setItems(FXCollections.observableArrayList(ShibutzControl.getInstance().getAllFlightsToShibutz()));

    	flightsList.setOnMouseClicked(event -> {
    		if(flightsList.getSelectionModel().getSelectedItem()!=null) {
    			itemsInFlightList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getAllItemsInFlight(flightsList.getSelectionModel().getSelectedItem())));
    		}	
    	});	
    	
    	comboMeal.setItems(FXCollections.observableArrayList(MealType.values()));
    	comboClass.setItems(FXCollections.observableArrayList(SeatClass.values()));
    	comboPayment.setItems(FXCollections.observableArrayList(PamentMethod.values()));
    	
    	
    	premuim.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (premuim.isSelected()) { 
					buggage.setDisable(false);
					reqA.setDisable(false);
					reqB.setDisable(false);
					reqC.setDisable(false);
					
    	        } else {
    	        	buggage.setDisable(true);
					reqA.setDisable(true);
					reqB.setDisable(true);
					reqC.setDisable(true);
    	        }
			}
    	});
    	
    	
	}
    
    
    
    
    public void addTicketToOrder() {
    	
    	FlightTicket toAdd = null;
    	int custID = 0;
    	Customer cust = null;
    	MealType meal = null;
    	SeatClass seatClass = null;
    	Flight flight = null;
    	double price;
    	int weight = 0;
    	
    	if(comboMeal.getValue()!=null)
    		meal = comboMeal.getValue();
    	
    	if(comboClass.getValue()!=null)
    		seatClass = comboClass.getValue();
    	
    	if(flightsList.getSelectionModel().getSelectedItem()!=null)
    		flight = flightsList.getSelectionModel().getSelectedItem();
    	
    	if(customerId.getText()!=null) {
    		if(!importControl.isCustomerExsist(new Customer(Integer.parseInt(customerId.getText())))){
    	    	   Alert a = new Alert(AlertType.ERROR);
    	 		   a.setContentText("Customer does not exsist, please try again");
    	 		   a.show();
    	 		   customerId.setText("");
    	    } else {
    	    	custID = Integer.parseInt(customerId.getText());
    	    	for(Customer c: importControl.getCustomers())
    	    		if(c.getPassportNum() == custID)
							cust = c;

    	    	
    	    	if(premuim.isSelected()) {
    	    		if(buggage.getText()!=null)
    	    			weight = Integer.parseInt(buggage.getText());
    	    	
    	    		String r1 = ""; String r2 = ""; String r3 = "";
    	    		if(reqA.getText()!=null)
    	    			r1 = reqA.getText();
    	    		if(reqB.getText()!=null)
    	    			r2 = reqB.getText();
    	    		if(reqC.getText()!=null)
    	    			r3 = reqC.getText();
    	    		
    	    		price = OrderControl.getInstance().calcPrice(flight, meal, seatClass, weight);
    	    		
    	    		toAdd = new PremiumTicket(cust, meal, seatClass, price, flight, weight, r1, r2, r3);
    	    		System.out.println("ID!!!!! " + cust.getPassportNum());
    	    		
    	    		if(toAdd!=null)
    	    			tickets.add(toAdd);
    	    		
    	    		ticketsInOrder.setItems(FXCollections.observableArrayList(tickets));
    	    		
    	    		ticketsInOrder.refresh();
    	    		
    	    		
    	    	} else { // In case that is not premuim ticket
    	    	   
    	    		price = OrderControl.getInstance().calcPrice(flight, meal, seatClass, weight);
    	    		System.out.println("cust " + cust + " meal: " +meal + " class: " + seatClass + " price:" +price );
    	    		System.out.println("ID!!!!! " + cust.getPassportNum());
    	    		toAdd = new FlightTicket(cust, meal, seatClass, price, flight, cust.getPassportNum());
    	    		
    	    		System.out.println("ID!!!!! " + toAdd.getCustomerPassportNumber());
    	    		if(toAdd!=null)
    	    			tickets.add(toAdd);
    	    		    		
    	    		ticketsInOrder.setItems(FXCollections.observableArrayList(tickets));
    	    		
    	    		ticketsInOrder.refresh();
    	    	}
    	    	
    	    	
    	    	Alert a = new Alert(AlertType.CONFIRMATION);
    			a.setContentText("Ticket was added successfully, the price is: " + price );
    			a.show();
    			
    			//clear all fields after adding ticket to cart
    			flightsList.getSelectionModel().clearSelection();
    			itemsInFlightList.setItems(FXCollections.observableArrayList());
    			comboMeal.getSelectionModel().clearSelection();
    			comboClass.getSelectionModel().clearSelection();
    			reqA.setText(""); reqB.setText(""); reqC.setText("");
    			customerId.setText("");
    			buggage.setText("");
    			

    	    }
    		
    	}
	
    }
    
    @FXML
    void addOrder(ActionEvent event) {
    	if(!tickets.isEmpty() && comboPayment.getValue()!=null )
			try {
				OrderControl.getInstance().makeOrder(tickets, comboPayment.getValue());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
    	Alert a = new Alert(AlertType.CONFIRMATION);
		a.setContentText("Order was added successfully!" );
		a.show();
		
		//clear all fields after adding ticket to cart
		flightsList.getSelectionModel().clearSelection();
		itemsInFlightList.setItems(FXCollections.observableArrayList());
		comboMeal.getSelectionModel().clearSelection();
		comboClass.getSelectionModel().clearSelection();
		reqA.setText(""); reqB.setText(""); reqC.setText("");
		customerId.setText("");
		buggage.setText("");
		ticketsInOrder.setItems(FXCollections.observableArrayList());
		tickets.removeAll(tickets);
		
    }
    

    @FXML
    void addRelative(ActionEvent event) throws NumberFormatException, ClassNotFoundException, SQLException {
    	
    	if (!customerId.getText().equals("")) {
    		int passport = Integer.parseInt(relativePassport.getText());
    		Customer c = OrderControl.getInstance().getCustByID(passport);
    		
    		if (c == null) {
        		Alert a = new Alert(AlertType.ERROR);
    	 		   a.setContentText("Customer does not exsist, please try again");
    	 		   a.show();
        	} else {
        		
    				if (OrderControl.getInstance().addRelative(Integer.parseInt(customerId.getText()),passport)) {
    					Alert a = new Alert(AlertType.CONFIRMATION);
    					a.setContentText("Relative added sucessfully" );
    					a.show();
        		}
    			relativePassport.setText("");
    			
        	}
    	} else {
    		Alert a = new Alert(AlertType.ERROR);
 		   a.setContentText("Customer does not exsist, please try again");
 		   a.show();
    	}
    }
}

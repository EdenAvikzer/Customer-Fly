package boundary;

import java.io.IOException;
import java.sql.SQLException;

import control.EnterteimentControl;
import entity.Flight;
import entity.Item;
import entity.ItemBySupplier;
import entity.ItemInFlight;
import entity.Supplier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import util.ItemType;

public class EnterteimentManagment {

    @FXML
    private AnchorPane mainScreen;
    
    @FXML
    private AnchorPane toHide;

    @FXML
    private ListView<Flight> flightsList;

    @FXML
    private Button selectFlightBtn;

    @FXML
    private ListView<ItemInFlight> itemsInFlightList;
    
    @FXML
    private ListView<Supplier> suppliersList;

    @FXML
    private ListView<ItemBySupplier> itemsList;
    
    @FXML
    private TextArea feedbackTxt;
    
    @FXML
    private AnchorPane popupAddSupplier;
    
    @FXML
    private TextField suppName;

    @FXML
    private TextField supEmail;

    @FXML
    private TextField supPhone;
    
    @FXML
    private AnchorPane popupAddItem;

    @FXML
    private TextField itemName;

    @FXML
    private TextField itemDescription;

    @FXML
    private ChoiceBox<util.ItemType> itemType;
    
    
    @FXML
    private AnchorPane popupAddItemToSup;

    @FXML
    private ChoiceBox<Supplier> supplierBox;

    @FXML
    private ChoiceBox<Item> itemsBox;
    
    @FXML
   	public void initialize()  {
    	toHide.setVisible(false);
    	flightsList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getAllFlights()));
    	
    	suppliersList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getSuppliers()));
    	suppliersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Supplier>() {


			@Override
			public void changed(ObservableValue<? extends Supplier> observable, Supplier oldValue, Supplier newValue) {
				showDetails();
			}
			
		});
    	
    	popupAddSupplier.setVisible(false);
    	popupAddItem.setVisible(false);
    	popupAddItemToSup.setVisible(false);
    	
    }

    protected void showDetails() {
    	Supplier sup = suppliersList.getSelectionModel().getSelectedItem();
    	itemsList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getItemBySupps(sup.getSupId())));
    	
	}

    @FXML
    void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/ManagerMenu.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
    

    @FXML
    void selectFlight(ActionEvent event) {
    	Flight flightSelected = (Flight) flightsList.getSelectionModel().getSelectedItem();
    	itemsInFlightList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getAllItemsInFlights(flightSelected.getFlightNum())));
    	toHide.setVisible(true);
    	
    }
    
    
    @FXML
    void feedback(ActionEvent event) {
    	
    	if (feedbackTxt.getText().equals("") || itemsInFlightList.getSelectionModel().getSelectedItem() == null) {
    		Alert alert = new Alert(AlertType.ERROR,"feedback update Faild, please check your feedback");
			alert.setHeaderText("feedback Error");
			alert.setTitle("feedback Error");
			alert.showAndWait();
    	} else {
	    	ItemInFlight itemInFlight = itemsInFlightList.getSelectionModel().getSelectedItem();
	    	EnterteimentControl.getInstance().updateFeedback(feedbackTxt.getText(), itemInFlight);
	    	Flight flightSelected = (Flight) flightsList.getSelectionModel().getSelectedItem();
	    	itemsInFlightList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getAllItemsInFlights(flightSelected.getFlightNum())));
	    	itemsInFlightList.refresh();

	    	Alert alert = new Alert(AlertType.INFORMATION,"feedback updated Sucssefully");
			alert.setHeaderText("Sucsses");
			alert.setTitle("feedback updated");
			alert.showAndWait();
			
			feedbackTxt.setText("");
    	}
    }
    
    //itemsList
    @FXML
    void addItemToFlight(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	if  (flightsList.getSelectionModel().getSelectedItem() != null && itemsList.getSelectionModel().getSelectedItem() != null ) {
	    	Flight flightSelected = (Flight) flightsList.getSelectionModel().getSelectedItem();
	    	ItemBySupplier itemBySupp = itemsList.getSelectionModel().getSelectedItem();
	    	EnterteimentControl.getInstance().insertItemToFlight(flightSelected, itemBySupp.getItemId(), itemBySupp.getSupID());
 	
	    	Alert alert = new Alert(AlertType.INFORMATION,"adding item Sucsseed");
			alert.setHeaderText("Sucsses");
			alert.setTitle("");
			alert.showAndWait();
			
			itemsInFlightList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getAllItemsInFlights(flightSelected.getFlightNum())));
	    	itemsInFlightList.refresh();
	    	
    	} else {
    		Alert alert = new Alert(AlertType.ERROR,"Add item Faild");
			alert.setHeaderText("Error");
			alert.setTitle("Error");
			alert.showAndWait();
    	}
    }
    

    

    @FXML
    void addNewItem(ActionEvent event) {
    	popupAddItem.setVisible(true);
    	itemType.setItems( FXCollections.observableArrayList(util.ItemType.values()));
    }
    
    @FXML
    void addNewItemm(ActionEvent event) throws ClassNotFoundException, SQLException {
    	if(itemName.getText().equals("") || itemDescription.getText().equals("") ||  itemType.getValue() == null) {
    		
    		Alert alert = new Alert(AlertType.ERROR,"Add item Faild");
			alert.setHeaderText("Error");
			alert.setTitle("Error");
			alert.showAndWait();
			
    	} else {
    		Item i = new Item(itemName.getText(), itemDescription.getText(), itemType.getValue() );
    		if(EnterteimentControl.getInstance().insertItem(i)) {
    			Alert alert = new Alert(AlertType.INFORMATION,"adding item Sucsseed");
    			alert.setHeaderText("Sucsses");
    			alert.setTitle("");
    			alert.showAndWait();
    			
    			itemName.setText("");
    			itemDescription.setText("");
    			itemType.setValue(null);

    			itemsList.refresh();
    			
    		}
    	}
    }
    
    @FXML
    void closePopup2(ActionEvent event) {
    	popupAddItem.setVisible(false);
    }

    @FXML
    void addSupplier(ActionEvent event) {
    	popupAddSupplier.setVisible(true);
    }

    @FXML
    void addSupplierr(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	
    	if(suppName.getText().equals("") || supEmail.getText().equals("") ||  supPhone.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.ERROR,"Add supplier Faild");
			alert.setHeaderText("Error");
			alert.setTitle("Error");
			alert.showAndWait();
			
    	} else {
    		Supplier s = new Supplier(suppName.getText(),supEmail.getText(), supPhone.getText() );
    		if(EnterteimentControl.getInstance().insertSupplier(s)) {
    			Alert alert = new Alert(AlertType.INFORMATION,"adding supplier Sucsseed");
    			alert.setHeaderText("Sucsses");
    			alert.setTitle("");
    			alert.showAndWait();
    			
    			suppName.setText("");
    			supEmail.setText("");
    			supPhone.setText("");
    			
    			suppliersList.setItems(FXCollections.observableArrayList(EnterteimentControl.getInstance().getSuppliers()));
    			suppliersList.refresh();
    			
    		}
    	}	
    }
    
    @FXML
    void closePopup(ActionEvent event) {
    	popupAddSupplier.setVisible(false);
    }
    
    @FXML
    void addItemToSupplier(ActionEvent event) {
    	popupAddItemToSup.setVisible(true);
    	supplierBox.setItems( FXCollections.observableArrayList(EnterteimentControl.getInstance().getSuppliers()));
    	itemsBox.setItems( FXCollections.observableArrayList(EnterteimentControl.getInstance().getItems()));
    }
    
    @FXML
    void addNewItemToSupplier(ActionEvent event) throws ClassNotFoundException, SQLException {
    	if(supplierBox.getValue() == null || itemsBox.getValue() == null) {
    		
    		Alert alert = new Alert(AlertType.ERROR,"Add item to supplier Faild");
			alert.setHeaderText("Error");
			alert.setTitle("Error");
			alert.showAndWait();
			
    	} else {
    		ItemBySupplier itemBySup = new ItemBySupplier( supplierBox.getValue().getSupId()  ,itemsBox.getValue().getItemId() );
    		
    		
    		if(EnterteimentControl.getInstance().insertItemBySupplier(itemBySup)) {
    			Alert alert = new Alert(AlertType.INFORMATION,"adding item to supplier Sucsseed");
    			alert.setHeaderText("Sucsses");
    			alert.setTitle("");
    			alert.showAndWait();
    			
    			supplierBox.setValue(null);
    			itemsBox.setValue(null);

    			suppliersList.refresh();
    			
    		}
    	}
    }
    
    @FXML
    void closePopup3(ActionEvent event) {
    	popupAddItemToSup.setVisible(false);
    }
    
}
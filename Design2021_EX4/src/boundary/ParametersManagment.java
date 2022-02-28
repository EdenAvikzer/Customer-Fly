package boundary;

import java.io.IOException;
import java.sql.SQLException;

import control.OrderControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

public class ParametersManagment {

    @FXML
    private TextField d;

    @FXML
    private TextField m;

    @FXML
    private TextField t;

    @FXML
    private TextField pricePerKG;
    @FXML
    private AnchorPane mainScreen;

    
    @FXML
    void updateParameters(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	
    	if ( d.getText().equals("") || m.getText().equals("") || t.getText().equals("") || pricePerKG.getText().equals("") ) {
    		Alert a = new Alert(AlertType.ERROR);
  		   a.setContentText("Error, please try again");
  		   a.show();
    	} else {
    		int i1 = Integer.parseInt(d.getText());
    		int i2 = Integer.parseInt(m.getText());
    		int i3 = Integer.parseInt(t.getText());
    		int i4 = Integer.parseInt(pricePerKG.getText());
    		
    		if (OrderControl.getInstance().updateParameters(i1, i2, i3, i4)) {
    			Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Parameters updates sucessfully" );
				a.show();
				
				d.setText("");
				m.setText("");
				t.setText("");
				pricePerKG.setText("");
				
    		} else {
    				Alert a = new Alert(AlertType.ERROR);
    	  		   a.setContentText("Error, please try again");
    	  		   a.show();
    		}
    	}	
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/ManagerMenu.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}

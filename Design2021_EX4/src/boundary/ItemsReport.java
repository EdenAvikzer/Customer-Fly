package boundary;

import java.io.IOException;
import java.sql.Date;

import javax.swing.JFrame;

import control.ControlItemsReport;
import control.ControlReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import util.Consts;

public class ItemsReport {

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private TextField p1;

    @FXML
    private TextField p2;
    
    @FXML
    private Button produceReportBtn;
    
    
    

    @FXML
    void checkParametrs(ActionEvent event) {
    	String aiport1 = p1.getText();
    	String aiport2 = p2.getText();
    	
    	if (aiport1 == null || aiport2 == null || ControlItemsReport.getInstance().isAirportExists(aiport1) == null || ControlItemsReport.getInstance().isAirportExists(aiport2)==null ) {
    		
    		Alert alert = new Alert(AlertType.ERROR,"Invalid parameters entered, please try again");
			alert.setHeaderText("Error");
			alert.setTitle("Error");
			alert.showAndWait();
    	} else {
    		produceReportBtn.setDisable(false);
			Alert alert = new Alert(AlertType.CONFIRMATION,"The parameters are valid");
    		alert.setHeaderText("Sucsses");
			alert.setTitle(" Sucsses");
			alert.showAndWait();
    	}
    	
    	
    }

    @FXML
    void produceReport(ActionEvent event) {
    	
    	String aiport1 = p1.getText();
    	String aiport2 = p2.getText();
    	
		JFrame reportFrame = ControlItemsReport.getInstance().produceReport(aiport1, aiport2);
		reportFrame.setVisible(true);
		produceReportBtn.setDisable(true);
		
		p1.setText("");
		p2.setText("");
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/ManagerMenu.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}
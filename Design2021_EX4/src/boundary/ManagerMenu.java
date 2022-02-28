package boundary;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ManagerMenu {
	

    @FXML
    private AnchorPane mainScreen;

	@FXML
    void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/Login.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

    @FXML
    void moveToItemsReport(ActionEvent event)throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/ItemsReport.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
    
    @FXML
    void moveToEntretainmentManagement(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/EnterteimentManagment.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
    
    @FXML
    void moveToParametersManagment(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/ParametersManagment.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}

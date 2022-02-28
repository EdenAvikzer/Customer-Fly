package boundary;

import java.io.IOException;
import java.util.ArrayList;

import control.LoginControl;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Login {

	@FXML
	private javafx.scene.layout.AnchorPane mainScreen;
	
    @FXML
    private TextField passport;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;
    
   

  //login method
  	public void login(ActionEvent event) throws IOException
  	{
  		//input check 
  		if (passport.getText().isEmpty() || password.getText().isEmpty()) {
  			//error messege
  			Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
			alert.setHeaderText("Login Error");
			alert.setTitle("Login Error");
			alert.showAndWait();
  		} 
  		else {
  			if (isCanLogin())
  				moveToNextPage();
  		}
	
  	}
  	
  	public void moveToNextPage() throws IOException
	{
  		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/OrderTickets.fxml"));
		BorderPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
  	
  //this method return true if can login, else false
  	public boolean isCanLogin() throws IOException
  	{
  		
  	//login Customer relations manager
  		if (passport.getText().equals("manager") && password.getText().equals("manager")) {
  			LoginControl.getInstance().setAdmin("Hello customer relations manager"); //save the login member
  			FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/ImportUpdates.fxml"));
  			javafx.scene.layout.AnchorPane pane = loader.load();
  			mainScreen.getChildren().removeAll(mainScreen.getChildren());
  			mainScreen.getChildren().add(pane);
			return false;
  		}
  		
  	//login Ground attendant
  		if (passport.getText().equals("attendant") && password.getText().equals("attendant")) {
  				LoginControl.getInstance().setAdmin("Hello attendant"); //save the login member
  				FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/Shibutz.fxml"));
  	  			javafx.scene.layout.AnchorPane pane = loader.load();
  	  			mainScreen.getChildren().removeAll(mainScreen.getChildren());
  	  			mainScreen.getChildren().add(pane);
				return false;
  		}
  		
  		
  	//login Flight Manager
  		if (passport.getText().equals("flightManager") && password.getText().equals("flightManager")) {
  				LoginControl.getInstance().setAdmin("Hello Flight Manager"); //save the login member
  				FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/ManagerMenu.fxml"));
  	  			javafx.scene.layout.AnchorPane pane = loader.load();
  	  			mainScreen.getChildren().removeAll(mainScreen.getChildren());
  	  			mainScreen.getChildren().add(pane);
				return false;
  		}
  		
  		if (!passport.getText().matches("^[0-9]*$") || !password.getText().matches("^[0-9]*$")) {
  			//error messege
  			Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
			alert.setHeaderText("Login Error");
			alert.setTitle("Login Error");
			alert.showAndWait();
  		}
  		
  		Customer c = new Customer(Integer.parseInt(passport.getText()), password.getText());
  		ArrayList<Customer> allCustumers = LoginControl.getInstance().getAllCustumers();
  		for(Customer cus: allCustumers)
  		{
  			if(cus.equals(c)) //if userName and password exist
  			{
  				LoginControl.getInstance().setLoginCustumer(cus); //save the login member
  				return true;
  			}
  		}
  		
		Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
		alert.setHeaderText("Login Error");
		alert.setTitle("Login Error");
		alert.showAndWait();
  		return false;
  	}

    @FXML
    void register(ActionEvent event) throws IOException {
    	moveToRegister();
    }
    
    public void moveToRegister() throws IOException
	{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/Register.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}

}

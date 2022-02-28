package boundary;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import control.LoginControl;
import entity.Customer;
import exceptions.EmptyFieldException;
import exceptions.EqualMailException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Register {
	
    @FXML
    private AnchorPane mainScreen;

    @FXML
    private TextField passport;

    @FXML
    private TextField citizenship;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField email;

    @FXML
    private DatePicker date;

    @FXML
    private PasswordField password;
    
    public void initialize () {
    	password.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	          public void handle(KeyEvent arg0) {

					//Input Check
	        	  if(password.getText().matches("^[0-9]*$")){
	        		  password.setStyle("");
	        	  } else {
	        		  password.setStyle("-fx-text-fill: red; -fx-border-color: red");
	        	  }
	        	  		
	          }
		});
    }
    

    @FXML
	public void createAccount(ActionEvent event) throws IOException
	{
		try 
		{
			if(!(fname.getText()!=null && lname.getText()!=null && passport.getText()!=null&& citizenship.getText()!=null && email.getText()!=null&password.getText()!=null))
			{
				throw new EmptyFieldException();
			}
			//date of birth can't be in the future
			if(date.getValue() == null || date.getValue().isAfter(LocalDate.now())) {
				throw new EmptyFieldException();
			}
			if(fname.getLength()==0 || lname.getLength()==0 || passport.getLength()==0 ||email.getLength()==0 ||password.getLength()==0 || citizenship.getLength()==0)
			{
				throw new EmptyFieldException();
			}
			
			//if the fields contain letters
			if(!passport.getText().matches("^[0-9]*$") || !password.getText().matches("^[0-9]*$"))  {
		  			//error messege
		  			Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
					alert.setHeaderText("Login Error");
					alert.setTitle("Login Error");
					alert.showAndWait();
		  	} 
			
			
			Customer c = new Customer(Integer.parseInt(passport.getText()), fname.getText(), lname.getText(), email.getText(), citizenship.getText(),Date.valueOf(date.getValue()), password.getText());
			
			if(LoginControl.getInstance().checkMail(c))
			{
				throw new EqualMailException();
			}
			
			//adding the customer to DB//
			LoginControl.getInstance().addNewCutsumerToDB(c);
			Alert alert = new Alert(AlertType.INFORMATION,"You are Succseed to Register, Please LogIn");
			alert.setHeaderText("Register Sucssed");
			alert.setTitle("Register Sucssed");
			alert.showAndWait();
			moveToNextPage(); //moving back to login
			
			
		}
		catch (EmptyFieldException e) 
		{
			String s = "One or More Fields are empty";
			Alert alert = new Alert(AlertType.ERROR,s);
			alert.setHeaderText("Register Error");
			alert.setTitle("Register Error");
			alert.showAndWait();
		} catch (EqualMailException e) {
			Alert alert = new Alert(AlertType.ERROR,e.getMessage());
			alert.setHeaderText("Register Error");
			alert.setTitle("Register Error");
			alert.showAndWait();
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR,"We have problem now, please try again later");
			alert.setHeaderText("Register Error");
			alert.setTitle("Register Error");
			alert.showAndWait();
		}

	}
    
	public void moveToNextPage() throws IOException
	{
  		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/Login.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
    @FXML
    void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/Login.fxml"));
		javafx.scene.layout.AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
    

}
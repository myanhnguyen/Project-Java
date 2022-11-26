package app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController implements Initializable {

    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
 
    @FXML
    private Label loginlabel;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void loginOnAction(ActionEvent event) {
        if (!username.getText().isBlank() && !password.getText().isBlank()){
            Database connectNow = new Database();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT count(*) FROM account WHERE username = '" + username.getText() 
                    + "' AND password = '" + password.getText() + "'";

            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while(queryResult.next()){
                    if(queryResult.getInt(1) == 1) {
                        try {
                            ChangeScene.Change(event,"App.fxml");
                        }
                        catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    else {
                        loginlabel.setText("Hãy nhập lại");

                    }
                }
            }
            catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
     
   public void signupOnAction(ActionEvent event) {
        try {
            ChangeScene.Change(event,"Signup.fxml");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
   }
}


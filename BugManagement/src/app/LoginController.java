package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginlabel;  
      
    @FXML
    public void loginOnAction(ActionEvent event) {
        if (!username.getText().isBlank() && !password.getText().isBlank()){
            Database connectNow = new Database();
            Connection connectDB = connectNow.getConnection();

            try {
                Statement statement = connectDB.createStatement();
                ResultSet truy_vấn = statement.executeQuery("SELECT count(*) FROM account WHERE username = '" + username.getText() 
                                                                + "' AND password = '" + password.getText() + "'");

                while(truy_vấn.next()){
                    if(truy_vấn.getInt(1) == 1) {
                        try {
                            ChangeScene.Change(event,"App.fxml");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        loginlabel.setText("Hãy nhập lại");
                    }
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }              
                              

            
     
    @FXML
    public void signupOnAction(ActionEvent event) {
        try {
            ChangeScene.Change(event,"Signup.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

package app;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField username_text;
    @FXML
    private PasswordField password_text;
    @FXML
    private Label login_label;  
    
    @FXML
    public void loginOnAction(ActionEvent event) {
        if (!username_text.getText().isBlank() && !password_text.getText().isBlank()){
            Connection connectDB = (new Database()).getConnection();
            Parent root;
           
            try {
                Statement statement = connectDB.createStatement();
                ResultSet truy_vấn = statement.executeQuery("SELECT count(*) FROM account WHERE username = '" + username_text.getText() 
                                                                + "' AND password = '" + password_text.getText() + "'");
                while(truy_vấn.next()){               
                    if(truy_vấn.getInt(1) == 1) {
                        try {
                            String username =  username_text.getText();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
                            root = loader.load();
                            
                            AppController appcontroller = loader.getController();
                            appcontroller.set(username);
                                    
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setTitle("App");
                            stage.setScene(new Scene(root));
                            stage.centerOnScreen();
                            stage.show();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        login_label.setText("Hãy nhập lại");
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
            Main.ChangeScene(event,"Signup.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
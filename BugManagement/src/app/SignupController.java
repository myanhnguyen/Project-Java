package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {
    @FXML private TextField name, email, username;  
    @FXML private PasswordField pass, cfpass;    
    @FXML private Label emaillabel, usernamelabel;
    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
  
    @FXML
    public void singupOnAction(ActionEvent event) throws SQLException {
        Connection conDB = ( new Database()).getConnection();    
   
        PreparedStatement thêm_vào_database = conDB.prepareStatement("INSERT INTO account VALUES (?,?,?,?)");
        thêm_vào_database.setString(1, name.getText());
        thêm_vào_database.setString(2, email.getText());
        thêm_vào_database.setString(3, username.getText());
        thêm_vào_database.setString(4, pass.getText());
    
        //username bị trùng
        String trùng_username = "";
        try {
            ResultSet r1 = conDB.createStatement().executeQuery("SELECT username FROM account WHERE username = '" + username.getText() + "'");
            if(r1.next()) {
                trùng_username =  r1.getString("username");
            }
        } catch (SQLException e) {e.printStackTrace();}

        //email bị trùng
        String trùng_email = "";
        try {
            ResultSet r1 = conDB.createStatement().executeQuery("SELECT email FROM account WHERE email = '" + email.getText() + "'");         
            if(r1.next()) {
                trùng_email = r1.getString("email");
            }
        } catch (SQLException e) {e.printStackTrace();}
        
        //check 
        if (Pattern.matches("^(\\S*)(\\s{1}\\S*){1,}$", name.getText()) == false) {
            name.pseudoClassStateChanged(errorClass, true);
        } else name.pseudoClassStateChanged(errorClass, false);            
        
        if (!email.getText().equals("")) {
            if (!email.getText().equals(trùng_email)) {
                emaillabel.setText("");            
                if (Pattern.matches("^[a-zA-Z][\\w_]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", email.getText()) == false) {
                    email.pseudoClassStateChanged(errorClass, true);                   
                } else email.pseudoClassStateChanged(errorClass, false); 
            } else {
                emaillabel.setText("email bị trùng");
                email.pseudoClassStateChanged(errorClass, true);
            }   
        } else email.pseudoClassStateChanged(errorClass, true);
        
        if (!username.getText().equals("")) {
            if (username.getText().equals(trùng_username)) {
                usernamelabel.setText("username bị trùng");
                username.pseudoClassStateChanged(errorClass, true); 
            } else {
                usernamelabel.setText("");
                username.pseudoClassStateChanged(errorClass, false); 
            }
        } else username.pseudoClassStateChanged(errorClass, true); 
   
        if (!pass.getText().equals("")) {
            if (Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^_+=<>%\"]).{8,}$", pass.getText()) == false) {
                pass.pseudoClassStateChanged(errorClass, true);  
            } else pass.pseudoClassStateChanged(errorClass, false); 
        } else pass.pseudoClassStateChanged(errorClass, true); 
        
        if (!cfpass.getText().equals("")) {
            if (!cfpass.getText().equals(pass.getText())) {
                cfpass.pseudoClassStateChanged(errorClass, true);
            } else cfpass.pseudoClassStateChanged(errorClass, false);
        } else cfpass.pseudoClassStateChanged(errorClass, true);
        
        if (Pattern.matches("^(\\S*)(\\s{1}\\S*){1,}$", name.getText()) == true) {
            if (!email.getText().equals(trùng_email)) {
                if (Pattern.matches("^[a-zA-Z][\\w_]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", email.getText()) == true) {  
                    if (!username.getText().equals("") && !username.getText().equals(trùng_username)) {     
                        if (Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^_+=<>%\"]).{8,}$", pass.getText()) == true) {
                            if (!cfpass.getText().equals("") && cfpass.getText().equals(pass.getText())) {                              
                                try {
                                    thêm_vào_database.executeUpdate(); 
                                    Main.ChangeScene(event,"Login.fxml");
                                } catch (SQLException e) {e.printStackTrace();}                                      
        }   }   }   }   }   }                                                                                                                                                               
    }
    
    @FXML
    public void loginOnAction(ActionEvent event) {Main.ChangeScene(event,"Login.fxml");}
}
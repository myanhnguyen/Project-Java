package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {
    @FXML private TextField name, email, username;  
    @FXML private PasswordField pass, cfpass;    
    @FXML private Label emaillabel, usernamelabel, cfpasslabel, passlabel, namelabel;
  
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
        if (!name.getText().equals("")) {
            namelabel.setText("");
        } else namelabel.setText("nhập");            
        
        if (!email.getText().equals("")) {
            if (email.getText().equals(trùng_email)) {
                emaillabel.setText("email bị trùng");}   
            if (Pattern.matches("^[a-zA-Z][\\w_]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", email.getText()) == false) {
                emaillabel.setText("email không hợp lệ");
            } else emaillabel.setText("");  
        } else emaillabel.setText("nhập");
        
        if (!username.getText().equals("")) {
            if (username.getText().equals(trùng_username)) {
                usernamelabel.setText("username bị trùng");
            } else usernamelabel.setText("");
        } else usernamelabel.setText("nhập");
   
        if (!pass.getText().equals("")) {
            if (Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^_+=<>%\"]).{8,}$", pass.getText()) == false) {
                passlabel.setText("mật khẩu không hợp lệ");  
            } else passlabel.setText("");
        } else passlabel.setText("nhập");
        
        if (!cfpass.getText().equals("")) {
            if (!cfpass.getText().equals(pass.getText())) {
                cfpasslabel.setText("mật khẩu không khớp");
            } else cfpasslabel.setText("");
        } else cfpasslabel.setText("nhập");
        
        if (!name.getText().equals("")) {
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

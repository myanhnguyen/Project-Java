package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignupController {
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private PasswordField pass;    
    @FXML
    private PasswordField cfpass;
    @FXML
    private Label emaillabel;
    @FXML
    private Label usernamelabel;   
    @FXML
    private Label cfpasslabel;
    @FXML
    private ToggleGroup chucvu;
    @FXML
    private Label passlabel;
    @FXML
    private Label namelabel;  

    @FXML
    public void singupOnAction() throws SQLException {
        //Connect to database
        Database connectionNow = new Database();
        Connection connectDB = connectionNow.getConnection();

        //Create SQL code
        String choose = ((RadioButton) chucvu.getSelectedToggle()).getText();
        
        PreparedStatement thêm_vào_database = connectDB.prepareStatement("INSERT INTO account VALUES (?,?,?,?,?)");
        thêm_vào_database.setString(1, name.getText());
        thêm_vào_database.setString(2, email.getText());
        thêm_vào_database.setString(3, username.getText());
        thêm_vào_database.setString(4, pass.getText());
        thêm_vào_database.setString(5, choose);
    
        //username bị trùng
        String trùng_username = "";
        try {
            Statement st = connectDB.createStatement();
            ResultSet r1 = st.executeQuery("SELECT username FROM account WHERE username = '" + username.getText() + "'");

            if(r1.next()) {
                trùng_username =  r1.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //email bị trùng
        String trùng_email = "";
        try {
            Statement st = connectDB.createStatement();
            ResultSet r1 = st.executeQuery("SELECT email FROM account WHERE email = '" + email.getText() + "'");

            if(r1.next()) {
                trùng_email =  r1.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
        if (!name.getText().equals("")) {
            namelabel.setText("");
            if (!email.getText().equals("")) {
                emaillabel.setText("");
                if (!email.getText().equals(trùng_email)) {  
                    if (!username.getText().equals("")) {
                        usernamelabel.setText("");
                        if (!username.getText().equals(trùng_username)) { 
                            if (!pass.getText().equals("")) {
                                passlabel.setText("");
                                if (pass.getText().length() >= 8) {
                                    if (!cfpass.getText().equals("")) {
                                        cfpasslabel.setText("");
                                        if (cfpass.getText().equals(pass.getText())) {
                                            try {
                                                thêm_vào_database.executeUpdate();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }  
                                        } else {
                                            cfpasslabel.setText("mật khẩu không khớp");
                                        }
                                    } else {
                                        cfpasslabel.setText("nhập");
                                    }
                                } else {
                                    passlabel.setText("mật khẩu quá ngắn");    
                                }
                            } else {
                                passlabel.setText("nhập");
                            }
                        } else {
                            usernamelabel.setText("username bị trùng");      
                        }
                    } else {
                        usernamelabel.setText("nhập");
                    }
                } else {
                    emaillabel.setText("email bị trùng");
                }   
            } else {
                emaillabel.setText("nhập");
            }
        } else {
            namelabel.setText("nhập");
        }
    }
    
    @FXML
    public void loginOnAction(ActionEvent event) {
        ChangeScene.Change(event,"Login.fxml");   
    }
}

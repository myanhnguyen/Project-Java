package app;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignupController implements Initializable {
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
    private Button signup;
    @FXML
    private Label namelabel;
    @FXML
    private Label emaillabel;
    @FXML
    private Label usernamelabel;
    
    @FXML
    private Label passlabel;
    @FXML
    private Label cfpasslabel;
    
    @FXML
    private ToggleGroup chucvu;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //Action when nextButton is pressed.
    @FXML
    public void singupOnAction() throws SQLException {
        if (pass.getText().equals(cfpass.getText())) {
            cfpasslabel.setText("");
            signup();
        } else {
            cfpasslabel.setText("Password does not match.");
        }
    }

    //Add data in user_account table
    public void signup() throws SQLException {
        //Connect to database
        Database connectionNow = new Database();
        Connection connectDB = connectionNow.getConnection();

        //Create SQL code
        String usernamefield = username.getText();
        String namefield = name.getText();
        String password = pass.getText();
        String emailfield = email.getText();
        RadioButton selectedRadioButton = (RadioButton) chucvu.getSelectedToggle();
        String choose = selectedRadioButton.getText();
        
        PreparedStatement insertToRegister = connectDB.prepareStatement("INSERT INTO account VALUES (?,?,?,?,?)");
        insertToRegister.setString(1, namefield);
        insertToRegister.setString(2, emailfield);
        insertToRegister.setString(3, usernamefield);
        insertToRegister.setString(4, password);
        insertToRegister.setString(5, choose);
    
        //Verify if username already exist
        String usernameDuplicate = "";
        try {
            Statement st = connectDB.createStatement();
            ResultSet r1 = st.executeQuery("SELECT username FROM account WHERE username = '" + username + "'");

            if(r1.next()) {
                usernameDuplicate =  r1.getString("username");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        //Verify if email already exist
        String emailDuplicate = "";
        try {
            Statement st = connectDB.createStatement();
            ResultSet r1 = st.executeQuery("SELECT email FROM account WHERE email = '" + email + "'");

            if(r1.next()) {
                emailDuplicate =  r1.getString("email");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (!namefield.equals("")) {
            namelabel.setText("");
            if (!emailfield.equals(emailDuplicate)) {
                emaillabel.setText("trùng");
                if (!password.equals("")) {
                    passlabel.setText("");
                    if (!usernamefield.equals(usernameDuplicate)) {
                        usernamelabel.setText("trung");
                        if(!usernamefield.equals("")) {
                            usernamelabel.setText("");
                            try {
                                insertToRegister.executeUpdate();
                                signup.setDisable(true);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }                                  
                        } 
                        else {
                            usernamelabel.setText("Please insert your username.");    
                        }
                    } 
                    else {
                        usernamelabel.setText("Username already exist.");
                    }
                } 
                else {
                    passlabel.setText("Please insert a password.");
                }           
            }
            else {
                emaillabel.setText("E-mail already exist.");   
            }
        }
        else {
            namelabel.setText("Please insert your name.");
        }   
    } 
    
    @FXML
    public void loginOnAction(ActionEvent event) {
        ChangeScene.Change(event,"Login.fxml");   
    }
}

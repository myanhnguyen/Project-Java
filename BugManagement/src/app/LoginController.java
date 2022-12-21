package app;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginController implements Initializable{
    @FXML private TextField username_text;
    @FXML private PasswordField password_text;
    @FXML private AnchorPane anchor_pane;
    @FXML private Label login_label;
    
    private static final int STAR_COUNT = 6000;   
    private final Rectangle[] nodes = new Rectangle[STAR_COUNT];
    private final double[] angles = new double[STAR_COUNT];
    private final long[] start = new long[STAR_COUNT];   
    private final Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i=0; i<STAR_COUNT; i++) {
            nodes[i] = new Rectangle(1.2, 1.2, Color.WHITE);
            angles[i] = 2.0 * Math.PI * random.nextDouble();
            start[i] = random.nextInt(2000000000);
        }
        anchor_pane.getChildren().add(new Group(nodes));
    
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                final double width = 2 * anchor_pane.getWidth();
                final double height = 2 * anchor_pane.getHeight();
                final double radius = Math.sqrt(2) * Math.max(width, height);
                for (int i=0; i<STAR_COUNT; i++) {
                    final Node node = nodes[i];
                    final double angle = angles[i];
                    final long speed = ((now / 120) - start[i]) % 2000000000;  
                    final double d = speed *10 * radius / 2000000000.0;      
                    node.setTranslateX(Math.cos(angle) * 3*d );
                    node.setTranslateY(Math.sin(angle) * d );
                }
            }
        }.start();
    }
    
    @FXML
    public void loginOnAction(ActionEvent event) {
        if (!username_text.getText().isBlank() && !password_text.getText().isBlank()){
            try {
                Statement statement = (new Database()).getConnection().createStatement();
                ResultSet truy_vấn = statement.executeQuery("SELECT count(*) FROM account WHERE username = '" + username_text.getText() 
                                                                + "' AND password = '" + password_text.getText() + "'");
                while(truy_vấn.next()){               
                    if(truy_vấn.getInt(1) == 1) {
                        try {
                            String username =  username_text.getText();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
                            Parent root = loader.load();
                            
                            AppController appcontroller = loader.getController();
                            appcontroller.set(username);
                                    
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setTitle("App");
                            stage.setScene(new Scene(root));
                            stage.centerOnScreen();
                            stage.show();
                        } catch (IOException e1) {e1.printStackTrace();}
                    } else login_label.setText("Username hoặc mật khẩu không chỉnh xác. Vui lòng nhập lại");
                }
            } catch (SQLException e2) {e2.printStackTrace();}
        } else login_label.setText("Username hoặc mật khẩu không chỉnh xác. Vui lòng nhập lại");
    }                    
     
    @FXML
    public void signupOnAction(ActionEvent event) {
        try {Main.ChangeScene(event,"Signup.fxml");} 
        catch (Exception e) {e.printStackTrace();}
    }
}
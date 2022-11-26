package app;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeScene {
    public static void Change(ActionEvent e, String fxmlfile){
        Parent root = null;
        try{
            root = FXMLLoader.load(ChangeScene.class.getResource(fxmlfile));
        } catch(IOException e1) {
            e1.printStackTrace();
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("App");
        stage.setScene(new Scene(root));
        stage.show();
    }    
}
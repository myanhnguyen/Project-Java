package app;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void ChangeScene(ActionEvent e, String fxmlfile){
        Parent root = null;
        try{
            root = FXMLLoader.load(Main.class.getResource(fxmlfile));
        } catch(IOException e1) {e1.printStackTrace();}
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Bug Tracker");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }    
    
    @Override
    public void start(Stage primaryStage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setTitle("Bug Tracker");
            primaryStage.setScene(new Scene(root));
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch(IOException e) {e.printStackTrace();}
    }
    
    public static void main(String[] args) {
        launch(args);
    } 
}
package com.example.flappybirdfx;

import com.example.model.flappybirdfx.Game;
import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(new Game(stage, 700, 500)/*fxmlLoader.load()*/, 700, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();}
    public static void main(String[] args) {
        launch();
    }
}
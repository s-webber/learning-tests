package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExampleApplication extends Application {
   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      primaryStage.setTitle("Example JavaFX Application");
      Scene scene = new Scene(new ExamplePane(), 100, 100);
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class ExampleApplication extends Application {
	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() {
		context = SpringApplication.run(ExampleApplication.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Example JavaFX Application");
		Scene scene = new Scene(context.getBean(ExamplePane.class), 100, 100);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

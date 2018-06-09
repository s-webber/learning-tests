package com.example.demo;

import org.springframework.stereotype.Component;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Component
public class ExamplePane extends VBox {
	public ExamplePane() {
		Label label = new Label();
		TextField textField = new TextField();
		textField.setId("textFieldId");
		Button button = new Button("click me!");

		button.setOnAction(actionEvent -> label.setText("hello " + textField.getText()));

		getChildren().add(label);
		getChildren().add(textField);
		getChildren().add(button);
	}
}

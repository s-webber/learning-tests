package com.example.demo;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class ExampleTest extends ApplicationTest {
	private Stage stage;

	@BeforeClass
	public static void setUpClass() throws Exception {
		ApplicationTest.launch(ExampleApplication.class);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
	}

	@Test
	public void should_contain_button() {
		System.out.println("YGJK");
		verifyThat(stage.getTitle(), IsEqual.equalTo("Example JavaFX Application"));
		verifyThat(".button", hasText("click me!"));
		verifyThat(".label", hasText(""));

		clickOn("#textFieldId");
		write("world!");
		clickOn(".button");
		verifyThat(".label", hasText("hello world!"));

		clickOn("#textFieldId");
		press(KeyCode.CONTROL, KeyCode.A);
		release(KeyCode.CONTROL, KeyCode.A);
		write("everyone");
		clickOn(".button");
		verifyThat(".label", hasText("hello everyone"));
	}
}

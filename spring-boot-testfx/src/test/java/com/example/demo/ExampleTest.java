package com.example.demo;

import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.awt.HeadlessException;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

// Note: To avoid HeadlessException this test needs to be run with the VM argument: -Djava.awt.headless=false
// e.g.: mvn -Djava.awt.headless=false test
@ExtendWith(ApplicationExtension.class)
public class ExampleTest {
	private Stage stage;

	@Start
	private void start(Stage stage) throws Exception {
		this.stage = stage;
		ExampleApplication app = new ExampleApplication();
		app.init();
		app.start(stage);
	}

	@Test
	public void should_contain_button(FxRobot robot) {
		try {
			verifyThat(stage.getTitle(), IsEqual.equalTo("Example JavaFX Application"));
			verifyThat(".button", hasText("click me!"));
			verifyThat(".label", hasText(""));

			robot.clickOn("#textFieldId");
			robot.write("world!");
			robot.clickOn(".button");
			verifyThat(".label", hasText("hello world!"));

			robot.clickOn("#textFieldId");
			robot.press(KeyCode.CONTROL, KeyCode.A);
			robot.release(KeyCode.CONTROL, KeyCode.A);
			robot.write("everyone");
			robot.clickOn(".button");
			verifyThat(".label", hasText("hello everyone"));
		} catch (HeadlessException e) {
			fail("Caught HeadlessException. Need to run tests with VM argument: -Djava.awt.headless=false");
		}
	}
}

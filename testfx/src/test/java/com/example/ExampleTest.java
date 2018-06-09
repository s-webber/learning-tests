package com.example;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ExampleTest extends ApplicationTest {
   @BeforeClass
   public static void setUpClass() throws Exception {
      ApplicationTest.launch(ExampleApplication.class);
   }

   Stage stage;

   @Override
   public void start(Stage stage) throws Exception {
      this.stage = stage;
   }

   @Test
   public void should_contain_button() {
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

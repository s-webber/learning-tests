package com.example;

import static org.junit.Assert.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleTest {
   @Rule
   public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
   private RestDocumentationResultHandler document;
   private MockMvc mockMvc;
   @Autowired
   private WebApplicationContext webApplicationContext;

   @Before
   public void setup() throws Exception {
      document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
      mockMvc = webAppContextSetup(webApplicationContext).apply(documentationConfiguration(restDocumentation)).alwaysDo(document).build();
   }

   @Test
   public void test() throws Exception {
      MvcResult result = mockMvc.perform(get("/example")).andExpect(status().isOk()).andReturn();
      assertEquals("hello", result.getResponse().getContentAsString());
   }
}

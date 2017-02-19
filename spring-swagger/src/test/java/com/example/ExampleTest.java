package com.example;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleTest {
   private MockMvc mockMvc;
   @Autowired
   private WebApplicationContext webApplicationContext;

   @Before
   public void setup() throws Exception {
      mockMvc = webAppContextSetup(webApplicationContext).build();
   }

   @Test
   public void noParameter() throws Exception {
      MvcResult result = mockMvc.perform(get("/example/qwerty")).andExpect(status().isOk()).andReturn();
      assertEquals("{\"path\":\"qwerty\",\"param\":\"default\"}", result.getResponse().getContentAsString());
   }

   @Test
   public void withParameter() throws Exception {
      MvcResult result = mockMvc.perform(get("/example/zxcvbn?name=asdf")).andExpect(status().isOk()).andReturn();
      assertEquals("{\"path\":\"zxcvbn\",\"param\":\"asdf\"}", result.getResponse().getContentAsString());
   }
}

package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;

/**
 * Example of using springfox-staticdocs to produce static documentation based on Swagger annotations.
 * <p>
 * Documentation will be output to {@code target/generated-snippets/swagger}.
 * <p>
 * See http://springfox.github.io/springfox/docs/current/#configuring-springfox-staticdocs
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Swagger2MarkupTest {

   @Autowired
   private WebApplicationContext context;

   private MockMvc mockMvc;

   @Before
   public void setUp() {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
   }

   @Test
   public void convertSwaggerToAsciiDoc() throws Exception {
      this.mockMvc.perform(get("/v2/api-docs?group=example").accept(MediaType.APPLICATION_JSON))
            .andDo(Swagger2MarkupResultHandler.outputDirectory("target/generated-snippets/swagger").build()).andExpect(status().isOk());
   }
}

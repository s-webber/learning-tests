package com.how2examples;

import io.swagger.annotations.ApiModelProperty;

public class ExampleResponse {
   @ApiModelProperty(value = "The value specified in the path of the request URL.", required = true)
   private final String path;
   @ApiModelProperty(value = "The value provided as a parameter value in the request.", required = true)
   private final String param;

   public ExampleResponse(String path, String param) {
      this.path = path;
      this.param = param;
   }

   public String getPath() {
      return path;
   }

   public String getParam() {
      return param;
   }
}

package com.example;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {
   @ApiOperation(value = "Example operation", notes = "A simple REST operation to demonstrate integration with Swagger.")
   @RequestMapping(path = "/{resourceId}", method = RequestMethod.GET, produces = "application/json")
   public ExampleResponse greeting(
         @PathVariable("resourceId") @ApiParam(value = "A value specified in the path of the request URL.", required = true) String path,
         @RequestParam(value = "name", defaultValue = "default") @ApiParam("A value provided as a parameter value in the request.") String param) {
      return new ExampleResponse(path, param);
   }
}

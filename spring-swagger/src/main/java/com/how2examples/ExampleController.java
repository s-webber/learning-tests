package com.how2examples;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api(value = "Example API", description = "A simple REST service to demonstrate integration with Swagger.")
@RestController
@RequestMapping("/example")
public class ExampleController {
   @ApiOperation(value = "Example operation", notes = "A simple REST operation to demonstrate integration with Swagger.")
   @ApiResponses(@ApiResponse(code = 200, message = "OK"))
   @RequestMapping(value = "/{resourceId}", method = RequestMethod.GET)
   public ExampleResponse greeting(@PathVariable(value = "resourceId") @ApiParam(value = "A value specified in the path of the request URL.") String path,
         @RequestParam(value = "name", defaultValue = "default") @ApiParam(value = "A value provided as a parameter value in the request.") String param) {
      return new ExampleResponse(path, param);
   }
}

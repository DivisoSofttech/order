/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.order.client.bpmn.api;

import java.util.Map;
import com.diviso.graeshoppe.order.client.bpmn.model.ProcessEngineInfoResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-08-01T11:49:01.976907+05:30[Asia/Kolkata]")

@Api(value = "Engine", description = "the Engine API")
public interface EngineApi {

    @ApiOperation(value = "Get engine info", nickname = "getEngineInfo", notes = "", response = ProcessEngineInfoResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Engine", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the engine info is returned.", response = ProcessEngineInfoResponse.class) })
    @RequestMapping(value = "/management/engine",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<ProcessEngineInfoResponse> getEngineInfo();


    @ApiOperation(value = "Get engine properties", nickname = "getProperties", notes = "", response = String.class, responseContainer = "Map", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Engine", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the properties are returned.", response = Map.class, responseContainer = "Map") })
    @RequestMapping(value = "/management/properties",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<Map<String, String>> getProperties();

}

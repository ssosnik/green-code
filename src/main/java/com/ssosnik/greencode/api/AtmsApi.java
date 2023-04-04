/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.5.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.ssosnik.greencode.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T15:51:15.919232900+02:00[Europe/Warsaw]")
@Validated
@Tag(name = "atms", description = "the atms API")
public interface AtmsApi {

    /**
     * POST /atms/calculateOrder
     * Calculates ATMs order for service team
     *
     * @param task  (required)
     * @return Successful operation (status code 200)
     */
    @Operation(
        operationId = "calculate",
        description = "Calculates ATMs order for service team",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ATM.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/atms/calculateOrder",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<List<ATM>> calculate(
        @Parameter(name = "Task", description = "", required = true) @Valid @RequestBody List<Task> task
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
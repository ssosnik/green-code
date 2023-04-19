/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.5.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.ssosnik.greencode.api;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssosnik.greencode.model.AccountImplSerial;
import com.ssosnik.greencode.model.AccountInterface;
import com.ssosnik.greencode.model.Transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:15:24.122895800+02:00[Europe/Warsaw]")
@Validated
@Tag(name = "transactions", description = "the transactions API")
public interface TransactionsApi {

	/**
	 * POST /transactions/report Execute report
	 *
	 * @param transaction (required)
	 * @return Successful operation (status code 200)
	 */
	@Operation(operationId = "report", description = "Execute report", responses = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AccountImplSerial.class))) }) })
	@RequestMapping(method = RequestMethod.POST, value = "/transactions/report", produces = {
			"application/json" }, consumes = { "application/json" })
	default ResponseEntity<List<AccountInterface>> report(
			@Parameter(name = "Transaction", description = "", required = true) @Valid @Size(max = 100000) @RequestBody List<Transaction> transaction) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

}

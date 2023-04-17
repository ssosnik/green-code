package com.ssosnik.greencode.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public interface AccountInterface {

	/**
	 * Get account
	 * 
	 * @return account
	 */
	@Size(min = 26, max = 26)
	@Schema(name = "account", example = "3.2309111922661937E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("account")
	String getAccount();

	/**
	 * Number of debit transactions
	 * 
	 * @return debitCount
	 */
	@Schema(name = "debitCount", example = "2", description = "Number of debit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("debitCount")
	Integer getDebitCount();

	/**
	 * Number of credit transactions
	 * 
	 * @return creditCount
	 */
	@Schema(name = "creditCount", example = "2", description = "Number of credit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("creditCount")
	Integer getCreditCount();

	/**
	 * Get balance
	 * 
	 * @return balance
	 */
	@Schema(name = "balance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("balance")
	BigDecimal getBalance();

	void creditCountIncrement();

	void debitCountIncrement();
	
	void balanceIncrease(BigDecimal amount);

	void balanceDecrease(BigDecimal amount);


}
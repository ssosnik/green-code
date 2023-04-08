package com.ssosnik.greencode.model;

import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * Transaction
 */

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:15:24.122895800+02:00[Europe/Warsaw]")
public class Transaction {

	private String debitAccount;

	private String creditAccount;

	private Float amount;

	public Transaction debitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
		return this;
	}

	/**
	 * Get debitAccount
	 * 
	 * @return debitAccount
	 */
	@Size(min = 26, max = 26)
	@Schema(name = "debitAccount", example = "3.2309111922661937E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("debitAccount")
	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}

	public Transaction creditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
		return this;
	}

	/**
	 * Get creditAccount
	 * 
	 * @return creditAccount
	 */
	@Size(min = 26, max = 26)
	@Schema(name = "creditAccount", example = "3.107431869813706E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("creditAccount")
	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	public Transaction amount(Float amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Get amount
	 * 
	 * @return amount
	 */

	@Schema(name = "amount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("amount")
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Transaction transaction = (Transaction) o;
		return Objects.equals(this.debitAccount, transaction.debitAccount)
				&& Objects.equals(this.creditAccount, transaction.creditAccount)
				&& Objects.equals(this.amount, transaction.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(debitAccount, creditAccount, amount);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Transaction {\n");
		sb.append("    debitAccount: ").append(toIndentedString(debitAccount)).append("\n");
		sb.append("    creditAccount: ").append(toIndentedString(creditAccount)).append("\n");
		sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}

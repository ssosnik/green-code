package com.ssosnik.greencode.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * AccountImplSerial
 */

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:15:24.122895800+02:00[Europe/Warsaw]")
public class AccountImplSerial implements AccountInterface {

	private String account;
	private Integer debitCount = 0;
	private Integer creditCount = 0;
	private BigDecimal balance = BigDecimal.ZERO;

	public AccountImplSerial() {
	}

	public AccountImplSerial(String accountNumber) {
		this.account = accountNumber;
	}

	/**
	 * Get account
	 * 
	 * @return account
	 */
	@Override
	public String getAccount() {
		return account;
	}

	/**
	 * Number of debit transactions
	 * 
	 * @return debitCount
	 */

	@Override
	public Integer getDebitCount() {
		return debitCount;
	}

	/**
	 * Number of credit transactions
	 * 
	 * @return creditCount
	 */

	@Override
	public Integer getCreditCount() {
		return creditCount;
	}

	/**
	 * Get balance
	 * 
	 * @return balance
	 */

	@Override
	public BigDecimal getBalance() {
		return balance;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AccountImplSerial account = (AccountImplSerial) o;
		return Objects.equals(this.account, account.account) && Objects.equals(this.debitCount, account.debitCount)
				&& Objects.equals(this.creditCount, account.creditCount)
				&& Objects.equals(this.balance, account.balance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(account, debitCount, creditCount, balance);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AccountImplSerial {\n");
		sb.append("    account: ").append(toIndentedString(account)).append("\n");
		sb.append("    debitCount: ").append(toIndentedString(debitCount)).append("\n");
		sb.append("    creditCount: ").append(toIndentedString(creditCount)).append("\n");
		sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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

	@Override
	public void creditCountIncrement() {
		creditCount++;		
	}

	@Override
	public void debitCountIncrement() {
		debitCount++;				
	}

	@Override
	public void balanceIncrease(BigDecimal amount) {
		balance = balance.add(amount);		
	}

	@Override
	public void balanceDecrease(BigDecimal amount) {
		balance = balance.subtract(amount);				
	}
}

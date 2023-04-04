package com.ssosnik.greencode.model;

import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * Account
 */

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:15:24.122895800+02:00[Europe/Warsaw]")
public class Account {

  private String account;

  private Integer debitCount;

  private Integer creditCount;

  private Float balance;

  public Account account(String account) {
    this.account = account;
    return this;
  }

  /**
   * Get account
   * @return account
  */
  @Size(min = 26, max = 26) 
  @Schema(name = "account", example = "3.2309111922661937E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("account")
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public Account debitCount(Integer debitCount) {
    this.debitCount = debitCount;
    return this;
  }

  /**
   * Number of debit transactions
   * @return debitCount
  */
  
  @Schema(name = "debitCount", example = "2", description = "Number of debit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("debitCount")
  public Integer getDebitCount() {
    return debitCount;
  }

  public void setDebitCount(Integer debitCount) {
    this.debitCount = debitCount;
  }

  public Account creditCount(Integer creditCount) {
    this.creditCount = creditCount;
    return this;
  }

  /**
   * Number of credit transactions
   * @return creditCount
  */
  
  @Schema(name = "creditCount", example = "2", description = "Number of credit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("creditCount")
  public Integer getCreditCount() {
    return creditCount;
  }

  public void setCreditCount(Integer creditCount) {
    this.creditCount = creditCount;
  }

  public Account balance(Float balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  */
  
  @Schema(name = "balance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("balance")
  public Float getBalance() {
    return balance;
  }

  public void setBalance(Float balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.account, account.account) &&
        Objects.equals(this.debitCount, account.debitCount) &&
        Objects.equals(this.creditCount, account.creditCount) &&
        Objects.equals(this.balance, account.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, debitCount, creditCount, balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
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
}


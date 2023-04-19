package com.ssosnik.greencode.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AccountImplParallel implements AccountInterface {

    private String accountNumber;
    private AtomicInteger creditCount = new AtomicInteger();
    private AtomicInteger debitCount = new AtomicInteger();
    private AtomicReference<BigDecimal> balance = new AtomicReference<>(BigDecimal.ZERO);

    public AccountImplParallel() {
	}

    public AccountImplParallel(String accountNumber) {
    	this.accountNumber = accountNumber;
	}

	@Override
    public String getAccount() {
        return accountNumber;
    }

    @Override
    public Integer getDebitCount() {
        return debitCount.get();
    }

    @Override
    public Integer getCreditCount() {
        return creditCount.get();
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void creditCountIncrement() {
        creditCount.incrementAndGet();
    }

    @Override
    public void debitCountIncrement() {
        debitCount.incrementAndGet();
    }

    @Override
    public void balanceIncrease(BigDecimal amount) {
        balance.accumulateAndGet(amount, BigDecimal::add);
    }

    @Override
    public void balanceDecrease(BigDecimal amount) {
        balance.accumulateAndGet(amount, BigDecimal::subtract);
    }
}
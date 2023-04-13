package com.ssosnik.greencode.service;

import java.util.List;

import com.ssosnik.greencode.model.Account;
import com.ssosnik.greencode.model.Transaction;
import com.ssosnik.greencode.service.TransactionServiceImpl.CalculateMethod;


public interface TransactionService {

	List<Account> calculateAccountList(List<Transaction> transaction);

	List<Account> calculateAccountList(List<Transaction> transaction, CalculateMethod method);
}

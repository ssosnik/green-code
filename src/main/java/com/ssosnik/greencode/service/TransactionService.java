package com.ssosnik.greencode.service;

import java.util.List;

import com.ssosnik.greencode.model.AccountInterface;
import com.ssosnik.greencode.model.Transaction;
import com.ssosnik.greencode.service.TransactionServiceImpl.CalculateMethod;


public interface TransactionService {

	List<AccountInterface> calculateAccountList(List<Transaction> transaction);

	List<AccountInterface> calculateAccountList(List<Transaction> transaction, CalculateMethod method);
}

package com.ssosnik.greencode.service;

import java.util.List;

import com.ssosnik.greencode.model.Account;
import com.ssosnik.greencode.model.Transaction;

public interface TransactionService {

	List<Account> calculateAccountList(List<Transaction> transaction);

}

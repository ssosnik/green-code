package com.ssosnik.greencode.api;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssosnik.greencode.model.Account;
import com.ssosnik.greencode.model.Transaction;

public interface TransactionService {

	List<Account> calculateAccountList(List<Transaction> transaction);


}

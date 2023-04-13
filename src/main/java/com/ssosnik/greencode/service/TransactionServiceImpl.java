package com.ssosnik.greencode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Account;
import com.ssosnik.greencode.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	public enum CalculateMethod {
		Simple, Parallel;
	}


	@Override
	public List<Account> calculateAccountList(List<Transaction> transaction) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}


	@Override
	public List<Account> calculateAccountList(List<Transaction> transactions,
			CalculateMethod method) {
		List<Account>  combinedAtmList = method == CalculateMethod.Simple ? simpleSolution(transactions)
				: parallelSolution(transactions);

		return combinedAtmList;	
	}


	private List<Account> parallelSolution(List<Transaction> transactions) {
		return null;
	}


	private List<Account> simpleSolution(List<Transaction> transactions) {		
		HashMap<String, Account> accountMap = new HashMap<>();				
		for (Transaction t : transactions) {
			Account creditAccount = accountMap.computeIfAbsent(t.getCreditAccount(), k -> createNewAccount(k));
			creditAccount.creditCount(creditAccount.getCreditCount()+1);
			creditAccount.balance(creditAccount.getBalance()+t.getAmount());
			
			Account debitAccount = accountMap.computeIfAbsent(t.getDebitAccount(), k -> createNewAccount(k));
			debitAccount.debitCount(debitAccount.getDebitCount()+1);
			debitAccount.balance(debitAccount.getBalance()-t.getAmount());			
		}
		
		Map<String, Account> sortedMap = new TreeMap<>(accountMap);
		
		return new ArrayList<Account>(sortedMap.values());
	}


	private Account createNewAccount(String accountNumber) {
		return new Account().account(accountNumber).creditCount(0).debitCount(0).balance(Float.valueOf(0.0f));
	}

}
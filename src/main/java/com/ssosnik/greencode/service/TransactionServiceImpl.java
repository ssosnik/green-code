package com.ssosnik.greencode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.ssosnik.greencode.model.Account;
import com.ssosnik.greencode.model.AccountInterface;
import com.ssosnik.greencode.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	public enum CalculateMethod {
		Simple, Parallel;
	}


	@Override
	public List<AccountInterface> calculateAccountList(List<Transaction> transaction) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}


	@Override
	public List<AccountInterface> calculateAccountList(List<Transaction> transactions,
			CalculateMethod method) {
		List<AccountInterface>  combinedAtmList = method == CalculateMethod.Simple ? simpleSolution(transactions)
				: parallelSolution(transactions);

		return combinedAtmList;	
	}


	private List<AccountInterface> parallelSolution(List<Transaction> transactions) {
		return null;
	}


	private List<AccountInterface> simpleSolution(List<Transaction> transactions) {		
		HashMap<String, AccountInterface> accountMap = new HashMap<>();				
		for (Transaction t : transactions) {
			AccountInterface creditAccount = accountMap.computeIfAbsent(t.getCreditAccount(), k -> createNewAccount(k));
			creditAccount.creditCountIncrement();
			creditAccount.balanceIncrease(t.getAmount());
			
			AccountInterface debitAccount = accountMap.computeIfAbsent(t.getDebitAccount(), k -> createNewAccount(k));
			debitAccount.debitCountIncrement();
			debitAccount.balanceDecrease(t.getAmount());			
		}
		
		Map<String, AccountInterface> sortedMap = new TreeMap<>(accountMap);
		
		return new ArrayList<AccountInterface>(sortedMap.values());
	}


	private AccountInterface createNewAccount(String accountNumber) {
		return new Account(accountNumber);
	}

}
package com.ssosnik.greencode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ssosnik.greencode.model.AccountImplParallel;
import com.ssosnik.greencode.model.AccountImplSerial;
import com.ssosnik.greencode.model.AccountInterface;
import com.ssosnik.greencode.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	public enum CalculateMethod {
		Parallel, Serial;
	}


	@Override
	public List<AccountInterface> calculateAccountList(List<Transaction> transactions) {
		List<AccountInterface>  accountList = transactions.size() < 20000 ? simpleSolution(transactions)
				: parallelSolution(transactions);

		return accountList;	
	}


	@Override
	public List<AccountInterface> calculateAccountList(List<Transaction> transactions,
			CalculateMethod method) {
		List<AccountInterface>  accountList = method == CalculateMethod.Serial ? simpleSolution(transactions)
				: parallelSolution(transactions);

		return accountList;	
	}

	private List<AccountInterface> parallelSolution(List<Transaction> transactions) {
		Map<String, AccountInterface> accountMap = new ConcurrentHashMap<>();
		transactions.parallelStream().forEach(t -> {
			AccountInterface creditAccount = accountMap.computeIfAbsent(t.getCreditAccount(), k -> newParallelAccount(k));
			creditAccount.creditCountIncrement();
			creditAccount.balanceIncrease(t.getAmount());

			AccountInterface debitAccount = accountMap.computeIfAbsent(t.getDebitAccount(), k -> newParallelAccount(k));
			debitAccount.debitCountIncrement();
			debitAccount.balanceDecrease(t.getAmount());
		});

		Map<String, AccountInterface> sortedMap = new TreeMap<>(accountMap);

		return new ArrayList<>(sortedMap.values());
	}

	private AccountInterface newParallelAccount(String accountNumber) {
		return new AccountImplParallel(accountNumber);
	}


	private List<AccountInterface> simpleSolution(List<Transaction> transactions) {		
		HashMap<String, AccountInterface> accountMap = new HashMap<>();				
		for (Transaction t : transactions) {
			AccountInterface creditAccount = accountMap.computeIfAbsent(t.getCreditAccount(), k -> newSerialAccount(k));
			creditAccount.creditCountIncrement();
			creditAccount.balanceIncrease(t.getAmount());
			
			AccountInterface debitAccount = accountMap.computeIfAbsent(t.getDebitAccount(), k -> newSerialAccount(k));
			debitAccount.debitCountIncrement();
			debitAccount.balanceDecrease(t.getAmount());			
		}
		
		Map<String, AccountInterface> sortedMap = new TreeMap<>(accountMap);
		
		return new ArrayList<AccountInterface>(sortedMap.values());
	}


	private AccountInterface newSerialAccount(String accountNumber) {
		return new AccountImplSerial(accountNumber);
	}

}
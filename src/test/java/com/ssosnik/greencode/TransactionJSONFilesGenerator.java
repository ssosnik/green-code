package com.ssosnik.greencode;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ssosnik.greencode.model.AccountInterface;
import com.ssosnik.greencode.model.Transaction;
import com.ssosnik.greencode.service.TransactionService;
import com.ssosnik.greencode.service.TransactionServiceImpl;
import com.ssosnik.greencode.service.TransactionServiceImpl.CalculateMethod;

public class TransactionJSONFilesGenerator {
	private static final Random rand = new Random();


	private static final ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT,
			false);

	private static final File TRANSACTION_SERVICE_FOLDER_INPUT = new File(
			"src/test/resources/testing-files/transactions/request");
	private static final File TRANSACTION_SERVICE_FOLDER_OUTPUT = new File(
			"src/test/resources/testing-files/transactions/response");

	public List<Transaction> createRandomTransactionList(int transactionCount) {
		List<Transaction> transactions = new ArrayList<>();
		
		int accountCount = transactionCount/50;

		List<String> accountList = new ArrayList<>();
		for (int i = 0; i < accountCount; i++) {
			String accountNumber = generateRandomDigits(26);
			accountList.add(accountNumber);
		}

		
		for (int i = 0; i < transactionCount; i++) {
			Transaction transaction = new Transaction();
			int accountIndexCredit = rand.nextInt(accountCount);			
			transaction.setCreditAccount(accountList.get(accountIndexCredit));
			
			int accountIndexDebit = rand.nextInt(accountCount);
			transaction.setDebitAccount(accountList.get(accountIndexDebit));
			
			BigDecimal amount = generateRandomAmount();
			transaction.setAmount(amount);
			
			transactions.add(transaction);
		}

		return transactions;
	}

	public void saveToJsonFile(Object object, File file) throws IOException {
		objectMapper.writeValue(file, object);
	}
	
	public static String generateRandomDigits(int length) {
		String digits = "0123456789";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			Integer digit = rand.nextInt(digits.length());
			sb.append(digit.toString());
		}
		return sb.toString();
	}
	
	public static BigDecimal generateRandomAmount() {
	    BigDecimal max = new BigDecimal("1000");
	    BigDecimal randomBigDecimal = max.multiply(new BigDecimal(rand.nextDouble()));
	    randomBigDecimal = randomBigDecimal.setScale(2, RoundingMode.DOWN);
	    return randomBigDecimal;
	}

	public static void main(String[] args) throws IOException {
		TransactionJSONFilesGenerator generator = new TransactionJSONFilesGenerator();

		for (int i = 1; i <= 15; i++) {
			Integer size = i*10000;
			List<Transaction> transactions = generator.createRandomTransactionList(size.intValue());
			String filename = String.format("transactions%06d.json", size);;
			generator.saveToJsonFile(transactions, new File(TRANSACTION_SERVICE_FOLDER_INPUT, filename));

			TransactionService transactionService = new TransactionServiceImpl();
			List<AccountInterface> actualResult = transactionService.calculateAccountList(transactions, CalculateMethod.Serial);
			generator.saveToJsonFile(actualResult, new File(TRANSACTION_SERVICE_FOLDER_OUTPUT, filename));

		}
	}

}

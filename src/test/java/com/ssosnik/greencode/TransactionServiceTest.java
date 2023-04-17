package com.ssosnik.greencode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssosnik.greencode.model.Account;
import com.ssosnik.greencode.model.AccountInterface;
import com.ssosnik.greencode.model.Transaction;
import com.ssosnik.greencode.service.TransactionService;
import com.ssosnik.greencode.service.TransactionServiceImpl.CalculateMethod;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionServiceTest {

	static private String TESTING_FILES_RESOURCE_DIRECTORY = "testing-files/transactions/";
	static private String TESTING_FILES_RESOURCE_DIRECTORY_INPUT = TESTING_FILES_RESOURCE_DIRECTORY + "request/";
	static private String TESTING_FILES_RESOURCE_DIRECTORY_OUTPUT = TESTING_FILES_RESOURCE_DIRECTORY + "response/";

	@Autowired
	private TransactionService transactionService;

	private final ObjectMapper objectMapper = new ObjectMapper();

//	@ParameterizedTest
//	@MethodSource("jsonFiles")
	public void testCalculate(String jsonFileName) throws IOException {
		// Arrange
		long startTime = System.currentTimeMillis();
		List<Transaction> transactions = readInput(jsonFileName);
		long endTime = System.currentTimeMillis();
		long elapsedTime1 = endTime - startTime;
		List<Account> expectedResult = readOutput(jsonFileName);

		// Record the start time
		startTime = System.currentTimeMillis();

		// Act
		List<AccountInterface> actualResult = transactionService.calculateAccountList(transactions);

		// Record the end time
		endTime = System.currentTimeMillis();

		// Calculate and print the elapsed time
		long elapsedTime2 = endTime - startTime;
		System.out.println("calculate: " + jsonFileName + " time: " + elapsedTime1 + ", " + elapsedTime2);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateSimple(String jsonFileName) throws IOException {
		testCalculateMethod(jsonFileName, CalculateMethod.Simple);
	}

//	@ParameterizedTest
//	@MethodSource("jsonFiles")
//	public void testCalculateSerial(String jsonFileName) throws IOException {
//		testCalculateMethod(jsonFileName, CalculateMethod.Serial);
//	}
//
//	@ParameterizedTest
//	@MethodSource("jsonFiles")
//	public void testCalculateParallel(String jsonFileName) throws IOException {
//		testCalculateMethod(jsonFileName, CalculateMethod.Parallel);
//	}
//
	private void testCalculateMethod(String jsonFileName, CalculateMethod calculateMethod)
			throws IOException, StreamReadException, DatabindException {
		// Arrange
		long startTime = System.currentTimeMillis();
		List<Transaction> transactions = readInput(jsonFileName);
		long endTime = System.currentTimeMillis();
		long elapsedTime1 = endTime - startTime;
		List<Account> expectedResult = readOutput(jsonFileName);

		// Record the start time
		startTime = System.currentTimeMillis();

		// Act
		List<AccountInterface> actualResult = transactionService.calculateAccountList(transactions, calculateMethod);

		// Record the end time
		endTime = System.currentTimeMillis();

		// Calculate and print the elapsed time
		long elapsedTime2 = endTime - startTime;
		System.out.println(
				calculateMethod.toString() + ", " + jsonFileName + " time: " + elapsedTime1 + ", " + elapsedTime2);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	private List<Account> readOutput(String jsonFileName) throws IOException, StreamReadException, DatabindException {
		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_OUTPUT + jsonFileName;
		testFilePath = testFilePath.replace("_request", "_response");
		ClassPathResource expectedResource = new ClassPathResource(testFilePath);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Account> expectedResult = objectMapper.readValue(expectedResource.getInputStream(),
				new TypeReference<List<Account>>() {
				});
		return expectedResult;
	}

	private List<Transaction> readInput(String jsonFileName) throws IOException, StreamReadException, DatabindException {
		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_INPUT + jsonFileName;
		ClassPathResource inputResource = new ClassPathResource(testFilePath);
		List<Transaction> transactions = objectMapper.readValue(inputResource.getInputStream(), new TypeReference<List<Transaction>>() {});
		return transactions;
	}

	static List<String> jsonFiles() throws IOException, URISyntaxException {
		URL resource = TransactionServiceTest.class.getClassLoader().getResource(TESTING_FILES_RESOURCE_DIRECTORY_INPUT);
		Path inputDirectoryPath = Paths.get(resource.toURI());
		List<String> fileNames = Files.list(inputDirectoryPath)
				.filter(path -> Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".json"))
				.map(path -> path.getFileName().toString()).collect(Collectors.toList());
		return fileNames;
	}
}

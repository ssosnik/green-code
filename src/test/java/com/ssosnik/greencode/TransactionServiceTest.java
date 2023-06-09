package com.ssosnik.greencode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
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
import com.ssosnik.greencode.model.AccountImplSerial;
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

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculate(String jsonFileName) throws IOException {
		// Arrange
		long startTime = System.currentTimeMillis();
		List<Transaction> transactions = readInput(jsonFileName);
		long endTime = System.currentTimeMillis();
		long elapsedTime1 = endTime - startTime;
		List<AccountImplSerial> expectedResult = readOutput(jsonFileName);

		// Record the start time
		startTime = System.currentTimeMillis();

		// Act
		List<AccountInterface> actualResult = transactionService.calculateAccountList(transactions);

		// Record the end time
		endTime = System.currentTimeMillis();		
		long elapsedTime2 = endTime - startTime;

		// Record the start time
		startTime = System.currentTimeMillis();

		// write result to bytearray
		byte[] jsonBytes = writeJsonToByteArray(actualResult);
		
		// Record the end time
		endTime = System.currentTimeMillis();

		// read from byte array
		List<AccountImplSerial> actualResult2 = readJsonFromByteArray(jsonBytes);

		// Calculate and print the elapsed time
		long elapsedTime3 = endTime - startTime;
		System.out.println(String.format("calculate time %s: %d, %d, %d", jsonFileName,
				elapsedTime1, elapsedTime2, elapsedTime3));

		// Assert
		assertEquals(expectedResult, actualResult2);
	}


	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateSerial(String jsonFileName) throws IOException {
		testCalculateMethod(jsonFileName, CalculateMethod.Serial);
	}

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateParallel(String jsonFileName) throws IOException {
		testCalculateMethod(jsonFileName, CalculateMethod.Parallel);
	}
	
	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateSerialTime(String jsonFileName) throws IOException {
		testCalculateMethodTime(jsonFileName, CalculateMethod.Serial);
	}

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateParallelTime(String jsonFileName) throws IOException {
		testCalculateMethodTime(jsonFileName, CalculateMethod.Parallel);
	}
	

	private void testCalculateMethod(String jsonFileName, CalculateMethod calculateMethod)
			throws IOException, StreamReadException, DatabindException {
		// Arrange
		long startTime = System.currentTimeMillis();
		List<Transaction> transactions = readInput(jsonFileName);
		long endTime = System.currentTimeMillis();
		long elapsedTime1 = endTime - startTime;
		List<AccountImplSerial> expectedResult = readOutput(jsonFileName);

		// Record the start time
		startTime = System.currentTimeMillis();

		// Act
		List<AccountInterface> actualResult = transactionService.calculateAccountList(transactions, calculateMethod);

		// Record the end time
		endTime = System.currentTimeMillis();		
		long elapsedTime2 = endTime - startTime;

		// Record the start time
		startTime = System.currentTimeMillis();

		// write result to bytearray
		byte[] jsonBytes = writeJsonToByteArray(actualResult);
		
		// Record the end time
		endTime = System.currentTimeMillis();

		// read from byte array
		List<AccountImplSerial> actualResult2 = readJsonFromByteArray(jsonBytes);

		// Calculate and print the elapsed time
		long elapsedTime3 = endTime - startTime;
		System.out.println(String.format("%s time %s: %d, %d, %d", calculateMethod.toString(), jsonFileName,
				elapsedTime1, elapsedTime2, elapsedTime3));


		// Assert
		assertEquals(expectedResult, actualResult2);
	}
	
	private void testCalculateMethodTime(String jsonFileName, CalculateMethod calculateMethod)
			throws IOException, StreamReadException, DatabindException {
		// Arrange
		long[] elapsedTime = {0, 0, 0, 0, 0};

		for (int i = 0; i < 10; i++) {

			long startTime = System.currentTimeMillis();
			List<Transaction> transactions = readInput(jsonFileName);
			long endTime = System.currentTimeMillis();
			elapsedTime[0] += endTime - startTime;
	
			// Record the start time
			startTime = System.currentTimeMillis();
	
			// Act
			List<AccountInterface> actualResult = transactionService.calculateAccountList(transactions, calculateMethod);
	
			// Record the end time
			endTime = System.currentTimeMillis();		
			elapsedTime[1] += endTime - startTime;
	
			// Record the start time
			startTime = System.currentTimeMillis();
	
			// write result to bytearray
			byte[] jsonBytes = writeJsonToByteArray(actualResult);
			
			// Record the end time
			endTime = System.currentTimeMillis();
	
			assertTrue(jsonBytes.length >= 0);
	
			// Calculate and print the elapsed time
			elapsedTime[2] += endTime - startTime;
		}

		long totalTime = elapsedTime[0] + elapsedTime[1] + elapsedTime[2];
		System.out.println(String.format("Transaction Time %s, %s: %d + %d + %d = %d", calculateMethod.toString(), jsonFileName, elapsedTime[0], elapsedTime[1], elapsedTime[2], totalTime));	

	}


	private List<AccountImplSerial> readOutput(String jsonFileName) throws IOException, StreamReadException, DatabindException {
		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_OUTPUT + jsonFileName;
		testFilePath = testFilePath.replace("_request", "_response");
		ClassPathResource expectedResource = new ClassPathResource(testFilePath);
		ObjectMapper objectMapper = new ObjectMapper();
		List<AccountImplSerial> expectedResult = objectMapper.readValue(expectedResource.getInputStream(),
				new TypeReference<List<AccountImplSerial>>() {
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
	
	public byte[] writeJsonToByteArray(Object myObject) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			objectMapper.writeValue(outputStream, myObject);
			return outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AccountImplSerial> readJsonFromByteArray(byte[] jsonBytes) {
        try {
        	List<AccountImplSerial> expectedResult = objectMapper.readValue(jsonBytes, new TypeReference<List<AccountImplSerial>>() {});
            return expectedResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.ssosnik.greencode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.ssosnik.greencode.model.Clan;
import com.ssosnik.greencode.model.Players;
import com.ssosnik.greencode.service.OnlineGameService;
import com.ssosnik.greencode.service.OnlineGameServiceImpl.CalculateMethod;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OnlineGameServiceTest {

	static private String TESTING_FILES_RESOURCE_DIRECTORY = "testing-files/onlinegame/";
	static private String TESTING_FILES_RESOURCE_DIRECTORY_INPUT = TESTING_FILES_RESOURCE_DIRECTORY + "request/";
	static private String TESTING_FILES_RESOURCE_DIRECTORY_OUTPUT = TESTING_FILES_RESOURCE_DIRECTORY + "response/";

	@Autowired
	private OnlineGameService onlineGameService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculate(String jsonFileName) throws IOException {
		// Arrange
		long startTime = System.currentTimeMillis();
		Players players = readInput(jsonFileName);
		long endTime = System.currentTimeMillis();
		long elapsedTime1 = endTime - startTime;
		List<List<Clan>> expectedResult = readOutput(jsonFileName);

		// Record the start time
		startTime = System.currentTimeMillis();

		// Act
		List<List<Clan>> actualResult = onlineGameService.calculateClanList(players);

		// Record the end time
		endTime = System.currentTimeMillis();

		// Calculate and print the elapsed time
		long elapsedTime2 = endTime - startTime;
		System.out.println(String.format("calculate time %s, %s: %d, %d", jsonFileName,
				jsonFileName.substring(7, 12), elapsedTime1, elapsedTime2));


		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateSimple(String jsonFileName) throws IOException {
		testCalculateMethod(jsonFileName, CalculateMethod.Simple);
	}

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateOptimized(String jsonFileName) throws IOException {
		testCalculateMethod(jsonFileName, CalculateMethod.Optimized);
	}
	
	private void testCalculateMethod(String jsonFileName, CalculateMethod calculateMethod)
			throws IOException, StreamReadException, DatabindException {
		// Arrange
		long startTime = System.currentTimeMillis();
		Players players = readInput(jsonFileName);
		long endTime = System.currentTimeMillis();
		long elapsedTime1 = endTime - startTime;
		List<List<Clan>> expectedResult = readOutput(jsonFileName);

		// Record the start time
		startTime = System.currentTimeMillis();

		// Act
		List<List<Clan>> actualResult = onlineGameService.calculateClanList(players, calculateMethod);

		// Record the end time
		endTime = System.currentTimeMillis();

		// Calculate and print the elapsed time
		long elapsedTime2 = endTime - startTime;
		System.out.println(String.format("%s time %s, %s: %d, %d", calculateMethod.toString(), jsonFileName,
				jsonFileName.substring(7, 12), elapsedTime1, elapsedTime2));

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	
	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateSimpleTime(String jsonFileName) throws IOException {
		testCalculateMethodTime(jsonFileName, CalculateMethod.Simple);
	}

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculateOptimizedTime(String jsonFileName) throws IOException {
		testCalculateMethodTime(jsonFileName, CalculateMethod.Optimized);
	}
	
	private void testCalculateMethodTime(String jsonFileName, CalculateMethod calculateMethod)
			throws IOException, StreamReadException, DatabindException {
		long[] elapsedTime = {0, 0, 0, 0, 0};

		for (int i = 0; i < 10; i++) {
			// Arrange
			long startTime = System.currentTimeMillis();
			Players players = readInput(jsonFileName);
			long endTime = System.currentTimeMillis();
			elapsedTime[0] += endTime - startTime;
	
			// Record the start time
			startTime = System.currentTimeMillis();
	
			// Act
			List<List<Clan>> actualResult = onlineGameService.calculateClanList(players, calculateMethod);
	
			// Record the end time
			endTime = System.currentTimeMillis();
	
			// Calculate and print the elapsed time
			elapsedTime[1] += endTime - startTime;
			
			startTime = System.currentTimeMillis();
			byte[] data = objectMapper.writeValueAsBytes(actualResult);
			endTime = System.currentTimeMillis();
			elapsedTime[2] += endTime - startTime;
			
			assertTrue(data.length >= 0);

		}

		long totalTime = elapsedTime[0] + elapsedTime[1] + elapsedTime[2];
		System.out.println(String.format("Onlinegame Time %s, %s: %d + %d + %d = %d", calculateMethod.toString(), jsonFileName, elapsedTime[0], elapsedTime[1], elapsedTime[2], totalTime));	
	}

	private List<List<Clan>> readOutput(String jsonFileName) throws IOException, StreamReadException, DatabindException {
		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_OUTPUT + jsonFileName;
		testFilePath = testFilePath.replace("_request", "_response");
		ClassPathResource expectedResource = new ClassPathResource(testFilePath);
		ObjectMapper objectMapper = new ObjectMapper();
		List<List<Clan>> expectedResult = objectMapper.readValue(expectedResource.getInputStream(),
				new TypeReference<List<List<Clan>>>() {
				});
		return expectedResult;
	}

	private Players readInput(String jsonFileName) throws IOException, StreamReadException, DatabindException {
		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_INPUT + jsonFileName;
		ClassPathResource inputResource = new ClassPathResource(testFilePath);
		Players players = objectMapper.readValue(inputResource.getInputStream(), new TypeReference<Players>() {});
		return players;
	}

	static List<String> jsonFiles() throws IOException, URISyntaxException {
		URL resource = OnlineGameServiceTest.class.getClassLoader().getResource(TESTING_FILES_RESOURCE_DIRECTORY_INPUT);
		Path inputDirectoryPath = Paths.get(resource.toURI());
		List<String> fileNames = Files.list(inputDirectoryPath)
				.filter(path -> Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".json"))
				.map(path -> path.getFileName().toString()).collect(Collectors.toList());
		return fileNames;
	}
}

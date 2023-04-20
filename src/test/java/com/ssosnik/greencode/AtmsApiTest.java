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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssosnik.greencode.model.ATM;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AtmsApiTest {

	static private String TESTING_FILES_RESOURCE_DIRECTORY = "testing-files/atmservice/";
	static private String TESTING_FILES_RESOURCE_DIRECTORY_INPUT = TESTING_FILES_RESOURCE_DIRECTORY + "request/";
	static private String TESTING_FILES_RESOURCE_DIRECTORY_OUTPUT = TESTING_FILES_RESOURCE_DIRECTORY + "response/";

	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
//    	System.out.println("Server running on port " + port);
		RestTemplateBuilder builder = new RestTemplateBuilder();
		restTemplate = new TestRestTemplate(builder);
	}

	@ParameterizedTest
	@MethodSource("jsonFiles")
	public void testCalculate(String jsonFileName) throws Exception {
		// Arrange
		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_INPUT + jsonFileName;
		ClassPathResource inputResource = new ClassPathResource(testFilePath);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ClassPathResource> requestEntity = new HttpEntity<>(inputResource, headers);
		List<ATM> expectedResult = readOutput(jsonFileName);

		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port)
				.path("/atms/calculateOrder").build();
		String requestUrl = uriComponents.toUriString();

		// Act
		long startTime = System.currentTimeMillis();
		for (int i=0; i<10; i++) {
			ResponseEntity<List<ATM>> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity,
					new ParameterizedTypeReference<List<ATM>>() {
					});
			
			// Assert
			assertEquals(expectedResult.size(), response.getBody().size());
//			assertEquals(expectedResult, response.getBody());
		}
		long endTime = System.currentTimeMillis();
		long requestTime = (endTime - startTime) / 10;
		System.out.println(jsonFileName + " request time: " + requestTime);

	}

	private List<ATM> readOutput(String jsonFileName) throws IOException, StreamReadException, DatabindException {
		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_OUTPUT + jsonFileName;
		testFilePath = testFilePath.replace("_request", "_response");
		ClassPathResource expectedResource = new ClassPathResource(testFilePath);
		ObjectMapper objectMapper = new ObjectMapper();
		List<ATM> expectedResult = objectMapper.readValue(expectedResource.getInputStream(),
				new TypeReference<List<ATM>>() {
				});
		return expectedResult;
	}

//	private List<Task> readInput(String jsonFileName) throws IOException, StreamReadException, DatabindException {
//		String testFilePath = TESTING_FILES_RESOURCE_DIRECTORY_INPUT + jsonFileName;
//		ClassPathResource inputResource = new ClassPathResource(testFilePath);
//        List<Task> tasks = objectMapper.readValue(inputResource.getInputStream(), new TypeReference<List<Task>>(){});
//		return tasks;
//	}

	static List<String> jsonFiles() throws IOException, URISyntaxException {
		URL resource = AtmsApiTest.class.getClassLoader().getResource(TESTING_FILES_RESOURCE_DIRECTORY_INPUT);
		Path inputDirectoryPath = Paths.get(resource.toURI());
		List<String> fileNames = Files.list(inputDirectoryPath)
				.filter(path -> Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".json"))
				.map(path -> path.getFileName().toString()).collect(Collectors.toList());
		return fileNames;
	}
}

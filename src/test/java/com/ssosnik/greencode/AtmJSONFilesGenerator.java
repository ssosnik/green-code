package com.ssosnik.greencode;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Task;
import com.ssosnik.greencode.service.AtmService;
import com.ssosnik.greencode.service.AtmServiceImpl;

public class AtmJSONFilesGenerator {
	private static final Random rand = new Random();

	private static final int MAX_REGION = 9999;
	private static final int MAX_ATM_ID = 9999;

	private static final ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT,
			false);

	private static final File ATM_SERVICE_FOLDER_INPUT = new File(
			"src/test/resources/testing-files/atmservice/request");
	private static final File ATM_SERVICE_FOLDER_OUTPUT = new File(
			"src/test/resources/testing-files/atmservice/response");

	public List<Task> createRandomTaskList(int size) {
		List<Task> taskList = new ArrayList<>();

		int maxRegion = Math.min((int) Math.sqrt(size) + 1, MAX_REGION);
		int maxATMId = Math.min((int) Math.sqrt(size) + 1, MAX_ATM_ID);

		for (int i = 0; i < size; i++) {
			Task task = new Task();
			task.setRegion(rand.nextInt(maxRegion) + 1);
			task.setRequestType(Task.RequestTypeEnum.values()[rand.nextInt(Task.RequestTypeEnum.values().length)]);
			task.setAtmId(rand.nextInt(maxATMId) + 1);
			taskList.add(task);
		}

		return taskList;
	}

	public void saveToJsonFile(Object object, File file) throws IOException {
		objectMapper.writeValue(file, object);
	}

	public static void main(String[] args) throws IOException {
		AtmJSONFilesGenerator generator = new AtmJSONFilesGenerator();

		for (int i = 2; i <= 6; i++) {
			BigInteger a = new BigInteger("10");
			BigInteger size = a.pow(i);
			long startTime = System.currentTimeMillis();
			List<Task> taskList = generator.createRandomTaskList(size.intValue());
			long endTime = System.currentTimeMillis();
			long elapsedTime1 = endTime - startTime;

			String filename = "atm" + size.toString() + ".json";
			generator.saveToJsonFile(taskList, new File(ATM_SERVICE_FOLDER_INPUT, filename));

			startTime = System.currentTimeMillis();
			AtmService atmService = new AtmServiceImpl();
			List<ATM> actualResult = atmService.calculateSortedATMList(taskList);
			endTime = System.currentTimeMillis();
			long elapsedTime2 = endTime - startTime;

			startTime = System.currentTimeMillis();
			generator.saveToJsonFile(actualResult, new File(ATM_SERVICE_FOLDER_OUTPUT, filename));
			endTime = System.currentTimeMillis();
			long elapsedTime3 = endTime - startTime;
			
			System.out.println(String.format("%s generation time: %d, %d, %d", filename, elapsedTime1, elapsedTime2, elapsedTime3));


		}
	}

}

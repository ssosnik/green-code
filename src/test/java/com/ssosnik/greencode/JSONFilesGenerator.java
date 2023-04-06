package com.ssosnik.greencode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ssosnik.greencode.model.Task;

public class JSONFilesGenerator {
	private static final Random rand = new Random();

    private static final int MAX_REGION = 9999;
    private static final int MAX_ATM_ID = 9999;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true);


    private static final File JSON_FILE = new File("tasks.json");

    public List<Task> createRandomTaskList(int size) {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Task task = new Task();
            task.setRegion(rand.nextInt(MAX_REGION) + 1);
            task.setRequestType(Task.RequestTypeEnum.values()[rand.nextInt(Task.RequestTypeEnum.values().length)]);
            task.setAtmId(rand.nextInt(MAX_ATM_ID) + 1);
            taskList.add(task);
        }
        
        return taskList;
    }

    public void saveTaskListToJsonFile(List<Task> taskList) throws IOException {
        objectMapper.writeValue(JSON_FILE, taskList);
    }

    public static void main(String[] args) throws IOException {
        JSONFilesGenerator generator = new JSONFilesGenerator();

        List<Task> taskList = generator.createRandomTaskList(10);
        generator.saveTaskListToJsonFile(taskList);
    }

}

package com.ssosnik.greencode.api;

import java.util.List;

import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Task;

import jakarta.validation.Valid;

public interface AtmService {

	List<ATM> calculateSortedATMList(@Valid List<Task> taskList);

}

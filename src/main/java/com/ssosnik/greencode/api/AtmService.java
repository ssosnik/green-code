package com.ssosnik.greencode.api;

import java.util.List;

import com.ssosnik.greencode.api.AtmServiceImpl.CalculateMethod;
import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Task;

public interface AtmService {

	List<ATM> calculateSortedATMList(List<Task> taskList);

	List<ATM> calculateSortedATMList(List<Task> taskList, CalculateMethod method);

}

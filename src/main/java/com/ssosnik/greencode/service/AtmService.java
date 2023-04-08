package com.ssosnik.greencode.service;

import java.util.List;

import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Task;
import com.ssosnik.greencode.service.AtmServiceImpl.CalculateMethod;

public interface AtmService {

	List<ATM> calculateSortedATMList(List<Task> taskList);

	List<ATM> calculateSortedATMList(List<Task> taskList, CalculateMethod method);

}

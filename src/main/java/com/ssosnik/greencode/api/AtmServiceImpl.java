package com.ssosnik.greencode.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Task;

import jakarta.validation.Valid;

@Service
public class AtmServiceImpl implements AtmService {
	private static Comparator<Task> comparator = Comparator.comparing(Task::getRegion)
			.thenComparing(Task::getRequestPriority);

	public enum CalculateMethod {
		Simple, Serial, Parallel,
	}

	@Override
	public List<ATM> calculateSortedATMList(List<Task> taskList) {

		List<ATM> combinedAtmList = taskList.size() <= 1000 ? simpleSolution(taskList) : parallelSoution(taskList);

		return combinedAtmList;
	}

	private List<ATM> simpleSolution(List<Task> taskList) {

		// Sort the input task list.
		Collections.sort(taskList, comparator);
		// Convert the sorted taskList to sorted ATM list and remove duplicates
		Set<ATM> usedATMs = new HashSet<>();
		List<ATM> combinedAtmList = new ArrayList<>();
		for (Task task : taskList) {
			ATM atm = new ATM(task.getRegion(), task.getAtmId());
			if (!usedATMs.contains(atm)) {
				combinedAtmList.add(atm);
				usedATMs.add(atm);
			}
		}

		return combinedAtmList;
	}

	private List<ATM> parallelSoution(List<Task> taskList) {
//		ConcurrentMap<Integer, ConcurrentMap<Integer, SortedSet<Integer>>> atmMap = taskList.parallelStream()
//				.collect(Collectors.groupingByConcurrent(Task::getRegion,
//						Collectors.groupingByConcurrent(Task::getRequestPriority,
//								Collectors.mapping(Task::getAtmId, Collectors.toCollection(TreeSet::new)))));

		Map<Integer, Map<Integer, List<Integer>>> atmMap = new HashMap<>();
		for (Task task : taskList) {
			int region = task.getRegion();
			int priority = task.getRequestType().ordinal();
			int atmId = task.getAtmId();

			atmMap.computeIfAbsent(region, r -> new HashMap<>()).computeIfAbsent(priority, p -> new ArrayList<>())
					.add(atmId);
		}

		ConcurrentMap<Integer, List<ATM>> atmListByRegion = new ConcurrentHashMap<>();

		atmMap.keySet().parallelStream().forEach(region -> {
			Set<Integer> usedAtmIds = new HashSet<Integer>();
			List<ATM> atmList = new ArrayList<>();
			atmListByRegion.put(region, atmList);
			Map<Integer, List<Integer>> atmPriorityMap = atmMap.get(region);
			List<Integer> keyList = new ArrayList<>(atmPriorityMap.keySet());
			Collections.sort(keyList);
			for (Integer key : keyList) {
				for (Integer atmId : atmPriorityMap.get(key)) {
					if (!usedAtmIds.contains(atmId)) {
						usedAtmIds.add(atmId);
						atmList.add(new ATM(region, atmId));
					}
				}
			}
		});

//		List<Integer> regionList = new ArrayList<>(atmMap.keySet());
//		Collections.sort(regionList);

		// Sort the map by key
		Map<Integer, List<ATM>> sortedMap = new TreeMap<>(atmListByRegion);

		List<ATM> combinedAtmList = new ArrayList<>();
		for (List<ATM> atms : sortedMap.values()) {
			combinedAtmList.addAll(atms);
		}
		return combinedAtmList;
	}

	@Override
	public List<ATM> calculateSortedATMList(@Valid List<Task> taskList, CalculateMethod method) {
		List<ATM> combinedAtmList = method == CalculateMethod.Simple ? simpleSolution(taskList)
				: method == CalculateMethod.Serial ? serialSoution(taskList) : parallelSoution(taskList);

		return combinedAtmList;
	}

	private List<ATM> serialSoution(@Valid List<Task> taskList) {
		Map<Integer, Map<Integer, List<Integer>>> atmMap = new HashMap<>();
		for (Task task : taskList) {
			int region = task.getRegion();
			int priority = task.getRequestType().ordinal();
			int atmId = task.getAtmId();

			atmMap.computeIfAbsent(region, r -> new HashMap<>()).computeIfAbsent(priority, p -> new ArrayList<>())
					.add(atmId);
		}

		Map<Integer, List<ATM>> atmListByRegion = new HashMap<>();

		atmMap.keySet().stream().forEach(region -> {
			Set<Integer> usedAtmIds = new HashSet<Integer>();
			List<ATM> atmList = new ArrayList<>();
			atmListByRegion.put(region, atmList);
			Map<Integer, List<Integer>> atmPriorityMap = atmMap.get(region);
			List<Integer> keyList = new ArrayList<>(atmPriorityMap.keySet());
			Collections.sort(keyList);
			for (Integer key : keyList) {
				for (Integer atmId : atmPriorityMap.get(key)) {
					if (!usedAtmIds.contains(atmId)) {
						usedAtmIds.add(atmId);
						atmList.add(new ATM(region, atmId));
					}
				}
			}
		});

		Map<Integer, List<ATM>> sortedMap = new TreeMap<>(atmListByRegion);
		List<ATM> combinedAtmList = new ArrayList<>();
		for (List<ATM> atms : sortedMap.values()) {
			combinedAtmList.addAll(atms);
		}
		return combinedAtmList;
	}
}
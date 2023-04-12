package com.ssosnik.greencode.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ssosnik.greencode.model.Clan;
import com.ssosnik.greencode.model.Players;

@Service
public class OnlineGameServiceImpl implements OnlineGameService {
	private static Comparator<Clan> comparator = Comparator.comparing(Clan::getPoints).reversed()
			.thenComparing(Clan::getNumberOfPlayers);


	public enum CalculateMethod {
		Simple, Optimized;
	}

	@Override
	public List<List<Clan>> calculateClanList(Players players) {
		List<List<Clan>> result = simpleSolution(players);

		return result;
	}
	
	@Override
	public List<List<Clan>> calculateClanList(Players players, CalculateMethod method) {
		List<List<Clan>>  combinedAtmList = method == CalculateMethod.Simple ? simpleSolution(players)
				: optimizedSolution(players);

		return combinedAtmList;
	}

	private List<List<Clan>> simpleSolution(Players players) {
		Integer groupSize = players.getGroupCount();
		List<Clan> clans = players.getClans();

		clans.sort(comparator);

		List<List<Clan>> result = new ArrayList<>();
		Set<Integer> usedClans = new HashSet<>();

		Integer currentPlayerCount = 0;
		for (Integer i = 0; i < clans.size(); i++) {
			if (usedClans.contains(i))
				continue;

			List<Clan> group = new ArrayList<>();
			group.add(clans.get(i));
			result.add(group);
			currentPlayerCount = clans.get(i).getNumberOfPlayers();

			for (Integer j = i + 1; j < clans.size(); j++) {
				if (currentPlayerCount.equals(groupSize)) {
					break;
				}
				if (usedClans.contains(j))
					continue;
				Clan clan = clans.get(j);
				if (clan.getNumberOfPlayers() + currentPlayerCount > groupSize)
					continue;

				currentPlayerCount += clan.getNumberOfPlayers();
				group.add(clan);
				usedClans.add(j);

			}

		}
		return result;
	}
	
	private List<List<Clan>> optimizedSolution(Players players) {
		List<List<Clan>> result = new ArrayList<>();

		Integer groupSize = players.getGroupCount();
		
//		Integer clanSize_L = groupSize < 16 ? 3 : (int) Math.sqrt(groupSize); 
//		Integer clanSize_M = groupSize < 16 ? 2 : (int) Math.sqrt(clanSize_L); 
//		Integer clanSize_S = (int) Math.sqrt(clanSize_M);

		Integer clanSize_M = groupSize < 16 ? 3 : (int) Math.sqrt(groupSize); 
		Integer clanSize_S = groupSize < 16 ? 2 : (int) Math.sqrt(clanSize_M); 

		
		List<Clan> clans = players.getClans();

		clans.sort(comparator);

		LinkedList<Integer> clanList_S = new LinkedList<>();
		LinkedList<Integer> clanList_M = new LinkedList<>();
//		LinkedList<Integer> clanList_L = new LinkedList<>();
		Set<Integer> usedClans = new HashSet<>();
		
		for (Integer i = 0; i < clans.size(); i++) {
			Clan clan = clans.get(i);
			if (clan.getNumberOfPlayers() <= clanSize_S) {
				clanList_S.add(i);				
			} else if (clan.getNumberOfPlayers() <= clanSize_M) {
				clanList_M.add(i);				
//			} else if (clan.getNumberOfPlayers() <= clanSize_L) {
//				clanList_L.add(i);								
			}
		}

		Integer currentPlayerCount = 0;
		for (Integer i = 0; i < clans.size(); i++) {
			if (usedClans.contains(i))
				continue;
			usedClans.add(i);

			List<Clan> group = new ArrayList<>();
			group.add(clans.get(i));
			result.add(group);
			currentPlayerCount = clans.get(i).getNumberOfPlayers();
			
			if (currentPlayerCount <= clanSize_S) {
				clanList_S.removeFirst();
			} else if (currentPlayerCount <= clanSize_M) {	
			   	clanList_M.removeFirst();
			}
			
			Integer j = i + 1; 
			while (j < clans.size()) {
				if (currentPlayerCount.equals(groupSize)) {
//					currentPlayerCount = 0;
					break;
				}
				Integer currentFreePlacesCount = groupSize - currentPlayerCount;

				if (currentFreePlacesCount <= clanSize_S) {
				    j = findGroupIndex(clans, clanList_S, currentFreePlacesCount, clans.size());
				} else if (currentFreePlacesCount <= clanSize_M) {	
					Integer stopIndex = clanList_S.size() > 0 ? clanList_S.getFirst() : clans.size(); 
				    j = findGroupIndex(clans, clanList_M, currentFreePlacesCount, stopIndex);
				    if (j==stopIndex && j<clans.size()) {
				    	// clan index was found in S-list, so remove it
				    	clanList_S.removeFirst();
				    }
				    	
				} else {
					j = findGroupIndex(clans, usedClans, j, currentFreePlacesCount);
				    if (j<clans.size()) {	
						if (clans.get(j).getNumberOfPlayers() <= clanSize_S) {
							clanList_S.removeFirst();
						} else if (clans.get(j).getNumberOfPlayers() <= clanSize_M) {	
						   	clanList_M.removeFirst();
						}
				    }
				}

				if (j<clans.size()) {
					Clan clan = clans.get(j);
					currentPlayerCount += clan.getNumberOfPlayers();
					group.add(clan);
					usedClans.add(j);
				}
			}

		}
		return result;
	}

	private Integer findGroupIndex(List<Clan> clans, Set<Integer> usedClans, Integer startIndex,
			Integer currentFreePlacesCount) {
		for (Integer k=startIndex; k<clans.size();k++) {
			if (usedClans.contains(k))
				continue;
			Clan clan = clans.get(k);
			if (clan.getNumberOfPlayers() <= currentFreePlacesCount) {
				return k;
			}
		}
		return clans.size();
	}

	private Integer findGroupIndex(List<Clan> clans, List<Integer> clanIndexList, Integer currentFreePlacesCount, Integer maxIndex) {
		Integer resultIndex = maxIndex;
		Iterator<Integer> iterator = clanIndexList.iterator();
		while (iterator.hasNext()) {
		    Integer index = iterator.next();
		    if (index > maxIndex)
		    	break;
		    if (clans.get(index).getNumberOfPlayers() <= currentFreePlacesCount) {
		    	resultIndex = index;
		    	iterator.remove();
		        break;
		    }											
		}
		return resultIndex;
	}

}
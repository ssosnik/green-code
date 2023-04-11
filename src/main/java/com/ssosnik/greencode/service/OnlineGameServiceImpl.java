package com.ssosnik.greencode.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
		Simple, Serial, Parallel,
	}

	@Override
	public List<List<Clan>> calculateClanList(Players players) {
		List<List<Clan>> result = simpleSolution(players);

		return result;
	}
	
	@Override
	public List<List<Clan>> calculateClanList(Players players, CalculateMethod method) {
		List<List<Clan>>  combinedAtmList = method == CalculateMethod.Simple ? simpleSolution(players)
				: serialSoution(players);

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
	
	private List<List<Clan>> serialSoution(Players players) {
		List<List<Clan>> result = new ArrayList<>();

		Integer groupSize = players.getGroupCount();
		
//		Integer clanSize_L = groupSize < 16 ? 3 : (int) Math.sqrt(groupSize); 
//		Integer clanSize_M = groupSize < 16 ? 2 : (int) Math.sqrt(clanSize_L); 
//		Integer clanSize_S = (int) Math.sqrt(clanSize_M);

		Integer clanSize_M = groupSize < 16 ? 3 : (int) Math.sqrt(groupSize); 
		Integer clanSize_S = groupSize < 16 ? 2 : (int) Math.sqrt(clanSize_M); 

		
		List<Clan> clans = players.getClans();

		clans.sort(comparator);

		List<Integer> clanList_S = new LinkedList<>();
		List<Integer> clanList_M = new LinkedList<>();
//		List<Integer> clanList_L = new LinkedList<>();
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
			
			Integer j = i + 1; 
			while (j < clans.size()) {
				if (currentPlayerCount.equals(groupSize)) {
//					currentPlayerCount = 0;
					break;
				}
				Integer currentFreePlacesCount = groupSize - currentPlayerCount;

				if (currentFreePlacesCount <= clanSize_S) {
					if (clanList_S.isEmpty())
						break;
					j = clanList_S.iterator().next();
				} else if (currentFreePlacesCount <= clanSize_M) {
					if (clanList_S.isEmpty() && clanList_M.isEmpty())
						break;
					
				} else {
					j++;
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

}
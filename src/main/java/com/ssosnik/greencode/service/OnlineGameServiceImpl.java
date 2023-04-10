package com.ssosnik.greencode.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
		Integer groupSize = players.getGroupCount();
		List<Clan> clans = players.getClans();

		clans.sort(comparator);

		List<List<Clan>> result = new ArrayList<>();
		Set<Integer> usedClans = new HashSet<>();

		Integer currentPlayerCount = 0;
		for (Integer i = 0; i < clans.size(); i++) {
			if (usedClans.contains(i))
				continue;
			if (clans.get(i).getNumberOfPlayers() > groupSize)
				continue;

			List<Clan> group = new ArrayList<>();
			group.add(clans.get(i));
			result.add(group);
			currentPlayerCount = clans.get(i).getNumberOfPlayers();

			for (Integer j = i + 1; j < clans.size(); j++) {
				if (usedClans.contains(j))
					continue;
				if (currentPlayerCount.equals(groupSize)) {
//					currentPlayerCount = 0;
					break;
				}
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
package com.ssosnik.greencode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ssosnik.greencode.model.Clan;
import com.ssosnik.greencode.model.Players;
import com.ssosnik.greencode.service.OnlineGameService;
import com.ssosnik.greencode.service.OnlineGameServiceImpl;
import com.ssosnik.greencode.service.OnlineGameServiceImpl.CalculateMethod;

public class OnlineGameJSONFilesGenerator {
	private static final Random rand = new Random();

//	private static final int MAX_GROUP_SIZE = 1000;	
//	private static final int MAX_CLAN_LIST = 20000;
	private static final int MAX_SCORE = 100000;

	private static final ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT,
			false);

	private static final File ONLINEGAME_SERVICE_FOLDER_INPUT = new File(
			"src/test/resources/testing-files/onlinegame/request");
	private static final File ONLINEGAME_SERVICE_FOLDER_OUTPUT = new File(
			"src/test/resources/testing-files/onlinegame/response");

	public Players createRandomClanList(int clanCount) {
		Players players = new Players();
		
		int groupSize = Math.max(10, clanCount/40);
		players.setGroupCount(groupSize);

		for (int i = 0; i < clanCount; i++) {
			Clan clan = new Clan();
//			clan.setNumberOfPlayers(rand.nextInt(1, groupSize/2));			
//			clan.setNumberOfPlayers(rand.nextInt(groupSize/2, groupSize));			
			clan.setNumberOfPlayers(rand.nextInt(1, groupSize));			
			clan.setPoints(rand.nextInt(clan.getNumberOfPlayers(), MAX_SCORE)/clan.getNumberOfPlayers());
			
			players.addClansItem(clan);
		}

		return players;
	}

	public void saveToJsonFile(Object object, File file) throws IOException {
		objectMapper.writeValue(file, object);
	}

	public static void main(String[] args) throws IOException {
		OnlineGameJSONFilesGenerator generator = new OnlineGameJSONFilesGenerator();

		for (int i = 1; i <= 20; i++) {
			Integer size = i*1000;
			
			long startTime = System.currentTimeMillis();
			Players players = generator.createRandomClanList(size.intValue());
			long endTime = System.currentTimeMillis();
			long elapsedTime1 = endTime - startTime;

			String filename = String.format("players%05d.json", size);
			generator.saveToJsonFile(players, new File(ONLINEGAME_SERVICE_FOLDER_INPUT, filename));

			startTime = System.currentTimeMillis();
			OnlineGameService onlineGameService = new OnlineGameServiceImpl();
			List<List<Clan>> actualResult = onlineGameService.calculateClanList(players, CalculateMethod.Optimized);
			endTime = System.currentTimeMillis();
			long elapsedTime2 = endTime - startTime;

			startTime = System.currentTimeMillis();
			actualResult = onlineGameService.calculateClanList(players, CalculateMethod.Simple);
			endTime = System.currentTimeMillis();
			long elapsedTime3 = endTime - startTime;

			
			startTime = System.currentTimeMillis();			
			generator.saveToJsonFile(actualResult, new File(ONLINEGAME_SERVICE_FOLDER_OUTPUT, filename));
			endTime = System.currentTimeMillis();
			long elapsedTime4 = endTime - startTime;
			
			System.out.println(String.format("%s generation time: %d, %d, %d, %d", filename, elapsedTime1, elapsedTime2, elapsedTime3, elapsedTime4));

		}
	}

}

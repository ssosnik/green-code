package com.ssosnik.greencode.service;

import java.util.List;

import com.ssosnik.greencode.model.Clan;
import com.ssosnik.greencode.model.Players;
import com.ssosnik.greencode.service.OnlineGameServiceImpl.CalculateMethod;

public interface OnlineGameService {

	List<List<Clan>> calculateClanList(Players players);

	List<List<Clan>> calculateClanList(Players players, CalculateMethod method);

}

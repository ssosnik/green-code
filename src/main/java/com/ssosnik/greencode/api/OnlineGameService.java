package com.ssosnik.greencode.api;

import java.util.List;

import com.ssosnik.greencode.model.Clan;
import com.ssosnik.greencode.model.Players;

public interface OnlineGameService {

	List<List<Clan>> calculateClanList(Players players);

}

package com.ssosnik.greencode.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssosnik.greencode.model.Clan;
import com.ssosnik.greencode.model.Players;

@Service
public class OnlineGameServiceImpl implements OnlineGameService {

	@Override
	public List<List<Clan>> calculateClanList(Players players) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}
}
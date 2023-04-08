package com.ssosnik.greencode.api;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssosnik.greencode.model.Clan;
import com.ssosnik.greencode.model.Players;
import com.ssosnik.greencode.service.OnlineGameService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:14:58.930237600+02:00[Europe/Warsaw]")
@Controller
@RequestMapping("${openapi.onlineGameTask.base-path:}")
public class OnlinegameApiController implements OnlinegameApi {

	@Autowired
	private OnlineGameService onlineGameService;

	@Override
	public ResponseEntity<List<List<Clan>>> calculate(
			@Parameter(name = "Players", description = "", required = true) @Valid @RequestBody Players players) {
	    List<List<Clan>> clanList = onlineGameService.calculateClanList(players);
	    return ResponseEntity.ok(clanList);
	}

}

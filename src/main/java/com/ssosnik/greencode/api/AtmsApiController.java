package com.ssosnik.greencode.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssosnik.greencode.model.ATM;
import com.ssosnik.greencode.model.Task;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T15:51:15.919232900+02:00[Europe/Warsaw]")
@Controller
@RequestMapping("${openapi.aTMsServiceTeamTask.base-path:}")
public class AtmsApiController implements AtmsApi {
	
	@Autowired
    private AtmService atmService;

	@Override
	public ResponseEntity<List<ATM>> calculate(@Parameter(name = "Task", description = "", required = true) @Valid @RequestBody List<Task> taskList) {
	    List<ATM> atmList = atmService.calculateSortedATMList(taskList);
	    return ResponseEntity.ok(atmList);
	}

}

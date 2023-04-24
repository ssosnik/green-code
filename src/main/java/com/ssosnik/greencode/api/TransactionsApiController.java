package com.ssosnik.greencode.api;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssosnik.greencode.model.AccountInterface;
import com.ssosnik.greencode.model.Transaction;
import com.ssosnik.greencode.service.TransactionService;

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:15:24.122895800+02:00[Europe/Warsaw]")
@Controller
@RequestMapping("${openapi.transactionsTask.base-path:}")
public class TransactionsApiController implements TransactionsApi {

	@Autowired
	private TransactionService transactionService;

	@Override
	public ResponseEntity<List<AccountInterface>> report(List<Transaction> transaction) {
		List<AccountInterface> accountList = transactionService.calculateAccountList(transaction);
		return ResponseEntity.ok(accountList);
	}

}

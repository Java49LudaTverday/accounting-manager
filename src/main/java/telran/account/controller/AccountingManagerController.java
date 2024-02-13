package telran.account.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.account.dto.AccountDto;
import telran.account.service.AccountingManagerService;

@RestController
@RequiredArgsConstructor
public class AccountingManagerController {
	final AccountingManagerService accountingService;
	
	@PostMapping
	AccountDto addAccount(AccountDto account) {
		//TODO
		return  accountingService.addAccount(account);
	}
	
	@DeleteMapping
	AccountDto removeAccount(String email) {
		//TODO
		return accountingService.removeAccount(email);
	}

}

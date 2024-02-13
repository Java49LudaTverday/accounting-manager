package telran.account.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import telran.account.dto.AccountDto;
import telran.account.service.AccountingManagerService;
import static telran.account.api.AccountManagerConfiguration.*;
import static telran.account.api.AccountManagerMessages.*;

@RestController
@RequestMapping(REQUEST_MAPPING)
@RequiredArgsConstructor
public class AccountingManagerController {
	final AccountingManagerService accountingService;
	
	@PostMapping
	AccountDto addAccount(@RequestBody @Valid AccountDto account) {
		//TODO
		return  accountingService.addAccount(account);
	}
	
	@DeleteMapping
	AccountDto removeAccount(@RequestParam(name = PARAM_NAME_REMOVE ) @NotNull(message=NOT_NULL_MESSAGE)
	@Email(message=WRONG_EMAIL_MESSAGE) String email) {
		//TODO
		return accountingService.removeAccount(email);
	}

}

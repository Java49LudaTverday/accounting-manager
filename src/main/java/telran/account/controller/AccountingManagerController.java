package telran.account.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.account.service.AccountingManagerService;
import telran.probes.dto.AccountDto;

import static telran.account.api.AccountManagerConfiguration.*;
import static telran.account.api.AccountManagerMessages.*;

@RestController
@RequestMapping(REQUEST_MAPPING)
@Validated
@RequiredArgsConstructor
@Slf4j
public class AccountingManagerController {
	final AccountingManagerService accountingService;
	
	@PostMapping
	public AccountDto addAccount(@RequestBody @Valid AccountDto account) {
		log.debug("account {} has been received", account);
		return accountingService.addAccount(account);
	}

	@DeleteMapping("{" + PARAM_NAME_REMOVE + "}")
	public AccountDto removeAccount(@PathVariable(name = PARAM_NAME_REMOVE)
			@NotNull(message=NOT_NULL_MESSAGE) @Email(message=WRONG_EMAIL_MESSAGE)  String email) {
		log.debug("email {} was received", email);
		return accountingService.removeAccount(email);
	}

}

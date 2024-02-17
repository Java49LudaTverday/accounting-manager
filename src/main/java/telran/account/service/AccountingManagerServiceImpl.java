package telran.account.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.account.exceptions.AccountAlreadyExistsException;
import telran.account.model.Account;
import telran.account.repo.AccountRepo;
import telran.probes.dto.AccountDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountingManagerServiceImpl implements AccountingManagerService {
	final AccountRepo accountRepo;
	final PasswordEncoder passwordEncoder;

	@Override
	public AccountDto addAccount(AccountDto accountDto) {
		String email = accountDto.email();
		AccountDto accountEncoded = getAcocuntDtoEncoded(accountDto);
		if(accountRepo.existsById(email)) {
			throw new AccountAlreadyExistsException(); 
		}
		Account account = accountRepo.save(new Account(accountEncoded));
		log.debug("account {} has been saved", account);
		return account.buildDto();
	}

	private AccountDto getAcocuntDtoEncoded(AccountDto account) {
		
		return new AccountDto(account.email(), passwordEncoder.encode(account.password()), account.roles());
	}

	@Override
	public AccountDto removeAccount(String email) {
		AccountDto res = null;
		if(accountRepo.existsById(email)) {
			Account account = accountRepo.findById(email).get();
			log.debug("account {} has been found", account);
			res = account.buildDto();
			accountRepo.delete(account);
			log.debug("account with id {} has been removed", email);
		}				
		return res;
	}
	

}

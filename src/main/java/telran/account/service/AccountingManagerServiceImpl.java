package telran.account.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.account.dto.AccountDto;
import telran.account.exceptions.AccountAlreadyExistsException;
import telran.account.model.Account;
import telran.account.repo.AccountRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountingManagerServiceImpl implements AccountingManagerService {
	final AccountRepo accountRepo;

	@Override
	public AccountDto addAccount(AccountDto account) {
		String email = account.email();
		if(accountRepo.existsById(email)) {
			throw new AccountAlreadyExistsException(); 
		}
		accountRepo.save(new Account(account));
		log.debug("account {} has been saved", account);
		return account;
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

package telran.account.service;

import telran.account.dto.AccountDto;

public interface AccountingManagerService {
//	1.1.2.1.
//	AccountDto addAccount(AccountDto account) adds account
//	and returns AccountDto with asterisks as the password content
	AccountDto addAccount(AccountDto account);
//	1.1.2.2.
//	AccountDto removeAccount(String email) removes account and
//	returns AccountDto with asterisks as the password content
	AccountDto removeAccount(String email);
}

package telran.account;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.account.exceptions.AccountAlreadyExistsException;
import telran.account.model.Account;
import telran.account.repo.AccountRepo;
import telran.account.service.AccountingManagerService;
import telran.probes.dto.AccountDto;

@SpringBootTest
@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
class AccountManagerServiceTest {
	
	static final List<Account> initialAcounts = List.of(
			new Account("email1@gmail.com", "12345677", new String[] {"role1"}),
			new Account("email2@gmail.com", "12345678", new String[] {"role2"}),
			new Account("email3@gmail.com", "12345679", new String[] {"role1"}));

	static final String EMAIL_ID_4 = "email4@gmail.com";
	static final String PASSWORD_1 = "12345688";
	static final String[] ROLES_1 = new String[]{"role2"};
	public AccountDto NEW_ACCOUNT_DTO = new AccountDto(EMAIL_ID_4, PASSWORD_1, ROLES_1);

	@Autowired
	AccountingManagerService accountingManager;	
	@Autowired
	AccountRepo accountRepo;
	
	@SuppressWarnings("unchecked")
	@Test
	@Order(1)
	void initialDb () {
		accountRepo.deleteAll();
		accountRepo.saveAll(initialAcounts);
	}
	
	@Test
	@Order(2)
	void addAccount_whenNormalFlow_thenReturnAccountDto() {
		AccountDto expected = accountingManager.addAccount(NEW_ACCOUNT_DTO);
		Account actual = accountRepo.findById(EMAIL_ID_4).orElse(null);
		AccountDto actualDto = actual.buildDto();
		assertEquals(expected.email(), actualDto.email() );
		assertEquals(expected.password(), actualDto.password() );
	}
	
	@Test
	@Order(3)
	void addAccount_whenEmailNotExists_thenThrowAlreadyExistsException() {
		assertThrowsExactly(AccountAlreadyExistsException.class,
				() -> accountingManager.addAccount(NEW_ACCOUNT_DTO));
	}
	
	@Test 
	@Order(4)
	void removeAccount_whenNormalFlow_thenReturnAccountDto () {
		AccountDto actual = accountingManager.removeAccount(EMAIL_ID_4);
		assertEquals(actual.email(), NEW_ACCOUNT_DTO.email());
	    assertNull(accountRepo.findById(EMAIL_ID_4).orElse(null));
	}
	
	@Test 
	@Order(5)
	void deleteAccount_whenEmailNotExists_thenReturnNull () {
		assertNull(accountingManager.removeAccount(EMAIL_ID_4));
	}

}

package telran.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.account.dto.AccountDto;
import telran.account.service.AccountingManagerService;
import static telran.account.api.AccountManagerConfiguration.*;
import static telran.account.api.AccountManagerMessages.*;



@WebMvcTest
class AccountingManagerController {
	private static final String EMAIL_ID = "email1@gmail.com";
	private static final AccountDto NORMAL_ACCOUNT_DTO = new AccountDto(EMAIL_ID, "12345", new String[] {"role1"});
	private static final AccountDto WRONG_ACCOUNT_DTO = new AccountDto("email", "", new String[] {});
	private static final String URL = "http://localhost:8090" + REQUEST_MAPPING;
	private static final String WRONG_EMAIL_ID_FORMAT = "wrong_email_format";
	private static final String NULL_EMAIL_ID = null;
	
	@Autowired
	MockMvc mockMvc;
	@MockBean
	AccountingManagerService accountingService; 
	@Autowired
	ObjectMapper mapper;

	@Test
	void addAccount_whenNormalFlow_thenReturnResponse() throws Exception {
		when(accountingService.addAccount(NORMAL_ACCOUNT_DTO)).thenReturn(NORMAL_ACCOUNT_DTO);
		String accountDtoJson = mapper.writeValueAsString(NORMAL_ACCOUNT_DTO);
		String actualResponse = mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				.content(accountDtoJson)).andExpect(status()
				.isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(accountDtoJson, actualResponse);
	}
	
	@Test
	void addAccount_whenWrongFields_thenThrowInvalidException() throws Exception {
		String wrongAccountDtoJson = mapper.writeValueAsString(WRONG_ACCOUNT_DTO);
		String actualResponse = mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				.content(wrongAccountDtoJson)).andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals(actualResponse, String.format("%s,%s,%s", WRONG_EMAIL_MESSAGE, 
				PASSWORD_SIZE_MESSAGE, EMPTY_ROLES_MESSAGE));
	}
	
	@Test
	void removeAccount_whenNormalFlow_thenReturnResponse() throws Exception {
		when(accountingService.removeAccount(EMAIL_ID)).thenReturn(NORMAL_ACCOUNT_DTO);
		String accountJson = mapper.writeValueAsString(NORMAL_ACCOUNT_DTO);
		String actualResponse = mockMvc.perform(delete(URL).param(EMAIL_ID)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals(accountJson, actualResponse);
	}
	
	@Test
	void removeAccount_whenFormatParam_thenThrowInvalidException() throws Exception {
		String actualResponse = mockMvc.perform(post(URL).param(WRONG_EMAIL_ID_FORMAT)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_EMAIL_MESSAGE, actualResponse);
	}
	
	@Test
	void removeAccount_whenNullParam_thenThrowInvalidException() throws Exception {
		String actualResponse = mockMvc.perform(post(URL).param(NULL_EMAIL_ID)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals(NOT_NULL_MESSAGE , actualResponse);
	}

}

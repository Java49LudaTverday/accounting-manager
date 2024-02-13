package telran.account.exceptions;

import static telran.account.api.AccountManagerMessages.*;

public class AccountAlreadyExistsException extends IllegalStateException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException() {
		super(ACCOUNT_ALREADY_EXISTS_MESSAGE);
	}

}

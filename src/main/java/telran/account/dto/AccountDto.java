package telran.account.dto;

import jakarta.validation.constraints.*;
import static telran.account.api.AccountManagerMessages.*;

import java.io.Serializable;

import static telran.account.api.AccountManagerConfiguration.*;

public record AccountDto(@NotEmpty @Email(message = WRONG_EMAIL_MESSAGE) String email, 
		@NotNull @Size(min=MIN_PASSWORD_SIZE, message = PASSWORD_SIZE_MESSAGE) String password, 
		@NotEmpty(message = EMPTY_ROLES_MESSAGE) String[] roles) implements Serializable{

}

package telran.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {
	@Id
	String email;
	String hashPassword;
	String[] roles;
}

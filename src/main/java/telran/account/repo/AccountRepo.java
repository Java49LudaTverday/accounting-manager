package telran.account.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import telran.account.model.Account;

public interface AccountRepo extends MongoRepository<Account, String> {
	
}

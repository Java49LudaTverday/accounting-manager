package telran.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"telran"})
public class AccountingManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingManagerApplication.class, args);
	}

}

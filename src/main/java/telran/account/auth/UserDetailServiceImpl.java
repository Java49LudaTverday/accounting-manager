package telran.account.auth;

import java.util.Arrays;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.account.model.Account;
import telran.account.repo.AccountRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	final AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepo.findById(username).orElseThrow(() -> 
		new UsernameNotFoundException(""));
		String[] roles = Arrays.stream(account.getRoles()).map(r -> "ROLE_" + r).toArray(String[]::new);
		return new User(account.getEmail(), account.getHashPassword(), AuthorityUtils.createAuthorityList(roles));
	}

}

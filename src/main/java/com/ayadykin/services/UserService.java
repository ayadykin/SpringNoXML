package com.ayadykin.services;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ayadykin.hibernate.AccountDao;
import com.ayadykin.model.Account;

public class UserService implements UserDetailsService {

	@Autowired
	private AccountDao accountRepository;

	@PostConstruct
	public void initialize() {
		accountRepository.addAccount(new Account("user", "demo", "ROLE_USER"));
		accountRepository.addAccount(new Account("admin", "admin", "ROLE_ADMIN"));
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if (account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

	public void signin(Account account) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(createUser(account), null,
				Collections.singleton(createAuthority(account)));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(),
				Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}

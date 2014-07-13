package com.ayadykin.hibernate;

import com.ayadykin.model.Account;

/**
 * @author ayadykin
 */
public interface AccountDao{
	public Account addAccount(Account account);
	public Account findByEmail(String email);
}

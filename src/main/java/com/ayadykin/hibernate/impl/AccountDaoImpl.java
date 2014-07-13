package com.ayadykin.hibernate.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ayadykin.hibernate.AccountDao;
import com.ayadykin.model.Account;

/**
 * @author ayadykin
 */
@Repository("accountDao")
@Transactional(readOnly = true)
public class AccountDaoImpl extends GenericDaoHibernate<Account, Long> implements AccountDao {

	@Inject
	private PasswordEncoder passwordEncoder;
	@PersistenceContext
	private EntityManager entityManager;

	public AccountDaoImpl() {
		super(Account.class);
	}

	@Override
	public Account addAccount(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		super.save(account);
		return account;
	}

	@Override
	public Account findByEmail(String email) {
		try {
			return entityManager.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
					.setParameter("email", email).getSingleResult();

		} catch (PersistenceException e) {
			//MessageHelper.addErrorAttribute(model, "Error", args);
			return null;
		}
	}

}

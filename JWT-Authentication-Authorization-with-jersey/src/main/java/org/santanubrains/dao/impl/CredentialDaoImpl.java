package org.santanubrains.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.santanubrains.dao.interfaces.CredentialDao;
import org.santanubrains.domain.Credential;
import org.santanubrains.domain.User;
import org.santanubrains.utiliy.PasswordUtils;

public class CredentialDaoImpl extends AbstractDao<User, Long> implements CredentialDao {

	@Override
	public boolean authenticate(Credential credential) {

		User user = findByCriteriaCondition(Restrictions.eq("emailId", credential.getUsername()),
				Restrictions.eq("password", PasswordUtils.digestPassword(credential.getPassword())));
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> getRolesByEmailId(String emailId) {

		User user = findByCriteriaCondition(Restrictions.eq("emailId", emailId));

		return user.getRoles();
	}

}

package org.santanubrains.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.santanubrains.dao.interfaces.UserDao;
import org.santanubrains.domain.User;

public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

	@Override
	public User createUser(User user) {

		return save(user);
	}

	@Override
	public List<User> findByLastName(String lastName) {

		return findByCriteria(Restrictions.eq("lastName", lastName));
	}

	@Override
	public boolean checkEmailId(User user) {

		User u = findByCriteriaCondition(Restrictions.like("emailId", user.getEmailId()));
		if (u != null) {
			return false;
		}
		return true;
	}

	@Override
	public User findUserById(Long id) {
		return findById(id);
	}

	@Override
	public User updateUser(User user) {
		
		return save(user);
	}

}

package org.santanubrains.dao.interfaces;

import java.util.List;

import org.santanubrains.dao.interfaces.Dao;
import org.santanubrains.domain.User;

public interface UserDao extends Dao<User, Long> {

	public User createUser(User user);

	public List<User> findByLastName(String lastName);
	
	public boolean checkEmailId(User user);
	
	public User findUserById(Long id);
	
	public User updateUser(User user);

}

package org.santanubrains.service;

import java.util.List;

import javax.inject.Inject;

import org.santanubrains.dataAdapter.UserDataAdapter;
import org.santanubrains.domain.User;
import org.santanubrains.response.Response;

import io.reactivex.Single;

public class UserServiceImpl implements UserService {

	private UserDataAdapter userDataAdapter;

	@Inject
	public UserServiceImpl(UserDataAdapter userDataAdapter) {
		super();
		this.userDataAdapter = userDataAdapter;
	}

	@Override
	public Single<Response> createUser(User user) {

		return userDataAdapter.createUser(user);
	}

	@Override
	public Single<List<User>> findByLastName(String lastName) {

		return userDataAdapter.findByLastName(lastName);
	}

	@Override
	public Single<List<User>> findAllUser() {

		return userDataAdapter.findAllUser();
	}

	@Override
	public Single<User> findById(Long id) {

		return userDataAdapter.findUserById(id);
	}

	@Override
	public Single<Response> updateUser(Long userId, User user) {
		
		return userDataAdapter.updateUser(userId, user);
	}

	@Override
	public Single<Response> deleteUser(Long userId) {
		
		return userDataAdapter.deleteUser(userId);
	}

}

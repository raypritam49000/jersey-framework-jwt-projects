package org.santanubrains.service;

import java.util.List;

import org.santanubrains.domain.User;
import org.santanubrains.response.Response;

import io.reactivex.Single;

public interface UserService {

	public Single<Response> createUser(User user);
	
	public Single<List<User>> findByLastName(String lastName);
	
	public Single<List<User>> findAllUser();
	
	public Single<User> findById(Long id);
	
	public Single<Response> updateUser(Long userId, User user);
	
	public Single<Response> deleteUser(Long userId);
}

package org.santanubrains.dataAdapter;

import java.util.List;

import org.santanubrains.domain.User;
import org.santanubrains.response.Response;

import io.reactivex.Single;

public interface UserDataAdapter {

	public Single<Response> createUser(User user);
	
	public Single<List<User>> findByLastName(String lastName);
	
	public Single<List<User>> findAllUser();
	
	public Single<User> findUserById(Long id);
	
	public Single<Response> updateUser(Long userId, User user);
	
	public Single<Response> deleteUser(Long userId);
}

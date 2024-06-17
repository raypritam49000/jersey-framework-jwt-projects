package org.santanubrains.dao.interfaces;

import java.util.List;

import org.santanubrains.domain.Credential;
import org.santanubrains.domain.User;

public interface CredentialDao extends Dao<User, Long>{

	public boolean authenticate(Credential credential);
	
	public List<String> getRolesByEmailId(String emailId);
}

package org.santanubrains.dataAdapter;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.santanubrains.domain.Credential;
import org.santanubrains.response.LoginResponse;

import io.reactivex.Single;

public interface CredentialDataAdapter {

	public Single<LoginResponse> authenticateUser(UriInfo uriInfo,Credential credential);
	
	public List<String> getUserRoles(String emailId);
}

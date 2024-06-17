package org.santanubrains.service;

import javax.ws.rs.core.UriInfo;

import org.santanubrains.domain.Credential;
import org.santanubrains.response.LoginResponse;

import io.reactivex.Single;

public interface CredentialService {

	public Single<LoginResponse> authenticate(UriInfo uriInfo,Credential credential);
}

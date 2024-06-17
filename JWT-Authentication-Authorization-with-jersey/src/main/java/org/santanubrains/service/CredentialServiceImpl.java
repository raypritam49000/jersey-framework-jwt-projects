package org.santanubrains.service;

import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import org.santanubrains.dataAdapter.CredentialDataAdapter;
import org.santanubrains.domain.Credential;
import org.santanubrains.response.LoginResponse;

import io.reactivex.Single;

public class CredentialServiceImpl implements CredentialService {

	private CredentialDataAdapter credentialDataAdapter;

	@Inject
	public CredentialServiceImpl(CredentialDataAdapter credentialDataAdapter) {
		super();
		this.credentialDataAdapter = credentialDataAdapter;
	}

	@Override
	public Single<LoginResponse> authenticate(UriInfo uriInfo, Credential credential) {

		return credentialDataAdapter.authenticateUser(uriInfo, credential);
	}

}

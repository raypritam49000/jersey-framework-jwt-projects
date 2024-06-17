package org.santanubrains.resource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanubrains.domain.Credential;
import org.santanubrains.exception.InternalErrorException;
import org.santanubrains.log4j.Log4jUtil;
import org.santanubrains.response.LoginResponse;
import org.santanubrains.service.CredentialService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("authenticate")
public class CredentialResource {

	private static final Logger logger = Log4jUtil.getLogger(CredentialResource.class);

	private CredentialService credentialService;
	private Gson gson;

	@Context
	private UriInfo uriInfo;

	@Inject
	public CredentialResource(CredentialService credentialService, Gson gson) {
		super();
		this.credentialService = credentialService;
		this.gson = gson;
	}

	@POST
	@ManagedAsync
	public void authenticateUserByCredential(@Suspended final AsyncResponse async, Credential credential) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		credentialService.authenticate(uriInfo, credential).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<LoginResponse>() {

					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onSuccess(LoginResponse loginResponse) {
						async.resume(gson.toJson(loginResponse));
						outerLatch.countDown();

					}

					@Override
					public void onError(Throwable errorResponse) {
						logger.error(errorResponse);
						async.resume(errorResponse);
						outerLatch.countDown();

					}
				});

		try {
			if (!outerLatch.await(10, TimeUnit.SECONDS)) {
				async.resume(new InternalErrorException());
			}
		} catch (Exception e) {
			async.resume(new InternalErrorException());
		}

	}

}

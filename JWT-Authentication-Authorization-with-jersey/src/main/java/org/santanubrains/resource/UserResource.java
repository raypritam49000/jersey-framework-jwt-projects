package org.santanubrains.resource;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanubrains.authentication.JWTTokenNeeded;
import org.santanubrains.domain.User;
import org.santanubrains.exception.InternalErrorException;
import org.santanubrains.log4j.Log4jUtil;
import org.santanubrains.response.Response;
import org.santanubrains.service.UserService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResource {

	private static final Logger logger = Log4jUtil.getLogger(UserResource.class);
	private UserService userService;
	private Gson gson;

	@Inject
	public UserResource(UserService userService, Gson gson) {
		super();
		this.userService = userService;
		this.gson = gson;
	}

	@Path("create")
	@POST
	@ManagedAsync
	public void createUser(@Suspended final AsyncResponse async, User user) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		userService.createUser(user).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Response>() {

			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(Response userCreatedResponse) {
				async.resume(gson.toJson(userCreatedResponse));
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

	@Path("findbylastname/{lastname}")
	@JWTTokenNeeded
	@RolesAllowed("ADMIN")
	@GET
	@ManagedAsync
	public void getUserByLastName(@Suspended final AsyncResponse async, @PathParam("lastname") final String lastName) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		userService.findByLastName(lastName).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<User>>() {

			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(List<User> userResponseByLastName) {
				async.resume(gson.toJson(userResponseByLastName));
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

	@GET
	@RolesAllowed("ADMIN")
	@JWTTokenNeeded
	@ManagedAsync
	public void getAllUsers(@Suspended final AsyncResponse async) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		userService.findAllUser().subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<User>>() {

			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(List<User> allUserResponse) {
				async.resume(gson.toJson(allUserResponse));
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

	@Path("{id}")
	@GET
	@RolesAllowed("USER")
	@JWTTokenNeeded
	@ManagedAsync
	public void getUserById(@Suspended final AsyncResponse async, @PathParam("id") final Long id) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		userService.findById(id).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<User>() {

			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(User userResponse) {
				async.resume(gson.toJson(userResponse));
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

	@Path("update/{id}")
	@PUT
	@RolesAllowed({"ADMIN","USER"})
	@JWTTokenNeeded
	@ManagedAsync
	public void updateUserById(@Suspended final AsyncResponse async, @PathParam("id") final Long id, User user) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		userService.updateUser(id, user).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Response>() {

			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(Response userUpdateResponse) {
				async.resume(gson.toJson(userUpdateResponse));
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

	@Path("delete/{id}")
	@DELETE
	@RolesAllowed("ADMIN")
	@JWTTokenNeeded
	@ManagedAsync
	public void deleteUserById(@Suspended final AsyncResponse async, @PathParam("id") final Long id) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		userService.deleteUser(id).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Response>() {

			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(Response deleteUserResponse) {
				async.resume(gson.toJson(deleteUserResponse));
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

package org.santanubrains.application;

import java.util.Date;

import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.santanubrains.dao.impl.CredentialDaoImpl;
import org.santanubrains.dao.impl.UserDaoImpl;
import org.santanubrains.dao.interfaces.CredentialDao;
import org.santanubrains.dao.interfaces.UserDao;
import org.santanubrains.dataAdapter.CredentialDataAdapter;
import org.santanubrains.dataAdapter.CredentialDataAdapterImpl;
import org.santanubrains.dataAdapter.UserDataAdapter;
import org.santanubrains.dataAdapter.UserDataAdapterImpl;
import org.santanubrains.exception.GenericExceptionMapper;
import org.santanubrains.exception.InternalErrorExceptionMapper;
import org.santanubrains.exception.NotFoundExceptionMapper;
import org.santanubrains.filter.JWTTokenNeededFilter;
import org.santanubrains.filter.LoggingFilter;
import org.santanubrains.filter.PoweredByResponseFilter;
import org.santanubrains.log4j.Log4jUtil;
import org.santanubrains.resource.CredentialResource;
import org.santanubrains.resource.UserResource;
import org.santanubrains.service.CredentialService;
import org.santanubrains.service.CredentialServiceImpl;
import org.santanubrains.service.UserService;
import org.santanubrains.service.UserServiceImpl;
import org.santanubrains.utiliy.IssueToken;
import org.santanubrains.utiliy.IssueTokenImpl;
import org.santanubrains.utiliy.KeyGenerator;
import org.santanubrains.utiliy.SimpleKeyGenerator;

import com.google.gson.Gson;

@ApplicationPath("api")
public class JWTAuthApplication extends ResourceConfig {

	private static final Logger logger = Log4jUtil.getLogger(JWTAuthApplication.class);

	public JWTAuthApplication() {
		super();

		logger.info("Restful rxjava JWTAuth application initializing : " + new Date().toString());

		register(GenericExceptionMapper.class);
		register(InternalErrorExceptionMapper.class);
		register(NotFoundExceptionMapper.class);
		register(LoggingFilter.class);
		register(PoweredByResponseFilter.class);
		register(JWTTokenNeededFilter.class);
		register(UserResource.class);
		register(CredentialResource.class);

		register(new AbstractBinder() {

			@Override
			protected void configure() {
				bindAsContract(Gson.class);
				bind(SimpleKeyGenerator.class).to(KeyGenerator.class);
				bind(IssueTokenImpl.class).to(IssueToken.class);
				bind(UserDaoImpl.class).to(UserDao.class);
				bind(UserDataAdapterImpl.class).to(UserDataAdapter.class);
				bind(UserServiceImpl.class).to(UserService.class);
				bind(CredentialDaoImpl.class).to(CredentialDao.class);
				bind(CredentialDataAdapterImpl.class).to(CredentialDataAdapter.class);
				bind(CredentialServiceImpl.class).to(CredentialService.class);

			}
		});
	}

}

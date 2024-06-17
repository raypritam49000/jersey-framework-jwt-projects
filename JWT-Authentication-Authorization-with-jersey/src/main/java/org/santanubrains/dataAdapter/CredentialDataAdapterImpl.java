package org.santanubrains.dataAdapter;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.santanubrains.dao.interfaces.CredentialDao;
import org.santanubrains.domain.Credential;
import org.santanubrains.hibernateUtil.HibernateUtil;
import org.santanubrains.response.LoginResponse;
import org.santanubrains.utiliy.IssueToken;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class CredentialDataAdapterImpl implements CredentialDataAdapter {

	private static final Integer success = 1;
	private static final String message = "login success";
	private SessionFactory sessionFactory = null;
	private Session session = null;

	private CredentialDao credentialDao;
	private IssueToken issueToken;

	@Inject
	public CredentialDataAdapterImpl(CredentialDao credentialDao, IssueToken issueToken) {
		super();
		this.credentialDao = credentialDao;
		this.issueToken = issueToken;
	}

	@Override
	public Single<LoginResponse> authenticateUser(UriInfo uriInfo, Credential credential) {

		return Single.create(new SingleOnSubscribe<LoginResponse>() {

			@Override
			public void subscribe(SingleEmitter<LoginResponse> subscriber) throws Exception {
				try {

					sessionFactory = HibernateUtil.getSessionfactory();
					session = sessionFactory.openSession();
					credentialDao.setSession(session);
					boolean bool = credentialDao.authenticate(credential);
					if (bool) {
						subscriber.onSuccess(
								new LoginResponse(success, message, issueToken.generateJWTToken(uriInfo, credential)));
					} else {
						subscriber.onError(new SecurityException("Invalid Credential"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					subscriber.onError(new RuntimeException("Oops something went wrong!"));
				} finally {
					if (session.isOpen()) {
						session.close();
					}
				}

			}
		});
	}

	@Override
	public List<String> getUserRoles(String emailId) {

		try {

			sessionFactory = HibernateUtil.getSessionfactory();
			session = sessionFactory.openSession();
			credentialDao.setSession(session);

			List<String> roleList = credentialDao.getRolesByEmailId(emailId);
			return roleList;

		} catch (Exception e) {
			e.printStackTrace();

		}finally {
			if(session.isOpen()) {
				session.close();
			}
		}
		return null;
	}

}

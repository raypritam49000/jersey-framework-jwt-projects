package org.santanubrains.dataAdapter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.santanubrains.dao.interfaces.UserDao;
import org.santanubrains.domain.User;
import org.santanubrains.hibernateUtil.HibernateUtil;
import org.santanubrains.response.Response;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class UserDataAdapterImpl implements UserDataAdapter {

	private static final Integer success = 1;
	private static final String message = " created successfully";
	private static final String updateMessage = "updated successfully";
	private static final String deleteMessage = "deleted successfully";
	private SessionFactory sessionFactory = null;
	private Session session = null;
	private Transaction tx = null;

	private UserDao userDao;

	@Inject
	public UserDataAdapterImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public Single<Response> createUser(User user) {

		return Single.create(new SingleOnSubscribe<Response>() {

			@Override
			public void subscribe(SingleEmitter<Response> subscriber) throws Exception {

				try {

					sessionFactory = HibernateUtil.getSessionfactory();
					session = sessionFactory.openSession();
					userDao.setSession(session);
					tx = session.beginTransaction();
					boolean bool = userDao.checkEmailId(user);
					if (bool) {
						user.setCreatedDate(new Date());
						user.setCreatedTime(new Date());
						user.setLastUpdatedDate(new Date());
						user.setLastUpdatedTime(new Date());
						User u = userDao.save(user);
						tx.commit();
						if (u != null) {
							subscriber.onSuccess(new Response(success, u.getEmailId() + message));
						} else {
							subscriber.onError(new RuntimeException("User creation failed"));
						}
					} else {
						subscriber.onError(new RuntimeException(user.getEmailId() + " already exists"));
					}

				} catch (Exception e) {
					if (tx.isActive()) {
						tx.rollback();
					}
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
	public Single<List<User>> findByLastName(String lastName) {

		return Single.create(new SingleOnSubscribe<List<User>>() {

			@Override
			public void subscribe(SingleEmitter<List<User>> subscriber) throws Exception {
				try {

					sessionFactory = HibernateUtil.getSessionfactory();
					session = sessionFactory.openSession();
					userDao.setSession(session);
					List<User> users = userDao.findByLastName(lastName);
					if (users.size() > 0) {
						subscriber.onSuccess(users);
					} else {
						subscriber.onError(new NotFoundException("Users not found with last name : " + lastName));
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
	public Single<List<User>> findAllUser() {

		return Single.create(new SingleOnSubscribe<List<User>>() {

			@Override
			public void subscribe(SingleEmitter<List<User>> subscriber) throws Exception {
				try {

					sessionFactory = HibernateUtil.getSessionfactory();
					session = sessionFactory.openSession();
					userDao.setSession(session);
					List<User> users = userDao.findAll();
					if (users.size() > 0) {
						subscriber.onSuccess(users);
					} else {
						subscriber.onError(new NotFoundException("Users not found"));
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
	public Single<User> findUserById(Long id) {

		return Single.create(new SingleOnSubscribe<User>() {

			@Override
			public void subscribe(SingleEmitter<User> subscriber) throws Exception {
				try {

					sessionFactory = HibernateUtil.getSessionfactory();
					session = sessionFactory.openSession();
					userDao.setSession(session);

					User user = userDao.findById(id);

					if (user != null) {
						User u = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmailId(), user.getCompany(),
								user.getRoles());

						subscriber.onSuccess(u);
					} else {
						subscriber.onError(new NotFoundException("User with : " + id + " not found"));
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
	public Single<Response> updateUser(Long userId, User user) {

		return Single.create(new SingleOnSubscribe<Response>() {

			@Override
			public void subscribe(SingleEmitter<Response> subscriber) throws Exception {
				try {

					if (user.getEmailId() == null && user.getPassword() == null) {
						sessionFactory = HibernateUtil.getSessionfactory();
						session = sessionFactory.openSession();
						userDao.setSession(session);
						tx = session.beginTransaction();
						User u = userDao.findById(userId);
						if (u != null) {
							u.setRoles(user.getRoles());
							u.setCompany(user.getCompany());
							u.setFirstName(user.getFirstName());
							u.setLastName(user.getLastName());
							u.setLastUpdatedDate(new Date());
							u.setLastUpdatedTime(new Date());
							User u1 = userDao.save(u);
							tx.commit();
							if (u1 != null) {
								subscriber.onSuccess(new Response(success,"userId : "+userId+" "+updateMessage));
							} else {
								subscriber.onSuccess(new Response(0, "user updation failed."));
							}

						} else {
							subscriber.onError(new NotFoundException("User with : " + userId + " not found"));
						}
					} else {
						subscriber.onSuccess(new Response(0, "emailId or password can not be updated."));
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (tx.isActive()) {
						tx.rollback();
					}
					subscriber.onError(new RuntimeException(e.getMessage()));
				} finally {
					if (session.isOpen()) {
						session.close();
					}
				}

			}
		});
	}

	@Override
	public Single<Response> deleteUser(Long userId) {
		
		return Single.create(new SingleOnSubscribe<Response>() {

			@Override
			public void subscribe(SingleEmitter<Response> subscriber) throws Exception {
				try {
					
					sessionFactory = HibernateUtil.getSessionfactory();
					session = sessionFactory.openSession();
					userDao.setSession(session);
					tx = session.beginTransaction();
					User u = userDao.findById(userId);
					if(u != null) {
						userDao.delete(u);
						tx.commit();
						subscriber.onSuccess(new Response(success,"userId : "+userId+" "+deleteMessage));
					}else {
						subscriber.onError(new NotFoundException("User with : " + userId + " not found"));
					}
					
				}catch(Exception e) {
					e.printStackTrace();
					if(tx.isActive()) {
						tx.rollback();
					}
					subscriber.onError(new RuntimeException(e.getMessage()));
				}finally {
					if(session.isOpen()) {
						session.close();
					}
				}
				
			}
		});
	}

}

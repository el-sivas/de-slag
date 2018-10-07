package de.slag.central.data.impl.adm;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.stereotype.Repository;

import de.slag.central.data.adm.UserDao;
import de.slag.central.data.impl.AbstractApplicationBeanDao;
import de.slag.central.data.model.Identification;
import de.slag.central.model.adm.User;

@Repository
public class UserDaoImpl extends AbstractApplicationBeanDao<Identification, User> implements UserDao {

	@Override
	protected Class<Identification> getBeanClass() {
		return Identification.class;
	}

	@Override
	protected BiConsumer<User, Identification> mapApplicationToPersist() {
		return new BiConsumer<User, Identification>() {

			@Override
			public void accept(User t, Identification u) {
				u.setIdentifier(t.getUsername());
				u.setIdHash(t.getPasswordHash());
			}
		}.andThen(super.mapApplicationToPersist());
	}

	@Override
	protected BiConsumer<Identification, User> mapPersistToApplication() {
		return new BiConsumer<Identification, User>() {

			@Override
			public void accept(Identification t, User u) {
				u.setUsername(t.getIdentifier());
				u.setPasswordHash(t.getIdHash());
			}
		}.andThen(super.mapPersistToApplication());
	}

	@Override
	public User loadByUsername(String username, Supplier<User> creator) {
		final Identification loadOneBy = loadOneBy("identifier", username);
		if (loadOneBy == null) {
			return null;
		}
		final User user = creator.get();
		mapPersistToApplication().accept(loadOneBy, user);
		return user;
	}
}

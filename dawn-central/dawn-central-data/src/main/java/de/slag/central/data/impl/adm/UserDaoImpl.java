package de.slag.central.data.impl.adm;

import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.stereotype.Repository;

import de.slag.central.data.adm.UserDao;
import de.slag.central.data.impl.AbstractApplicationBeanDao;
import de.slag.central.data.impl.HqlSelect;
import de.slag.central.data.model.Identification;
import de.slag.central.model.adm.User;

@Repository
public class UserDaoImpl extends AbstractApplicationBeanDao<Identification, User> implements UserDao {

	@Override
	protected BiConsumer<User, Identification> getSpecialTransferAP() {
		return new BiConsumer<User, Identification>() {

			@Override
			public void accept(User t, Identification u) {
				u.setIdentifier(t.getUsername());
				u.setIdHash(t.getPasswordHash());
			}
		};
	}
	
	@Override
	protected BiConsumer<Identification, User> getSpecialTransferPA() {
		return new BiConsumer<Identification, User>() {
			
			@Override
			public void accept(Identification t, User u) {
				u.setUsername(t.getIdentifier());
				u.setPasswordHash(t.getIdHash());
			}
		};
	}

	@Override
	protected Class<Identification> getBeanClass() {
		return Identification.class;
	}

	@Override
	public User loadByUsername(String username, Supplier<User> creator) {
		final String hql = HqlSelect.getHql(getBeanClass(), Collections.singletonMap("identifier", username));
		final Identification identification = execute(hql, RESULT_UNIQUE);
		return one(identification, creator);
	}
}

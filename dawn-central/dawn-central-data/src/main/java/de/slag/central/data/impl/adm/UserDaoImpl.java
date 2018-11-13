package de.slag.central.data.impl.adm;

import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.stereotype.Repository;

import de.slag.central.data.adm.UserDao;
import de.slag.central.data.impl.AbstractApplicationBeanDao;
import de.slag.central.data.impl.HqlSelect;
import de.slag.central.data.model.AdmIdentification;
import de.slag.central.model.adm.User;

@Repository
public class UserDaoImpl extends AbstractApplicationBeanDao<AdmIdentification, User> implements UserDao {

	@Override
	protected BiConsumer<User, AdmIdentification> getSpecialTransferAP() {
		return new BiConsumer<User, AdmIdentification>() {

			@Override
			public void accept(User t, AdmIdentification u) {
				u.setIdentifier(t.getUsername());
				u.setIdHash(t.getPasswordHash());
			}
		};
	}
	
	@Override
	protected BiConsumer<AdmIdentification, User> getSpecialTransferPA() {
		return new BiConsumer<AdmIdentification, User>() {
			
			@Override
			public void accept(AdmIdentification t, User u) {
				u.setUsername(t.getIdentifier());
				u.setPasswordHash(t.getIdHash());
			}
		};
	}

	@Override
	protected Class<AdmIdentification> getBeanClass() {
		return AdmIdentification.class;
	}

	@Override
	public User loadByUsername(String username, Supplier<User> creator) {
		final String hql = HqlSelect.getHql(getBeanClass(), Collections.singletonMap("identifier", username));
		final AdmIdentification identification = execute(hql, RESULT_UNIQUE);
		return one(identification, creator);
	}
}

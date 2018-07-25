package de.slag.central.data;

import java.util.Date;

public abstract class PersistBean implements CreateableBean {

	private Long id;

	private Date created;

	private Date valitUntil;

	public Long getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getValitUntil() {
		return valitUntil;
	}

	@Override
	public boolean equals(Object obj) {
		Class<? extends PersistBean> class1 = this.getClass();
		Class<? extends Object> class2 = obj.getClass();
		if (!class1.equals(class2)) {
			return false;
		}
		return this.compareTo((PersistBean) obj) == 0;
	}

	

}

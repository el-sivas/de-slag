package de.slag.central.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;

import de.slag.base.tools.DateUtils;

@MappedSuperclass
@Inheritance
public abstract class PersistBean implements CreateableBean, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic
	private Long id;

	private Date created = new Date();

	private Date validUntil = DateUtils.toDate(LocalDateTime.of(9999, 12, 31, 23, 59, 59));
	
	protected PersistBean() {
	
	}

	public Long getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getValidUntil() {
		return validUntil;
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

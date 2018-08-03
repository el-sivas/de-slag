package de.slag.central.data.dbinfo;

import javax.persistence.Column;
import javax.persistence.Entity;

import de.slag.central.data.PersistBean;

@Entity
public class DbInfo extends PersistBean {

	private static final long serialVersionUID = 1L;

	@Column
	private String info;

	@Column
	private String text;

	public DbInfo() {
		super();
	}

	public DbInfo(final String info, final String text) {
		this.info = info;
		this.text = text;
	}

	public String getInfo() {
		return info;
	}

	public String getText() {
		return text;
	}

}

package de.slag.central.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

import de.slag.central.DawnConstants;
import de.slag.central.data.impl.PersistBean;

@Entity
public class DbInfo extends PersistBean {

	@Column(length = DawnConstants.Database.ORACLE_MAX_LENGTH_VARCHAR_2)
	private String info;
	
	private DbInfoType type;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = StringUtils.abbreviate(info, DawnConstants.Database.ORACLE_MAX_LENGTH_VARCHAR_2);
	}

	public DbInfoType getType() {
		return type;
	}

	public void setType(DbInfoType type) {
		this.type = type;
	}

}

package de.slag.demo.data;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.slag.central.data.PersistBean;

@Entity
@Table(name="demo")
public class Demo extends PersistBean {

	private static final long serialVersionUID = 1L;
	

}

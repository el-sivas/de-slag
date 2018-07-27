package de.slag.central.data;

import java.util.Date;

public interface CreateableBean extends Comparable<CreateableBean> {

	Date getCreated();

	Long getId();

	default int compareTo(final CreateableBean o) {
		if (o == null) {
			return 1;
		}

		Class class1 = o.getClass();
		Class class2 = getClass();
		if (class1 != class2) {
			return class1.getName().compareTo(class2.getName());
		}

		final Long otherId = o.getId();
		final Long thisId = getId();

		if (thisId != null || otherId != null) {

			if (thisId != null && otherId == null) {
				return 1;
			}

			if (thisId == null && otherId != null) {
				return -1;
			}
			return thisId.compareTo(otherId);
		}

		final Date otherCreated = o.getCreated();
		final Date thisCreated = this.getCreated();

		if (thisCreated != null && otherCreated == null) {
			return 1;
		}

		if (thisCreated == null && otherCreated != null) {
			return -1;
		}
		return thisCreated.compareTo(otherCreated);

	}

}

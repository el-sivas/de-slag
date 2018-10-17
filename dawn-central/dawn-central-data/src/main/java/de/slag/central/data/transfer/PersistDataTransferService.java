package de.slag.central.data.transfer;

import java.util.function.BiConsumer;

import de.slag.central.data.impl.PersistBean;
import de.slag.central.model.ApplicationBean;

public interface PersistDataTransferService {

	void transferData(PersistBean from, ApplicationBean to);

	void transferData(ApplicationBean from, PersistBean to);

}

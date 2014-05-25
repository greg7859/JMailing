package org.jmailing.io.email;

import org.jmailing.model.email.EMail;

public interface EMailIO {

	void save(String path, EMail email, int index);

	EMail retrieve(String path, int index);

}

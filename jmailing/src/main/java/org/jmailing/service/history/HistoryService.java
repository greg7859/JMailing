package org.jmailing.service.history;

import java.io.IOException;
import java.util.List;

import org.jmailing.model.email.EMail;
import org.jmailing.model.history.History;

public interface HistoryService {

	/**
	 * Load the history set of the current project
	 * 
	 * @return the collection of history
	 */
	List<History> load();

	/**
	 * Load the log of the history
	 * 
	 * @param h
	 *            the history
	 * @return the full log
	 */
	String loadLog(History h) throws IOException;
	
	List<EMail> loadEMails(History h) throws IOException;


}

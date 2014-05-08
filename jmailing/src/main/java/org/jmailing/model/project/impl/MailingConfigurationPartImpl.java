package org.jmailing.model.project.impl;

import org.jmailing.model.project.MailingConfigurationPart;

public class MailingConfigurationPartImpl implements MailingConfigurationPart {

	private int numberMailPerHour = 120;

	public MailingConfigurationPartImpl() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.jmailing.model.project.impl.MailingConfigurationPart#getNumberMailPerHour()
	 */
	@Override
	public int getNumberMailPerHour() {
		return numberMailPerHour;
	}

	/* (non-Javadoc)
	 * @see org.jmailing.model.project.impl.MailingConfigurationPart#setNumberMailPerHour(int)
	 */
	@Override
	public void setNumberMailPerHour(int numberMailPerHour) {
		this.numberMailPerHour = numberMailPerHour;
	}

}

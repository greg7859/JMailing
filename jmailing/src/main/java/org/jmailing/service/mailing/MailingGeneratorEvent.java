package org.jmailing.service.mailing;

public class MailingGeneratorEvent {
	
	private int progress;
	private int eventType;
	private int index;

	public MailingGeneratorEvent(int eventType, int progress, int index) {
		this.progress=progress;
		this.eventType=eventType;
		this.index=index;
	}

	/**
	 * @return the progress
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * @return the event type
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	

}

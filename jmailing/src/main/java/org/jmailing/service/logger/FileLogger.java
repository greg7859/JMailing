package org.jmailing.service.logger;

public interface FileLogger {

	void start(String path);

	void log(String message);

	void stop();

}
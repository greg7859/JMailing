package org.jmailing.service.logger.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import org.jmailing.injector.annotation.Log;
import org.jmailing.service.logger.FileLogger;

public class FileLoggerImpl implements FileLogger {
	@Inject
	@Log
	private String logExtension;

	private BufferedWriter log;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH-mm-ss");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.service.logger.FileLogger#start(java.lang.String)
	 */
	@Override
	public void start(String path) {
		try {
			StringBuffer buf = new StringBuffer(path);
			buf.append(File.separator);
			buf.append("log");
			buf.append(logExtension);
			log = new BufferedWriter(new FileWriter(buf.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.service.logger.FileLogger#log(java.lang.String)
	 */
	@Override
	public void log(String message) {
		if (log == null)
			return;
		try {
			String dateStr = dateFormat
					.format(Calendar.getInstance().getTime());
			StringBuffer buf = new StringBuffer();
			buf.append("[").append(dateStr).append("] ");
			buf.append(message);
			log.write(buf.toString());
			log.newLine();
			log.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.service.logger.FileLogger#stop()
	 */
	@Override
	public void stop() {
		if (log == null)
			return;
		try {
			log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log = null;
	}

}

package org.jmailing.io.csv;

import java.io.IOException;
import java.util.List;

import org.jmailing.model.source.Data;

public interface DataFileReader {
	List<Data> readAll(String filename) throws IOException;
	List<Data> read(String filename, int nbItemToRead)
			throws IOException;
}

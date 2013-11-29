package org.jmailing.io.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jmailing.model.source.Data;
import org.jmailing.model.source.impl.DataImpl;

import com.google.common.base.Splitter;

public class CsvFileReader implements DataFileReader {

	// @Inject
	// Injector injector = null;

	public CsvFileReader() {
	}

	@Override
	public List<Data> readAll(String filename) throws IOException {
		return read(filename, -1);
	}

	@Override
	public List<Data> read(String filename, int nbItemToRead)
			throws IOException {
		List<Data> dataColl = new ArrayList<>();
		Data data = null;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line = br.readLine();
			while (line != null) {
				Iterable<String> splited = Splitter.on(',').split(line);
				// data = injector.getInstance(Data.class);
				// FIXME Use injector
				data = new DataImpl();
				dataColl.add(data);
				for (String string : splited) {
					data.add(string);
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return dataColl;
	}
}

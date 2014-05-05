package org.jmailing.io.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jmailing.injector.provider.DataProvider;
import org.jmailing.model.source.Data;

import com.google.common.base.Splitter;

public class CsvFileReader implements DataFileReader {

	@Inject
	DataProvider provider;

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
				data = provider.get();
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

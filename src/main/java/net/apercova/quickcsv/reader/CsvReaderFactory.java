package net.apercova.quickcsv.reader;

import java.io.Reader;
import java.util.List;

public final class CsvReaderFactory {

	public static CsvReader<List<String>> newInstance() {
		return new SimpleCsvReader();
	}
	public static <T> CsvReader<T> newInstance(Class<T> type) {
		return new EntityCsvReader<T>(type);
	}
	public static CsvReader<List<String>> newInstance(Reader reader) {
		return new SimpleCsvReader(reader);
	}	
	public static <T> CsvReader<T> newInstance(Reader reader, Class<T> type) {
		return new EntityCsvReader<T>(reader, type);
	}
}

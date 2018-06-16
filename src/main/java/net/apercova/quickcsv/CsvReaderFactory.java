<<<<<<< HEAD
package net.apercova.quickcsv;

import java.io.Reader;
import java.util.List;

import net.apercova.quickcsv.reader.CsvReader;
import net.apercova.quickcsv.reader.EntityCsvReader;
import net.apercova.quickcsv.reader.SimpleCsvReader;

public final class CsvReaderFactory {

	public static CsvReader<List<String>> newInstance() {
		return SimpleCsvReader.newInstance();
	}
	public static <T> CsvReader<T> newInstance(Class<T> type) {
		return EntityCsvReader.newInstance(type);
	}
	public static CsvReader<List<String>> newInstance(Reader reader) {
		return SimpleCsvReader.newInstance(reader);
	}	
	public static <T> CsvReader<T> newInstance(Reader reader, Class<T> type) {
		return EntityCsvReader.newInstance(reader, type);
	}
}
=======
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
>>>>>>> branch 'interface-refactor' of https://github.com/apercova/QuickCsv.git

package net.apercova.quickcsv;

import java.io.Writer;
import java.util.Collection;

import net.apercova.quickcsv.writer.CsvWriter;
import net.apercova.quickcsv.writer.EntityCsvWriter;
import net.apercova.quickcsv.writer.SimpleCsvWriter;

public final class CsvWriterFactory {
	
	public static CsvWriter<Collection<String>> newInstance() {
		return SimpleCsvWriter.newInstance();
	}
	
	public static CsvWriter<Collection<String>> newInstance(Writer writer) {
		return SimpleCsvWriter.newInstance(writer);
	}
	
	public static CsvWriter<Collection<String>> newInstance(Writer writer, Collection<Collection<String>> lines) {
		return SimpleCsvWriter.newInstance(writer, lines);
	}
	
	public static <T> CsvWriter<T> newInstance(Class<T> type) {
		return EntityCsvWriter.newInstance(type);
	}
	
	public static <T> CsvWriter<T> newInstance(Writer writer, Class<T> type) {
		return EntityCsvWriter.newInstance(writer, type);
	}
	
	public static <T> CsvWriter<T> newInstance(Writer writer, Collection<T> lines, Class<T> type) {
		return EntityCsvWriter.newInstance(writer, lines, type);
	}
	
}

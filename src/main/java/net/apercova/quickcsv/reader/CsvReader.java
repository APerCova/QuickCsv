package net.apercova.quickcsv.reader;

import java.io.Closeable;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

/**
 * Reader for Comma-Separated values in compliance with RFC 4180
 * plus custom delimiter and quoted values.
 * <pre>
 *  Common Format and MIME Type for Comma-Separated Values (CSV) Files
 *  <a href="https://tools.ietf.org/html/rfc4180" target="_blank">https://tools.ietf.org/html/rfc4180</a>
 * </pre>
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @since 1.0
 */
public interface CsvReader<T> extends Iterator<T>, Iterable<T>, Closeable{

	CsvReader<T> setReader(Reader reader);
	CsvReader<T> setDelimiter(char delimiter);
	CsvReader<T> setQuote(char quote);
	CsvReader<T> fromLine(long fromLine);
	CsvReader<T> maxLines(long maxLines);
	CsvReader<T> escapeheader(boolean readHeader);
	boolean escapeheader();
	List<T> read() throws CsvReaderException;
	int getLineNumber();
	List<Throwable> getSuppressed();
}
 

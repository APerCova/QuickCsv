package net.apercova.quickcsv;

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
public interface CsvReader<C> extends Iterator<C>, Iterable<C>, Closeable{

	public CsvReader<C> setReader(Reader reader);
	public CsvReader<C> setDelimiter(char delimiter);
	public CsvReader<C> setQuote(char quote);
	public CsvReader<C> fromLine(long fromLine);
	public CsvReader<C> maxLines(long maxLines);
	public CsvReader<C> readheader(boolean readHeader);
	public boolean isHeaderReadable();
	public int getLineNumber();
	public List<C> read() throws CsvReaderException; 
}

package net.apercova.quickcsv;

import java.io.IOException;
import java.io.Reader;

import net.apercova.io.IterableLineNumberReader;

public abstract class AbstractCsvReader<E> implements CsvReader<E>{

	protected Reader reader;
	protected char delimiter;
	protected char quote;
	protected boolean escapeHeader;
	protected long fromLine;
	protected long maxLines;

	public AbstractCsvReader() {
		super();
		delimiter = CsvCons.COMMA;
		quote = CsvCons.DOUBLE_QUOTE;
		escapeHeader = false;
		fromLine = 0;
		maxLines = 0;
	}
	public AbstractCsvReader(Reader reader) {
		super();
		setReader(reader);
		delimiter = CsvCons.COMMA;
		quote = CsvCons.DOUBLE_QUOTE;
		escapeHeader = false;
		fromLine = 0;
		maxLines = 0;
	}
	
	public CsvReader<E> setDelimiter(char delimiter){
		this.delimiter = delimiter;
		return this;
	}
	public CsvReader<E> setQuote(char quote){
		this.quote = quote;
		return this;
	}
	public CsvReader<E> setReader(Reader reader){
		if( !(reader instanceof IterableLineNumberReader)) {
			reader = new IterableLineNumberReader(reader);
		}
		this.reader = reader;
		return this;
	}
	public CsvReader<E> fromLine(long fromLine){
		this.fromLine = fromLine;
		return this;
	}
	public CsvReader<E> maxLines(long maxLines){
		this.maxLines = maxLines;
		return this;
	};
	public CsvReader<E> escapeheader(boolean escapeHeader){
		this.escapeHeader = escapeHeader;
		return this;
	};
	public boolean isHeaderEscaped() {
		return escapeHeader;
	}
	
	public void close() throws IOException {
		if(reader != null) {
			reader.close();
		}
	}
	
	/**
	 * Not implemented by default
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	
}

package net.apercova.quickcsv;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractCsvWriter<E> implements CsvWriter<E> {

	protected Writer writer;
	protected Collection<E> lines;
	protected char delimiter;
	protected char quote;
	protected boolean writeHeader;
	protected boolean autoflush;
	
	protected AbstractCsvWriter() {
		this.lines = new LinkedList<E>();
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.writeHeader = true;
		this.autoflush = true;
	}
	
	protected AbstractCsvWriter(Writer writer) {
		this.writer = writer;
		this.lines = new LinkedList<E>();
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.writeHeader = true;
		this.autoflush = true;
	}
	
	protected AbstractCsvWriter(Writer writer, Collection<E> lines) {
		this.writer = writer;
		this.lines = lines;
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.writeHeader = true;
		this.autoflush = true;
	}
	
	public CsvWriter<E> setWriter(Writer writer){
		this.writer = writer;
		return this;
	}
	public CsvWriter<E> setDelimiter(char delimiter){
		this.delimiter = delimiter;
		return this;
	}
	public CsvWriter<E> setQuote(char quote){
		this.quote = quote;
		return this;
	}
	
	public CsvWriter<E> setAutoflush(boolean autoflush){
		this.autoflush = autoflush;
		return this;
	}
	
	public CsvWriter<E> setLines(Collection<E> lines) {
		this.lines = lines;
		return this;
	}
	
	public CsvWriter<E> addLines(Collection<E> lines) {
		if(lines == null) {
			throw new NullPointerException("lines");
		}
		lines.addAll(lines);
		return this;
	}
	
	public CsvWriter<E> addLine(E line) {
		if(lines == null) {
			throw new NullPointerException("lines");
		}
		lines.add(line);
		return this;
	}
	
	public CsvWriter<E> writeheader(boolean writeHeader){
		this.writeHeader = writeHeader;
		return this;
	}
	
	public boolean isHeaderWritable() {
		return writeHeader;
	}
	
	public void flush() throws IOException {
		if(writer != null) {
			writer.flush();
		}
	}
	
	public void close() throws IOException {
		if(writer != null) {
			writer.close();
		}
	}
}

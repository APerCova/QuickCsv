<<<<<<< HEAD
package net.apercova.quickcsv.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedList;

import net.apercova.quickcsv.CsvCons;

public abstract class AbstractCsvWriter<E> implements CsvWriter<E> {

	protected Writer writer;
	protected Collection<E> lines;
	protected char delimiter;
	protected char quote;
	protected boolean escapeHeader;
	protected boolean autoflush;
	
	protected AbstractCsvWriter() {
		this.lines = new LinkedList<E>();
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.escapeHeader = false;
		this.autoflush = true;
	}
	
	protected AbstractCsvWriter(Writer writer) {
		this.writer = writer;
		this.lines = new LinkedList<E>();
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.escapeHeader = false;
		this.autoflush = true;
	}
	
	protected AbstractCsvWriter(Writer writer, Collection<E> lines) {
		this.writer = writer;
		this.lines = lines;
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.escapeHeader = false;
		this.autoflush = true;
	}
	
	public CsvWriter<E> setWriter(Writer writer){
		this.writer = writer;
		return this;
	}
	public CsvWriter<E> setLines(Collection<E> lines) {
		this.lines = lines;
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
	public CsvWriter<E> escapeHeader(boolean escapeHeader){
		this.escapeHeader = escapeHeader;
		return this;
	}
	public CsvWriter<E> autoflush(boolean autoflush){
		this.autoflush = autoflush;
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
	
	public Collection<E> getLines() {
		return this.lines;
	}
	
	public boolean escapeHeader() {
		return escapeHeader;
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
	
	public static  void write(Writer writer, Collection<? extends Collection<String>> lines, char delimiter, char quote, boolean escapeHeader)
            throws CsvWriterException{
        if(writer == null){
            throw new CsvWriterException("missing writer",
                    new IllegalArgumentException(new NullPointerException("writer")));
        }

        if(delimiter == ' ')
            delimiter = CsvCons.COMMA;

        if(quote == ' ')
            quote = CsvCons.DOUBLE_QUOTE;

        
        if(lines != null && !lines.isEmpty()) {
        	int lineCount = 1;
        	for (Collection<String> line : lines) {
        		
        		if((lineCount == 1 && !escapeHeader) || lineCount > 1 ) {
        			writeLine(writer, line, delimiter, quote);
        		}
                lineCount++;
            }
        }
    }
	public static void writeLine(Writer writer, Collection<String> line, char delimiter, char quote) throws CsvWriterException {
		try {
			
			int c = 0;
			for(String val: line) {
	            writer.write(formatValue(val, delimiter, quote));
	            c++;
	            if (c < line.size()) {
	                writer.write(delimiter);
	            }
	            
			}
			
	        writer.write(System.getProperty("line.separator"));
		} catch (IOException e) {
			throw new CsvWriterException(e.getMessage(), e);
		}
		
	}
    public static String formatValue(String value, char delimiter, char quote){
        if(value == null || value.length() < 1){
            return "";
        }

        if(delimiter == ' ')
            delimiter = CsvCons.COMMA;

        if(quote == ' ')
            quote = CsvCons.DOUBLE_QUOTE;

        if((!value.contains(""+delimiter)) && (!value.contains(""+quote))){
            return value;
        }

        value = value.replaceAll("" + quote, quote + "" + quote);
        value = quote+""+value+""+quote;

        return value;
    }
	
}
=======
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
	protected boolean escapeHeader;
	protected boolean autoflush;
	
	protected AbstractCsvWriter() {
		this.lines = new LinkedList<E>();
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.escapeHeader = false;
		this.autoflush = true;
	}
	
	protected AbstractCsvWriter(Writer writer) {
		this.writer = writer;
		this.lines = new LinkedList<E>();
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.escapeHeader = false;
		this.autoflush = true;
	}
	
	protected AbstractCsvWriter(Writer writer, Collection<E> lines) {
		this.writer = writer;
		this.lines = lines;
		this.delimiter = CsvCons.COMMA;
		this.quote = CsvCons.DOUBLE_QUOTE;
		this.escapeHeader = false;
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
	
	public CsvWriter<E> escapeHeader(boolean escapeHeader){
		this.escapeHeader = escapeHeader;
		return this;
	}
	
	public boolean isHeaderWritable() {
		return escapeHeader;
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
>>>>>>> branch 'interface-refactor' of https://github.com/apercova/QuickCsv.git

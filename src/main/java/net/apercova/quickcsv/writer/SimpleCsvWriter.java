package net.apercova.quickcsv.writer;

import java.io.Writer;
import java.util.Collection;

import net.apercova.quickcsv.CsvCons;

public class SimpleCsvWriter extends AbstractCsvWriter<Collection<String>> {
		
	protected SimpleCsvWriter() {
		super();
	}
	protected SimpleCsvWriter(Writer writer) {
		super(writer);
	}
	protected SimpleCsvWriter(Writer writer, Collection<Collection<String>> lines) {
		super(writer, lines);
	}
	
	public static SimpleCsvWriter newInstance() {
		return new SimpleCsvWriter();
	}
	public static SimpleCsvWriter newInstance(Writer writer) {
		return new SimpleCsvWriter(writer);
	}
	public static SimpleCsvWriter newInstance(Writer writer, Collection<Collection<String>> lines) {
		return new SimpleCsvWriter(writer, lines);
	}
	
	public void write() throws CsvWriterException {
		SimpleCsvWriter.write(writer, lines, delimiter, quote, escapeHeader);
	}

	public static void write(Writer writer, Collection<Collection<String>> lines) 
    		throws CsvWriterException {
    	write(writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false);
    }
    public static void write(Writer writer, Collection<Collection<String>> lines, boolean escapeHeader) 
    		throws CsvWriterException {
    	write(writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader);
    }
    public static void write(Writer writer, Collection<Collection<String>> lines, char delimiter) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, false);
    }    
    public static void write(Writer writer, Collection<Collection<String>> lines, char delimiter, boolean escapeHeader) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader);
    }  
    public static void write(Writer writer, Collection<Collection<String>> lines, char delimiter, char quote) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, quote, false);
    }

}

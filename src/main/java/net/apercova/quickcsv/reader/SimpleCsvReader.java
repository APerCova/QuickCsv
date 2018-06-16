package net.apercova.quickcsv.reader;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import net.apercova.io.IterableLineNumberReader;
import net.apercova.quickcsv.CsvCons;

public class SimpleCsvReader extends AbstractCsvReader<List<String>>  {
	
	public static final Logger logger = Logger.getLogger(SimpleCsvReader.class.getName());
	
	protected SimpleCsvReader() {
		super();
	}
	protected SimpleCsvReader(Reader reader) {
		super(reader);
	}
	
	public static SimpleCsvReader newInstance() {
		return new SimpleCsvReader();
	}
	public static SimpleCsvReader newInstance(Reader reader) {
		return new SimpleCsvReader(reader);
	}
	
	public List<List<String>> read() throws CsvReaderException {
		return SimpleCsvReader.read(reader, delimiter, quote, escapeHeader, fromLine, maxLines);
	}

	public boolean hasNext() {
		return ((IterableLineNumberReader) reader).hasNext();
	}

	public List<String> next() {
		return SimpleCsvReader.readLine(((IterableLineNumberReader) reader).next(), delimiter, quote);
	}

	public Iterator<List<String>> iterator() {
		return this;
	}
	
    public static List<List<String>> read(Reader reader) 
    		throws CsvReaderException{
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, long fromLine, long maxLines) 
    		throws CsvReaderException{
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, fromLine, maxLines);
    }
    
    public static List<List<String>> read(Reader reader, boolean escapeHeader) 
    		throws CsvReaderException{
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, boolean escapeHeader, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, boolean escapeHeader, long fromLine, long maxLines)
    		throws CsvReaderException{
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, maxLines);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, false, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, long fromLine, long maxLines) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, false, fromLine, maxLines);
    }

    public static List<List<String>> read(Reader reader, char delimiter, boolean escapeHeader) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, boolean escapeHeader, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, boolean escapeHeader, long fromLine, long maxLines) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, maxLines);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, false, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote) 
    		throws CsvReaderException{
    	return read(reader,delimiter, quote, false, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, false, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine, long maxLines) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, false, fromLine, maxLines);
    }
        
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean escapeHeader) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, escapeHeader, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, escapeHeader, fromLine, 0);
    }
	
}

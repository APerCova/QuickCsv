package net.apercova.quickcsv.reader;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
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
	protected SimpleCsvReader(Reader reader, char delimiter) {
		super(reader, delimiter);
	}
	protected SimpleCsvReader(Reader reader, char delimiter, char quote) {
		super(reader, delimiter, quote);
	}
	public static SimpleCsvReader newInstance() {
		return new SimpleCsvReader();
	}
	public static SimpleCsvReader newInstance(Reader reader) {
		return new SimpleCsvReader(reader);
	}
	public static SimpleCsvReader newInstance(Reader reader, char delimiter) {
		return new SimpleCsvReader(reader, delimiter);
	}
	public static SimpleCsvReader newInstance(Reader reader, char delimiter, char quote) {
		return new SimpleCsvReader(reader, delimiter, quote);
	}
	
	public List<String> next() {
		return readLine(((IterableLineNumberReader) reader).next(), delimiter, quote);
	}
	
    public static List<List<String>> read(Reader reader) {
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, long fromLine) {
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, long fromLine, long maxLines) {
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, fromLine, maxLines);
    }
    
    public static List<List<String>> read(Reader reader, boolean skipHeader) {
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, skipHeader, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, boolean skipHeader, long fromLine) {
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, boolean skipHeader, long fromLine, long maxLines){
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, maxLines);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, long fromLine) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, false, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, long fromLine, long maxLines) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, false, fromLine, maxLines);
    }

    public static List<List<String>> read(Reader reader, char delimiter, boolean skipHeader) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, skipHeader, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, boolean skipHeader, long fromLine) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, boolean skipHeader, long fromLine, long maxLines) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, maxLines);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, false, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote) {
    	return read(reader,delimiter, quote, false, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine) {
    	return read(reader, delimiter, quote, false, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine, long maxLines) {
    	return read(reader, delimiter, quote, false, fromLine, maxLines);
    }
        
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean skipHeader) {
    	return read(reader, delimiter, quote, skipHeader, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean skipHeader, long fromLine) {
    	return read(reader, delimiter, quote, skipHeader, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean skipHeader, long fromLine, long maxLines){
    	List<List<String>>  lines = new LinkedList<List<String>>();
    	SimpleCsvReader csvReader = null;
    	try {
    		csvReader = SimpleCsvReader.newInstance();
    		csvReader.setReader(reader)
    		.setDelimiter(delimiter)
    		.setQuote(quote)
    		.skipHeader(skipHeader)
    		.fromLine(fromLine)
    		.maxLines(maxLines);
    		
    		while(csvReader.hasNext()) {
    			lines.add(csvReader.next());
    		}
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		} finally {
			try {
				if(csvReader != null) 
					csvReader.close();
			} catch (IOException e2) {
				logger.log(Level.WARNING, e2.getMessage(), e2);
			}
		}
		return lines;
	}
	
}

package net.apercova.quickcsv.reader;

import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
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
		return read(reader, delimiter, quote, escapeHeader, fromLine, maxLines);
	}

	public boolean hasNext() {
		this.fromLine = (this.fromLine < 1)? 1: this.fromLine;
		this.maxLines = (this.maxLines < 0)? 0: this.maxLines;
		IterableLineNumberReader r = ((IterableLineNumberReader) reader);
		boolean  res = r.hasNext();
		if(res) {
			if(this.fromLine > 0) {
				if(this.fromLine == 1) {
					if(r.getLineNumber() == 1) {
						if(this.escapeheader()) {
							r.next();
							res = this.hasNext();
						}
					}
					if(this.maxLines > 0) {
						long limit = (this.fromLine - 1 + this.maxLines);
						if(r.getLineNumber() > limit) {
							r.next();
							res = this.hasNext();
						}
					}
				}else {
					if(r.getLineNumber() == 1) {
						if(this.escapeheader()) {
							r.next();
							res = this.hasNext();
						}
					}
					else if(r.getLineNumber() < this.fromLine) {
						r.next();
						res = this.hasNext();
					}else {
						if(this.maxLines > 0) {
							long limit = (this.fromLine - 1 + this.maxLines);
							if(r.getLineNumber() > limit) {
								r.next();
								res = this.hasNext();
							}
						}
					}
				}
			}
		}
		return res;
	}

	public List<String> next() {
		List<String> res = readLine(((IterableLineNumberReader) reader).next(), delimiter, quote);
		return res;
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
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine, long maxLines){
		SimpleCsvReader csvReader = SimpleCsvReader.newInstance();
		csvReader.setReader(reader)
		.setDelimiter(delimiter)
		.setQuote(quote)
		.escapeheader(escapeHeader)
		.fromLine(fromLine)
		.maxLines(maxLines);
		
		List<List<String>>  lines = new LinkedList<List<String>>();
		for(List<String> line: csvReader) {
			lines.add(line);
		}
		
		return lines;
	}
	
}

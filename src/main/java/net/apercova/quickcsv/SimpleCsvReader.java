package net.apercova.quickcsv;

import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.apercova.io.IterableLineNumberReader;

public class SimpleCsvReader extends AbstractCsvReader<List<String>>  {
	
	public SimpleCsvReader() {
		super();
	}

	public SimpleCsvReader(Reader reader) {
		super(reader);
	}
	
	public int getLineNumber() {
		return ((IterableLineNumberReader) reader).getLineNumber();
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

	public static final Logger logger = Logger.getLogger(SimpleCsvReader.class.getName());
	
    public static List<List<String>> read(Reader reader) 
    		throws CsvReaderException{
    	return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, 0, 0);
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
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, false, fromLine, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine, long maxLines) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, false, fromLine, maxLines);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote) 
    		throws CsvReaderException{
    	return read(reader,delimiter, quote, false, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean escapeHeader) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, escapeHeader, 0, 0);
    }
    
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, escapeHeader, fromLine, 0);
    }
	
    public static List<List<String>> read(Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine, long maxLines) 
    		throws CsvReaderException{
        if(fromLine < 1) {
        	fromLine = 1;
        }
        
    	if(reader == null){
            throw new CsvReaderException("missing reader",
                    new IllegalArgumentException(new NullPointerException("reader")));
        }
        if(delimiter == ' '){
            delimiter = CsvCons.COMMA;
        }
        if(quote == ' '){
            quote = CsvCons.DOUBLE_QUOTE;
        }
        IterableLineNumberReader buff = null;
        try {
            //Create an underlying reader to keep original reader's reference
            buff = new IterableLineNumberReader(reader);

            List<List<String>>  csv = new LinkedList<List<String>>();
            int lineCount = 0;
            int lineNumber = 0;
            while(buff.hasNext()) {
            	lineNumber = buff.getLineNumber();            	
            	if(lineNumber == 1 ) {//proccess header
            		
            		if(fromLine <= lineNumber) {
            			lineCount++;
            		}
            		if(!escapeHeader) {
            			csv.add(readLine(buff.next(), delimiter, quote));
            		}
            		
            	}else {
            		if(lineNumber >= fromLine) {
            			csv.add(readLine(buff.next(), delimiter, quote));
            			lineCount++;
            		}
            	}
            	if( maxLines > 0 && lineCount >= maxLines) {
            		break;
            	}
            }
            return csv;
        }catch(Exception e){
            throw new CsvReaderException(e);
        }finally{
            if(buff != null){
                try{
                    buff.close();
                    buff = null;
                }catch(Exception e){
                    logger.log(Level.WARNING,"Unable to close underlying reader", e);
                }
            }
        }
    }

    public static List<String> readLine(String line, char delimiter, char quote) {
        if(line == null)
            line = "";
        if(delimiter == ' ')
            delimiter = CsvCons.COMMA;
        if(quote == ' ')
            quote = CsvCons.DOUBLE_QUOTE;

        List<String> values = new LinkedList<String>();
        char[] chars = line.toCharArray();

        StringBuffer value = new StringBuffer("");
        boolean quoted = false;

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(CsvCons.CR == c)
                continue;

            if(CsvCons.LF == c) {
                values.add(value.toString());
                value = new StringBuffer();
                break;
            }

            if(quoted) {
                if(quote == c) {
                    if((i+1) < chars.length && chars[i+1] == quote) {
                        value.append(quote);
                        i++;
                    }else {
                        quoted = false;
                    }
                }else if(delimiter == c) {
                    value.append(c);
                }else {
                    value.append(c);
                }
            }else {
                if(quote == c) {
                    quoted = true;
                    if(
                            ((i+1) < chars.length && chars[i+1] == delimiter) &&
                                    ((i-1) >= 0   && chars[i-1] != delimiter)
                            ) {
                        quoted = false;
                        value.append(c);
                    }

                } else if(delimiter == c) {
                    values.add(value.toString());
                    value = new StringBuffer();
                } else{
                    value.append(c);
                }
            }

        }

        if(value.length()>0) {
            values.add(value.toString());
        }

        return values;
    }

}

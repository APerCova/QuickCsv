package net.apercova.quickcsv.reader;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.apercova.io.IterableLineNumberReader;
import net.apercova.quickcsv.CsvCons;

public abstract class AbstractCsvReader<T> implements CsvReader<T>{

	private static final Logger logger = Logger.getLogger(AbstractCsvReader.class.getName());
	
	protected Reader reader;
	protected char delimiter;
	protected char quote;
	protected boolean escapeHeader;
	protected long fromLine;
	protected long maxLines;

	protected AbstractCsvReader() {
		super();
		delimiter = CsvCons.COMMA;
		quote = CsvCons.DOUBLE_QUOTE;
		escapeHeader = false;
		fromLine = 0;
		maxLines = 0;
	}
	protected AbstractCsvReader(Reader reader) {
		super();
		setReader(reader);
		delimiter = CsvCons.COMMA;
		quote = CsvCons.DOUBLE_QUOTE;
		escapeHeader = false;
		fromLine = 0;
		maxLines = 0;
	}
	
	public CsvReader<T> setDelimiter(char delimiter){
		this.delimiter = delimiter;
		return this;
	}
	public CsvReader<T> setQuote(char quote){
		this.quote = quote;
		return this;
	}
	public CsvReader<T> setReader(Reader reader){
		if( !(reader instanceof IterableLineNumberReader)) {
			reader = new IterableLineNumberReader(reader);
		}
		this.reader = reader;
		return this;
	}
	public CsvReader<T> fromLine(long fromLine){
		this.fromLine = fromLine;
		return this;
	}
	public CsvReader<T> maxLines(long maxLines){
		this.maxLines = maxLines;
		return this;
	}
	public CsvReader<T> escapeheader(boolean escapeHeader){
		this.escapeHeader = escapeHeader;
		return this;
	}
	public boolean isHeaderEscaped() {
		return escapeHeader;
	}
	public int getLineNumber() {
		return ((IterableLineNumberReader) reader).getLineNumber();
	}
	public List<Throwable> getSuppressed(){
		return ((IterableLineNumberReader) reader).getSuppressed();
	}
	public void close() throws IOException {
		if(reader != null) {
			reader.close();
		}
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

	/**
	 * Not implemented by default
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	
}

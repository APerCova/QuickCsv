package net.apercova.quickcsv.reader;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import net.apercova.io.IterableLineNumberReader;
import net.apercova.quickcsv.CsvCons;

public abstract class AbstractCsvReader<T> implements CsvReader<T>{

	private static final Logger logger = Logger.getLogger(AbstractCsvReader.class.getName());
	
	protected Reader reader;
	protected char delimiter;
	protected char quote;
	protected boolean skipHeader;
	protected long fromLine;
	protected long maxLines;

	protected AbstractCsvReader() {
		this(null, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
	}
	protected AbstractCsvReader(Reader reader) {
		this(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
	}
	protected AbstractCsvReader(Reader reader, char delimiter) {
		this(reader, delimiter, CsvCons.DOUBLE_QUOTE);
	}
	protected AbstractCsvReader(Reader reader, char delimiter, char quote) {
		super();
		setReader(reader);
		this.delimiter = delimiter;
		this.quote = quote;
		skipHeader = false;
		fromLine = 0;
		maxLines = 0;
	}

	public CsvReader<T> setDelimiter(char delimiter){
		this.delimiter = delimiter;
		return this;
	}
	public char getDelimiter() {
		return delimiter;
	}
	public CsvReader<T> setQuote(char quote){
		this.quote = quote;
		return this;
	}
	public char getQuote() {
		return quote;
	}
	public CsvReader<T> setReader(Reader reader){
		if(reader != null && !(reader instanceof IterableLineNumberReader)) {
			reader = new IterableLineNumberReader(reader);
		}
		this.reader = reader;
		return this;
	}
	public Reader getReader() {
		return reader;
	}
	public CsvReader<T> fromLine(long fromLine){
		this.fromLine = fromLine;
		return this;
	}
	public CsvReader<T> maxLines(long maxLines){
		this.maxLines = maxLines;
		return this;
	}
	public CsvReader<T> skipHeader(boolean skipHeader){
		this.skipHeader = skipHeader;
		return this;
	}
	public boolean skipHeader() {
		return skipHeader;
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
	 * Not supported.
	 */
	public void remove() {
		logger.warning("java.util.Iterator#remove() not supported");
		throw new UnsupportedOperationException("java.util.Iterator#remove() not supported");
	}
	
	
}

package net.apercova.quickcsv;

import java.io.Closeable;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.apercova.io.IterableLineNumberReader;

/**
 * Reader for Comma-Separated values in compliance with RFC 4180
 * plus custom delimiter and quoted values.
 * <pre>
 *  Common Format and MIME Type for Comma-Separated Values (CSV) Files
 *  <a href="https://tools.ietf.org/html/rfc4180" target="_blank">https://tools.ietf.org/html/rfc4180</a>
 * </pre>
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @since 1.0
 */
public class CsvReader implements Closeable, Iterator<List<String>>, Iterable<List<String>>{

    private static Logger logger = Logger.getLogger(CsvReader.class.getCanonicalName());
    protected Reader reader;
    protected char delimiter;
    protected char quote;
    protected long fromLine;
    protected long maxLines;

    public CsvReader(){
        delimiter = CsvCons.COMMA;
        quote = CsvCons.DOUBLE_QUOTE;
    }

    /**
     * Constructor accepting source reader.
     * @param reader Source reader.
     */
    public CsvReader(Reader reader){
        this.reader = new IterableLineNumberReader(reader);
        delimiter = CsvCons.COMMA;
        quote = CsvCons.DOUBLE_QUOTE;
    }

    /**
     * Sets source reader.
     * @param reader Source reader.
     * @return Csv Reader
     */
    public CsvReader from(Reader reader){
        this.reader = new IterableLineNumberReader(reader);
        return this;
    }

    /**
     * Sets custom delimiter.
     * @param delimiter Csv delimiter.
     * @return Csv Reader.
     */
    public CsvReader withDelimiter(char delimiter){
        this.delimiter = delimiter;
        return this;
    }

    /**
     * Sets custom quote.
     * @param quote Csv quote.
     * @return Csv Reader.
     */
    public CsvReader withQuote(char quote){
        this.quote = quote;
        return this;
    }

    /**
     * Sets line index to read from.
     * @param fromLine 1-based line index as beginning of reading.
     */
    public void fromLine(long fromLine) {
		this.fromLine = fromLine;
	}
    
    /**
     * Sets max number of lines to read.
     * @param maxLines limit the number of lines to read.
     */
    public void maxLines(long maxLines) {
		this.maxLines = maxLines;
	}
    
    /**
     * Reads a CSV-like document with current configuration.
     * @return List of read Csv lines.
     * @throws CsvReaderException If an error happens whilst reading.
     */
    public List<List<String>> read() throws CsvReaderException{
        return CsvReader.read(reader, delimiter, quote, fromLine, maxLines);
    }

    /**
     * Core implementation for reading CSV-values from a {@link java.io.Reader}.
     * Default delimiter and quote chars are used in compliance with RFC 4180.
     * @param reader Source for reading.
     * @return List of read Csv lines.
     * @throws CsvReaderException If an error happens whilst reading.
     */
    public static List<List<String>> read(Reader reader) throws CsvReaderException{
        return read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
    }

    /**
     * Core implementation for reading CSV-values from a {@link java.io.Reader}
     * @param reader Source for reading.
     * @param delimiter Delimiter character. Default is a comma(,).
     * @param quote Quote character. Default is a double quote char(").
     * @return List of read Csv lines.
     * @throws CsvReaderException If an error happens whilst reading.
     */
    public static List<List<String>> read(Reader reader, char delimiter, char quote) throws CsvReaderException{
    	return read(reader, delimiter, quote, 0, 0);
    }
    
    /**
     * Core implementation for reading CSV-values from a {@link java.io.Reader}
     * @param reader Source for reading.
     * @param delimiter Delimiter character. Default is a comma(,).
     * @param quote Quote character. Default is a double quote char(").
     * @param fromLine 1-based line index as beginning of reading.
     * @return List of read Csv lines.
     * @throws CsvReaderException If an error happens whilst reading.
     */
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine) throws CsvReaderException{
    	return read(reader, delimiter, quote, fromLine, 0);
    }
    
    /**
     * Core implementation for reading CSV-values from a {@link java.io.Reader}
     * @param reader Source for reading.
     * @param delimiter Delimiter character. Default is a comma(,).
     * @param quote Quote character. Default is a double quote char(").
     * @param fromLine 1-based line index as beginning of reading.
     * @param maxLines limit the number of lines to read.
     * @return List of read Csv lines.
     * @throws CsvReaderException If an error happens whilst reading.
     */
    public static List<List<String>> read(Reader reader, char delimiter, char quote, long fromLine, long maxLines) throws CsvReaderException{
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
            z:
            while(buff.hasNext()) {
            	if(buff.getLineNumber() >= fromLine) {   
        			csv.add(readLine(buff.next(), delimiter, quote));
        			lineCount ++;
        			if( (0 < maxLines) && (maxLines <= lineCount) ) {
        				break z;
        			}
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

    
    /**
     * Core implementation for reading CSV-values within text line.
     * @param line Csv text line
     * @param delimiter Delimiter character. Default is a comma(,).
     * @param quote Quote character. Default is a double quote char(").
     * @return List of read Csv values.
     */
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
     * Retrieves the delimiter char used for this writer.
     * @return delimiter char.
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * Retrieves the quotation char used for this writer.
     * @return quotation char.
     */
    public char getQuote() {
        return quote;
    }

    public void close() throws IOException {
        if(reader != null){
            reader.close();
        }
    }

    /**
     * Returns {@code true} if there's more lines to be read.
     * (In other words, returns {@code true} if {@link #getNextLine()} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if there's more lines to read
     */
    public boolean hasNext() {
        return ((IterableLineNumberReader) reader).hasNext();
    }

    /**
     * Returns the next line read.
     *
     * @return Value of the next line read
     * @throws NoSuchElementException if there's no more lines to be read.
     */
    public List<String> next() {
        return CsvReader.readLine(((IterableLineNumberReader) reader).next(), delimiter, quote);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns {@code true} if there's more lines to be read.
     * (In other words, returns {@code true} if {@link #getNextLine()} would
     * return an element rather than throwing an exception.)
     * Convenience alias for {@link #hasNext()}
     * @return {@code true} if there's more lines to be read
     */
    public boolean hasMoreLines() {
        return hasNext();
    }

    /**
     * Returns the next line read.
     * Convenience alias for {@link #next()}
     * @return Value of the next line read
     * @throws NoSuchElementException if there's no more lines to be read.
     */
    public List<String> getNextLine() {
        return next();
    }

    /**
     * Get the current line number.
     * @return  The current line number
     * @see LineNumberReader#getLineNumber
     */
    public int getLineNumber(){
        return ((IterableLineNumberReader) reader).getLineNumber();
    }

    /**
     * Exception list caused by reading errors when iterating.
     * {@link Iterator#next()} does not declare a {@code throws} statement.
     * @return Suppressed exceptions list.
     */
    public List<Throwable> getSuppressed(){
        if(reader == null)
            return null;
        return ((IterableLineNumberReader)reader).getSuppressed();
    }

    /**
     * Returns an instance of this {@link CsvReader} as {@link Iterator}.
     * @return Iterator.
     */
    public Iterator<List<String>> iterator() {
        return this;
    }
}

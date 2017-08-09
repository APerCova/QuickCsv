package com.apercova.quickcsv;

import java.io.*;
import java.util.List;

/**
 * Writer for Comma-Separated values in compliance with RFC 4180
 * plus custom delimiter and quoted values.
 * <pre>
 *  Common Format and MIME Type for Comma-Separated Values (CSV) Files
 *  <a href="https://tools.ietf.org/html/rfc4180" target="_blank">https://tools.ietf.org/html/rfc4180</a>
 * </pre>
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @version 1.0 2017.08
 */
public class CsvWriter implements Closeable {

    protected Writer writer;
    protected List<List<String>> values;
    protected char delimiter;
    protected char quote;
    protected boolean autoflush;

    public CsvWriter(){
        delimiter = CsvCons.COMMA;
        quote = CsvCons.DOUBLE_QUOTE;
        autoflush = false;
    }

    /**
     * Constructor accepting target writer and source values.
     * @param writer Target writer.
     * @param values Ssource csv values list to write.
     */
    public CsvWriter(Writer writer, List<List<String>> values){
        this.writer = writer;
        this.values = values;
        delimiter = CsvCons.COMMA;
        quote = CsvCons.DOUBLE_QUOTE;
        autoflush = false;
    }

    /**
     * Sets target writer.
     * @param writer Target writer.
     * @return Csv Writer.
     */
    public CsvWriter to(Writer writer){
        this.writer = writer;
        return this;
    }

    /**
     * Sets source csv values list to write.
     * @param values Source csv values.
     * @return Csv Writer.
     */
    public CsvWriter withValues(List<List<String>> values){
        this.values = values;
        return this;
    }

    /**
     * Sets custom delimiter.
     * @param delimiter Csv delimiter.
     * @return Csv Writer.
     */
    public CsvWriter withDelimiter(char delimiter){
        this.delimiter = delimiter != ' '? delimiter: CsvCons.COMMA;
        return this;
    }

    /**
     * Sets custom quote.
     * @param quote Csv quote.
     * @return Csv Writer.
     */
    public CsvWriter withQuote(char quote){
        this.quote = quote != ' '? quote: CsvCons.DOUBLE_QUOTE;
        return this;
    }

    /**
     * Sets auto-flush flag for underlying writer.
     * @param autoflush Auto-flush flag
     * @return Csv Writer.
     */
    public CsvWriter setAutoFlush(boolean autoflush){
        this.autoflush = autoflush;
        return this;
    }

    /**
     * Writes a Csv-like document with current configuration.
     * @throws CsvWriterException If an error happens whilst writing.
     */
    public void write() throws CsvWriterException{
        CsvWriter.write(writer, values, delimiter, quote, autoflush);
    }

    /**
     * Core implementation for writing csv-values from a list of values.
     * @param writer Destination for writing.
     * @param values List of csv values.
     * @throws CsvWriterException If an error happens whilst writing.
     */
    public static void write(Writer writer, List<List<String>> values)
            throws CsvWriterException{
        write(writer, values, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false);
    }

    /**
     * Core implementation for writing csv-values from a list of values.
     * @param writer Destination for writing.
     * @param values List of csv values.
     * @param autoflush Defines if destination writer should be flushed.
     * @throws CsvWriterException If an error happens whilst writing.
     */
    public static void write(Writer writer, List<List<String>> values, boolean autoflush)
            throws CsvWriterException{
        write(writer, values, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, autoflush);
    }
    
    /**
     * Core implementation for writing csv-values from a list of values.
     * @param writer Destination for writing.
     * @param values List of csv values.
     * @param delimiter Delimiter character. Default is a comma(,).
     * @param quote Quote character. Default is a double quote char(").
     * @throws CsvWriterException If an error happens whilst writing.
     */
    public static void write(Writer writer, List<List<String>> values, char delimiter, char quote)
            throws CsvWriterException{
        write(writer, values, delimiter,quote, false);
    }

    /**
     * Core implementation for writing csv-values from a list of values.
     * @param writer Destination for writing.
     * @param values List of csv values.
     * @param delimiter Delimiter character. Default is a comma(,).
     * @param quote Quote character. Default is a double quote char(").
     * @param autoflush Defines if destination writer should be flushed.
     * @throws CsvWriterException If an error happens whilst writing.
     */
    public static void write(Writer writer, List<List<String>> values, char delimiter, char quote, boolean autoflush)
            throws CsvWriterException{
        if(writer == null){
            throw new CsvWriterException("missing writer",
                    new IllegalArgumentException(new NullPointerException("writer")));
        }

        if(delimiter == ' ')
            delimiter = CsvCons.COMMA;

        if(quote == ' ')
            quote = CsvCons.DOUBLE_QUOTE;

        try{
            if(values != null && !values.isEmpty()) {
                for (List<String> line : values) {
                    for (int c = 0; c < line.size(); c++) {
                        writer.write(formatValue(line.get(c), delimiter, quote));
                        if ((c + 1) < line.size()) {
                            writer.write(delimiter);
                        }
                    }
                    writer.write(System.getProperty("line.separator"));
                }
                if(autoflush)
                    writer.flush();
            }
        }catch(Exception e){
            throw new CsvWriterException(e.getMessage(), e);
        }
    }

    /**
     * Format a text value as in RFC 4180.
     * @param value Text value.
     * @param delimiter Delimiter character. Default is a comma(,).
     * @param quote Quote character. Default is a double quote char(").
     * @return Csv formated text value.
     */
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
        if(writer != null)
            writer.close();
    }

}

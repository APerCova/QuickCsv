package com.apercova.quickcsv.usecases.writer;

import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

public class SingleClassCsvWriter {

	public static final Logger logger = Logger.getLogger(SingleClassCsvWriter.class.getCanonicalName());
	
	public static final char COMMA = ',';
	public static final char DOUBLE_QUOTE = '"';
	
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
            throws Exception{
        if(writer == null){
            throw new Exception("missing writer",
                    new IllegalArgumentException(new NullPointerException("writer")));
        }

        if(delimiter == ' ')
            delimiter = COMMA;

        if(quote == ' ')
            quote = DOUBLE_QUOTE;

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
            logger.severe(e.getMessage());
        	throw e;
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
            delimiter = COMMA;

        if(quote == ' ')
            quote = DOUBLE_QUOTE;

        if((!value.contains(""+delimiter)) && (!value.contains(""+quote))){
            return value;
        }

        value = value.replaceAll("" + quote, quote + "" + quote);
        value = quote+""+value+""+quote;

        return value;
    }

}

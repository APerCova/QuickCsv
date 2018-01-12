package net.apercova.quickcsv;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public class SimpleCsvWriter extends AbstractCsvWriter<List<String>> {
	
	
	
	public SimpleCsvWriter() {
		super();
	}
	
	public SimpleCsvWriter(Writer writer) {
		super(writer);
	}

	public SimpleCsvWriter(Writer writer, Collection<List<String>> lines) {
		super(writer, lines);
	}
	
	public void write() throws CsvWriterException {
		SimpleCsvWriter.write(writer, lines, delimiter, quote, escapeHeader);
	}

	public static void write(Writer writer, Collection<List<String>> lines) 
    		throws CsvWriterException {
    	write(writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false);
    }
    
    public static void write(Writer writer, Collection<List<String>> lines, boolean escapeHeader) 
    		throws CsvWriterException {
    	write(writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader);
    }
    
    public static void write(Writer writer, Collection<List<String>> lines, char delimiter) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, false);
    }
    
    public static void write(Writer writer, Collection<List<String>> lines, char delimiter, boolean escapeHeader) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader);
    }
    
    public static void write(Writer writer, Collection<List<String>> lines, char delimiter, char quote) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, quote, false);
    }
	
	public static void write(Writer writer, Collection<List<String>> lines, char delimiter, char quote, boolean escapeHeader)
            throws CsvWriterException{
        if(writer == null){
            throw new CsvWriterException("missing writer",
                    new IllegalArgumentException(new NullPointerException("writer")));
        }

        if(delimiter == ' ')
            delimiter = CsvCons.COMMA;

        if(quote == ' ')
            quote = CsvCons.DOUBLE_QUOTE;

        
        if(lines != null && !lines.isEmpty()) {
        	int lineCount = 1;
        	for (List<String> line : lines) {
        		
        		if((lineCount == 1 && !escapeHeader) || lineCount > 1 ) {
        			writeLine(writer, line, delimiter, quote);
        		}
                lineCount++;
            }
        }
    }
	
	public static void writeLine(Writer writer, List<String> line, char delimiter, char quote) throws CsvWriterException {
		try {
			for (int c = 0; c < line.size(); c++) {
	            writer.write(formatValue(line.get(c), delimiter, quote));
	            if ((c + 1) < line.size()) {
	                writer.write(delimiter);
	            }
	        }
	        writer.write(System.getProperty("line.separator"));
		} catch (IOException e) {
			throw new CsvWriterException(e.getMessage(), e);
		}
		
	}
	
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
	
}

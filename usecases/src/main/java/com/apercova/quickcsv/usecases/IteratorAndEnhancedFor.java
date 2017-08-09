package com.apercova.quickcsv.usecases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.apercova.quickcsv.CsvReader;

public class IteratorAndEnhancedFor {
	private static final Logger logger = Logger.getLogger(IteratorAndEnhancedFor.class.getCanonicalName());
	
	public void readIterator() {
		
	Reader reader = null;
	CsvReader csvReader = null;
    try {
    	//Getting a reader for CsvFile.csv
        reader = new InputStreamReader(
                new FileInputStream("CsvFile.csv"), 
                Charset.forName("utf-8"));
        
        csvReader = new CsvReader(reader);
        
        //Max number of rows to be read
        int maxRead = 2000;
        
        //Iterator example
        int c = 0;
        while(csvReader.hasNext() && c++ <= maxRead) {
        	
        	//Get next row/line from reader
        	List<String> nextRow = csvReader.next();
        	
        	//Get row/line number from reader
        	int rowNum = csvReader.getLineNumber();
        	
        	logger.info(String.format("Row #%d : %s", rowNum, nextRow));
        }
        
        
        //Get suppressed exceptions
        List<Throwable> suppressed = csvReader.getSuppressed();
        
        
    } catch (IOException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
        
    } finally {
        try {
        	if(reader != null)
        		reader.close();
        	if(csvReader != null)
        		csvReader.close();
            reader = null;
            csvReader = null;
        } catch (IOException e) {
            logger.log(Level.FINE, "Unable to close reader", e);	
        }
    }
	}
	
	public void readForLoop() {
		
	Reader reader = null;
	CsvReader csvReader = null;
    try {
    	//Getting a reader for CsvFile.csv
        reader = new InputStreamReader(
                new FileInputStream("CsvFile.csv"), 
                Charset.forName("utf-8"));
        
        csvReader = new CsvReader(reader);
        
        //Max number of rows to be read
        int maxRead = 2000;
        
        //Enhanced for loop example
        int c = 0;
        for(List<String> nextRow: csvReader) {
        	if(c++ > maxRead)
        		break;
        	
        	//Get row/line number from reader
        	int rowNum = csvReader.getLineNumber();
        	
        	       
        }
        
        //Get suppressed exceptions
        List<Throwable> suppressed = csvReader.getSuppressed();
        
        
    } catch (IOException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
        
    } finally {
        try {
        	if(reader != null)
        		reader.close();
        	if(csvReader != null)
        		csvReader.close();
            reader = null;
            csvReader = null;
        } catch (IOException e) {
            logger.log(Level.FINE, "Unable to close reader", e);	
        }
    }
}

}

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

public class ProcessAndForget {
	private static final Logger logger = Logger.getLogger(ProcessAndForget.class.getCanonicalName());
	
	public void read() {
		
	Reader reader = null;
	CsvReader csvReader = null;
    try {
    	//Getting a reader for VeryBigFile.csv
        reader = new InputStreamReader(
                new FileInputStream("VeryBigFile.csv"), 
                Charset.forName("utf-8"));
        
        csvReader = new CsvReader(reader);
        
        //Single reference for next read row!
        List<String> nextRow = null;
        
        //Using an iterator lets you read subsequent rows on demand instead of whole file
        while(csvReader.hasNext()) {
        	
        	nextRow = csvReader.next();
        	
        	//Get row/line number from reader
        	int rowNum = csvReader.getLineNumber();
        	
        	logger.info(String.format("Row #%d : %s", rowNum, nextRow));
        	processRowAndForget(nextRow);
        }
        
        
        //Get suppressed exceptions
        List<Throwable> suppressed = csvReader.getSuppressed();
        
        
    } catch (IOException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
        
    } finally {
        try {
        	if(reader != null)
        		reader.close();
            reader = null;
        } catch (IOException e) {
            logger.log(Level.FINE, "Unable to close reader", e);	
        }
    }
	}
	
	public void processRowAndForget(List<String> row) {
		//Process Csv row
	};
}

package com.apercova.quickcsv.usecases.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.apercova.quickcsv.CsvReader;
import com.apercova.quickcsv.CsvReaderException;

public class QuickReading {

	private static final Logger logger = Logger.getLogger(QuickReading.class.getCanonicalName());
	
	public void read() {
		
		Reader reader = null;
	    try {
	    	//Getting a reader for CsvFile.csv
	        reader = new InputStreamReader(
	                new FileInputStream("CsvFile.csv"), 
	                Charset.forName("utf-8"));
	        
	        //Values are read as a row list
	        List<List<String>> values = CsvReader.read(reader);
	        
	    } catch (IOException e) {
	        logger.log(Level.SEVERE, "Can't perform reading", e);
	        
	    } catch (CsvReaderException e) {
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
}

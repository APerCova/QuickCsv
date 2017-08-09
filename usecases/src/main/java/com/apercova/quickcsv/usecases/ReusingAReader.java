package com.apercova.quickcsv.usecases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.apercova.quickcsv.CsvCons;
import com.apercova.quickcsv.CsvReader;
import com.apercova.quickcsv.CsvReaderException;

public class ReusingAReader {

	private static final Logger logger = Logger.getLogger(ReusingAReader.class.getCanonicalName());
	
	public void read() {
	
		//Getting a CsvReader
		CsvReader csvreader = new CsvReader();
	
	    Reader reader = null;
	    
	    try {
	    	
	    	//Getting a reader for CsvFileOne.csv
	        reader = new InputStreamReader(
	                new FileInputStream("CsvFileOne.csv"), 
	                Charset.forName("utf-8"));
	        
	        
	        csvreader.from(reader);
	        
	        //Getting CsvFileOne value list
	        List<List<String>> CsvFileOneValues = csvreader.read();
	        
	        	        
	        //Getting a reader for CsvFileTwo.csv
	        reader = new InputStreamReader(
	                new FileInputStream("CsvFileTwo.csv"), 
	                Charset.forName("utf-8"));
	        
	        //Customizing for further read
	        csvreader
	        .from(reader)
	        .withDelimiter(CsvCons.PIPE)
	        .withQuote(CsvCons.SINGLE_QUOTE);
	        
	        //Getting CsvFileTwo value list
	        List<List<String>> CsvFileTwoValues = csvreader.read();
	        
	    } catch (IOException e) {
	        logger.log(Level.SEVERE, "Can't perform reading", e);
	        
	    } catch (CsvReaderException e) {
	        logger.log(Level.SEVERE, "Can't perform reading", e);
	        
	    } finally {
	        try {
			if(csvreader != null)
				csvreader.close();
			if(reader != null)
				reader.close();
			
			csvreader = null;
			reader = null;
	        } catch (IOException e) {
	            logger.log(Level.FINE, "Unable to close reader", e);	
	        }
	    }
	}
}

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

public class CustomDelimQuote {

	private static final Logger logger = Logger.getLogger(CustomDelimQuote.class.getCanonicalName());
	
	public void read() {
		
		Reader reader = null;
	    try {
	    	//Getting a reader for CsvFile.csv
	        reader = new InputStreamReader(
	                new FileInputStream("CsvFile.csv"), 
	                Charset.forName("utf-8"));
	        
	        //Custom delimiter an quotation chars from CsvCons
	        List<List<String>> values = CsvReader.read(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
	        
	        //Custom delimiter an quotation chars from char values
	        values = CsvReader.read(reader, '|', '\'');
	        
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

package net.apercova.quickcsv.usecases.writer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.apercova.quickcsv.CsvWriter;
import net.apercova.quickcsv.CsvWriterException;

public class ReusingAWriter {

	private static final Logger logger = Logger.getLogger(ReusingAWriter.class.getCanonicalName());
	
	public void write() {
	    Writer writer = null;
	    
	    //Unique CsvWriter for reusing
	    CsvWriter csvWriter = new CsvWriter();
	    try {
	    	//Getting a writer for Countries.csv
	        writer = new BufferedWriter(new OutputStreamWriter(
	                new FileOutputStream("Countries.csv"), 
	                Charset.forName("utf-8")));
	        
	        List<List<String>> values = new LinkedList<List<String>>();
	        values.add(Arrays.asList(new String[] {"ISO_CODE","NAME","CAPITAL"}));
	        values.add(Arrays.asList(new String[] {"US","United States of America",""}));
	        values.add(Arrays.asList(new String[] {"MX","Estados Unidos Mexicanos","Ciudad de México, \"CDMX\""}));
	        values.add(Arrays.asList(new String[] {"AU","Austalia","Sidney"}));
	        
	        //Configuring CsvWriter
	        csvWriter.to(writer)
	                 .withLines(values)
	                 .setAutoFlush(true);
	        
	        //Writing out values
	        csvWriter.write();
	        
	        
	        //Getting a writer for Countries2.csv
	        writer = new BufferedWriter(new OutputStreamWriter(
	                new FileOutputStream("Countries2.csv"), 
	                Charset.forName("utf-8")));
	        
	        //Configuring CsvWiter (Reusing with diferent writer)
	        csvWriter.to(writer)
	                 .withLines(values)
	                 .setAutoFlush(true);
	        
	        //Writing out values
	        csvWriter.write();
	        
	        
	    } catch(IOException e) {
	        logger.log(Level.SEVERE, "Can't perform reading", e);
	    } catch (CsvWriterException e) {
	        logger.log(Level.SEVERE, "Can't perform reading", e);
	    } finally {
	        try {
	        	csvWriter.close();
	        	csvWriter = null;
	        	if(writer != null)
	        		writer.close();
	            writer = null;
	        } catch (IOException e) {
	            logger.log(Level.FINE, "Failed to close resource", e);	
	        }
	    }

	}
	
}

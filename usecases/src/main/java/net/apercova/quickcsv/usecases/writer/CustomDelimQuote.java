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

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvWriter;
import net.apercova.quickcsv.CsvWriterException;

public class CustomDelimQuote {

	private static final Logger logger = Logger.getLogger(CustomDelimQuote.class.getCanonicalName());
	
	public void write() {
	    Writer writer = null;
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
	        
	        //Writing out values
	        //Custom delimiter an quotation chars from CsvCons
	        CsvWriter.write(writer, values, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
	        writer.flush();
	        
	        //Custom delimiter an quotation chars from char values
	        CsvWriter.write(writer, values, '|', '\'');
	        writer.flush();
	        
	        
	    } catch(IOException e) {
	        logger.log(Level.SEVERE, "Can't perform reading", e);
	    } catch (CsvWriterException e) {
	        logger.log(Level.SEVERE, "Can't perform reading", e);
	    } finally {
	        try {
	        	if(writer != null)
	        		writer.close();
	            writer = null;
	        } catch (IOException e) {
	            logger.log(Level.FINE, "Failed to close resource", e);	
	        }
	    }

	}
}

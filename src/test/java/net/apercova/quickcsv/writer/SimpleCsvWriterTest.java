<<<<<<< HEAD
package net.apercova.quickcsv.writer;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvWriterFactory;

public class SimpleCsvWriterTest {

	private static final Logger logger = Logger.getLogger(SimpleCsvWriterTest.class.getName());
	private Collection<Collection<String>> csvValues;
	
	@Before
	public void init() {
		csvValues = new ArrayList<Collection<String>>();
        csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
        csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
        csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "Ciudad de México, \"CDMX\"", "MX"}));
        csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
        csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
        csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));
	}
	
    @Test
    public void Rfc4180Test() throws Exception {
    	
        CsvWriter<Collection<String>> csvWriter = CsvWriterFactory.newInstance();
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setLines(csvValues)
                .autoflush(true)
                .write();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.COMMA));
        writer.close();
        logger.info("Rfc4180Test completed successfully");
        
    }

    @Test
    public void Rfc4180EscapeHeaderTest() throws Exception {
    	
        CsvWriter<Collection<String>> csvWriter = CsvWriterFactory.newInstance();
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setLines(csvValues)
                .escapeHeader(true)
                .autoflush(true)
                .write();
        
        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"+CsvCons.COMMA));
        writer.close();
        logger.info("Rfc4180EscapeHeaderTest completed successfully");
        
    }
    
    @Test
    public void Rfc4180AddLineTest() throws Exception {
    	
        CsvWriter<Collection<String>> csvWriter = CsvWriterFactory.newInstance();
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setLines(csvValues)
                .autoflush(true)
                .addLine(Arrays.asList("India", "New Delhi", "IN"))
                .write();
        
        List<Collection<String>> allLines = (List<Collection<String>>) csvWriter.getLines();
        List<String> rec = (List<String>) allLines.get(allLines.size() - 1);
        Assert.assertEquals("New Delhi", rec.get(1));
        
        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.trim().endsWith("IN"));
        writer.close();
        logger.info("Rfc4180AddLineTest completed successfully");
        
    }

    @Test
    public void customTest() throws Exception {
    	
        CsvWriter<Collection<String>> csvWriter = CsvWriterFactory.newInstance();
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(csvValues)
                .autoflush(true)
                .write();
        
        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.PIPE));
        writer.close();
        logger.info("customTest completed successfully");
        
    }

    @Test
    public void customEscapeHeaderTest() throws Exception {
    	
        CsvWriter<Collection<String>> csvWriter = CsvWriterFactory.newInstance();
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(csvValues)
                .escapeHeader(true)
                .autoflush(true)
                .write();
        
        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"+CsvCons.PIPE));
        writer.close();
        logger.info("customEscapeHeaderTest completed successfully");
        
    }

}
=======
package net.apercova.quickcsv;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultCsvWriterTest {

    @Test
    public void write() throws Exception {
        System.out.println("========== CsvWriterTest#write()==========");

        Writer writer = null;
        CsvWriter<List<String>> csvWriter = new DefaultCsvWriter();
        try {
            
            List<List<String>> csvValues = new ArrayList<List<String>>();
            csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
            csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
            csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "Ciudad de México, \"CDMX\"", "MX"}));
            csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
            csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
            csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));

            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setLines(csvValues)
                    .write();

            String result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: complete writing", result.startsWith("NAME"+CsvCons.COMMA));
            writer.close();
            
            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setDelimiter(CsvCons.COMMA)
                    .setQuote(CsvCons.DOUBLE_QUOTE)
                    .setLines(csvValues)
                    .escapeHeader(true)
                    .setAutoflush(true)
                    .write();
            
            result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: escape header", result.startsWith("United States of America"+CsvCons.COMMA));
            writer.close();
            
            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setDelimiter(CsvCons.PIPE)
                    .setLines(csvValues)
                    .escapeHeader(true)
                    .setAutoflush(true)
                    .write();
            
            result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: custom delimiter", result.startsWith("United States of America"+CsvCons.PIPE));
            writer.close();
            
            

        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
            	if(csvWriter != null) {
            		csvWriter.close();
            	}
                if(writer != null) {
                	writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("========================================================");
    }

}
>>>>>>> branch 'interface-refactor' of https://github.com/apercova/QuickCsv.git

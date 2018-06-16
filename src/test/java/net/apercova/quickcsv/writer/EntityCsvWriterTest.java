<<<<<<< HEAD
package net.apercova.quickcsv.writer;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvWriterFactory;
import net.apercova.quickcsv.entity.Country;

public class EntityCsvWriterTest {

	private static final Logger logger = Logger.getLogger(SimpleCsvWriterTest.class.getName());
	private Collection<Country> countries;
	
	@Before
	public void init() {
		countries = new ArrayList<Country>();
    	countries.add(new Country("United States of America","Washington D.C.","US"));
    	countries.add(new Country("Estados Unidos Mexicanos","Ciudad de México, \"CDMX\"","MX"));
    	countries.add(new Country("Australia","Sidney","AU"));
    	countries.add(new Country("United Kingdom","London","OK"));
    	countries.add(new Country("Belize","Belmopán","BZ"));
	}
	
    @Test
    public void Rfc4180Test() throws Exception {
    	
        CsvWriter<Country> csvWriter = CsvWriterFactory.newInstance(Country.class);
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setLines(countries)
                .autoflush(true)
                .write();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.COMMA));
        writer.close();
        logger.info("Rfc4180Test completed successfully");
        
    }

    @Test
    public void Rfc4180EscapeHeaderTest() throws Exception {
    	
        CsvWriter<Country> csvWriter = CsvWriterFactory.newInstance(Country.class);
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setLines(countries)
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
    	
        CsvWriter<Country> csvWriter = CsvWriterFactory.newInstance(Country.class);
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setLines(countries)
                .autoflush(true)
                .addLine(new Country("India", "New Delhi", "IN"))
                .write();
        
        ArrayList<Country> allLines = (ArrayList<Country>) csvWriter.getLines();
        Assert.assertEquals("New Delhi", allLines.get(allLines.size() - 1).getCapital());
        
        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.trim().endsWith("IN"));
        writer.close();
        logger.info("Rfc4180AddLineTest completed successfully");
        
    }

    @Test
    public void customTest() throws Exception {
    	
        CsvWriter<Country> csvWriter = CsvWriterFactory.newInstance(Country.class);
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(countries)
                .autoflush(true)
                .write();
        
        String result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.PIPE));
        writer.close();
        logger.info("customTest completed successfully");
        
    }

    @Test
    public void customEscapeHeaderTest() throws Exception {
    	
        CsvWriter<Country> csvWriter = CsvWriterFactory.newInstance(Country.class);
        
        Writer writer = new StringWriter();
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(countries)
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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.apercova.quickcsv.entity.Country;

public class EntityCsvWriterTest {

    @Test
    public void test01() throws Exception {
        System.out.println("========== ObjectCsvWriterTest#test01()==========");

        Writer writer = null;
        CsvWriter<Country> csvWriter = EntityCsvWriter.getInstance(Country.class);
        try {
        	List<Country> countries = new ArrayList<Country>();
        	countries.add(new Country("United States of America","Washington D.C.","US"));
        	countries.add(new Country("Estados Unidos Mexicanos","Ciudad de México, \"CDMX\"","MX"));
        	countries.add(new Country("Australia","Sidney","AU"));
        	countries.add(new Country("United Kingdom","London","OK"));
        	countries.add(new Country("Belize","Belmopán","BZ"));

            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setLines(countries)
                    .write();

            String result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: complete writing", result.startsWith("NAME"+CsvCons.COMMA));
            writer.close();
            
            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setDelimiter(CsvCons.COMMA)
                    .setQuote(CsvCons.DOUBLE_QUOTE)
                    .setLines(countries)
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
                    .setLines(countries)
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

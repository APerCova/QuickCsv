package net.apercova.quickcsv.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvWriterFactory;
import net.apercova.quickcsv.entity.Country;

public class CsvEntityWriterTest {

	private Collection<Country> countries;
	private CsvWriter<Country> csvWriter;
	private Writer writer;
	private String result;
	
	@Before
	public void init() {
		countries = new ArrayList<Country>();
    	countries.add(new Country("United States of America","Washington D.C.","US"));
    	countries.add(new Country("Estados Unidos Mexicanos","Ciudad de México, \"CDMX\"","MX"));
    	countries.add(new Country("Australia","Sidney","AU"));
    	countries.add(new Country("United Kingdom","London","OK"));
    	countries.add(new Country("Belize","Belmopán","BZ"));
    	
    	writer = new StringWriter();
	}
	
    @Test
    public void test01() throws Exception {
        csvWriter = CsvWriterFactory.newInstance(Country.class);
        csvWriter.setWriter(writer)
                .setLines(countries)
                .autoflush(true)
                .write();

        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.COMMA));
        writer.close();
    }

    @Test
    public void test02() throws Exception {
        csvWriter = CsvWriterFactory.newInstance(Country.class);
        csvWriter.setWriter(writer)
                .setLines(countries)
                .skipHeader(true)
                .autoflush(true)
                .write();
        
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"+CsvCons.COMMA));
        writer.close();
    }
    
    @Test
    public void test03() throws Exception {
        csvWriter = CsvWriterFactory.newInstance(Country.class);
        csvWriter.setWriter(writer)
                .setLines(countries)
                .autoflush(true)
                .addLine(new Country("India", "New Delhi", "IN"))
                .write();
        
        ArrayList<Country> allLines = (ArrayList<Country>) csvWriter.getLines();
        Assert.assertEquals("New Delhi", allLines.get(allLines.size() - 1).getCapital());
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.trim().endsWith("IN"));
        writer.close();
    }

    @Test
    public void test04() throws Exception {
    	csvWriter = CsvWriterFactory.newInstance(Country.class);
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(countries)
                .autoflush(true)
                .write();
        
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.PIPE));
        writer.close();        
    }

    @Test
    public void test05() throws Exception {
    	csvWriter = CsvWriterFactory.newInstance(Country.class);
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(countries)
                .skipHeader(true)
                .autoflush(true)
                .write();
        
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"+CsvCons.PIPE));
        writer.close();
    }
    
	@Test
    public void test06() throws CsvWriterException, IOException {
        EntityCsvWriter.write(writer, countries, Country.class);
        writer.close();
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"));
    }
	
	@Test
    public void test07() throws CsvWriterException, IOException {
        EntityCsvWriter.write(writer, countries, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Country.class);
        writer.close();
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.PIPE));
    }
	
	@Test
    public void test08() throws CsvWriterException, IOException {
        EntityCsvWriter.write(writer, countries, true, Country.class);
        writer.close();
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"));
    }
	
	@Test
    public void test09() throws CsvWriterException, IOException {
        EntityCsvWriter.write(writer, countries, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, true, Country.class);
        writer.close();
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"+CsvCons.PIPE));
    }
	 
}
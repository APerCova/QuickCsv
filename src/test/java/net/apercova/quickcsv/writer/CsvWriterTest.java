package net.apercova.quickcsv.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvWriterFactory;

public class CsvWriterTest {

	private Collection<Collection<String>> csvValues;
	private CsvWriter<Collection<String>> csvWriter;
	private Writer writer;
	private String result;
	
	@Before
	public void init() {
		csvValues = new ArrayList<Collection<String>>();
        csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
        csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
        csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "Ciudad de México, \"CDMX\"", "MX"}));
        csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
        csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
        csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));
        
        writer = new StringWriter();
        csvWriter = CsvWriterFactory.newInstance();
	}
	
    @Test
    public void test01() throws Exception {
        csvWriter.setWriter(writer)
                .setLines(csvValues)
                .autoflush(true)
                .write();

        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.COMMA));
        writer.close();
    }

    @Test
    public void test02() throws Exception {
        csvWriter.setWriter(writer)
                .setLines(csvValues)
                .skipHeader(true)
                .autoflush(true)
                .write();
        
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"+CsvCons.COMMA));
        writer.close();
    }
    
    @Test
    public void test03() throws Exception {
        csvWriter.setWriter(writer)
                .setLines(csvValues)
                .autoflush(true)
                .addLine(Arrays.asList("India", "New Delhi", "IN"))
                .write();
        
        List<Collection<String>> allLines = (List<Collection<String>>) csvWriter.getLines();
        List<String> rec = (List<String>) allLines.get(allLines.size() - 1);
        Assert.assertEquals("New Delhi", rec.get(1));
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.trim().endsWith("IN"));
        writer.close();
    }

    @Test
    public void test04() throws Exception {
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(csvValues)
                .autoflush(true)
                .write();
        
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("NAME"+CsvCons.PIPE));
        writer.close();
    }

    @Test
    public void test05() throws Exception {
        csvWriter.setWriter(writer)
                .setDelimiter(CsvCons.PIPE)
                .setLines(csvValues)
                .skipHeader(true)
                .autoflush(true)
                .write();
        
        result = ((StringWriter)writer).toString();
        Assert.assertTrue(result.startsWith("United States of America"+CsvCons.PIPE));
        writer.close();        
    }
    
	@Test
    public void test06() throws CsvWriterException, IOException {
        SimpleCsvWriter.write(writer, csvValues);
        writer.close();

        result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: Rfc4180Test",result.startsWith("NAME"));
    }
	
	@Test
    public void test07() throws CsvWriterException, IOException {
        SimpleCsvWriter.write(writer, csvValues, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
        writer.close();

        result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: CustomTest",result.startsWith("NAME"+CsvCons.PIPE));
    }
	
	@Test
    public void test08() throws CsvWriterException, IOException {
        SimpleCsvWriter.write(writer, csvValues, true);
        writer.close();

        result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: Rfc4180EscapeHeaderTest",result.startsWith("United States of America"));
    }
	
	@Test
    public void test09() throws CsvWriterException, IOException {
        SimpleCsvWriter.write(writer, csvValues, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, true);
        writer.close();

        result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: CustomEscapeHeaderTest",result.startsWith("United States of America"+CsvCons.PIPE));
    }

    @Test
    public void test10()  {
        String value = "México";
        String formated = SimpleCsvWriter.formatValue(value, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        Assert.assertEquals("Fail: formatValue", value, formated);

        value = "Ciudad de México, CDMX";
        formated = SimpleCsvWriter.formatValue(value, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        Assert.assertTrue("Delimiter in value format error",
                formated.equals("\"Ciudad de México, CDMX\""));
        Assert.assertEquals("Fail: formatValue", "\"Ciudad de México, CDMX\"", formated);

        value = "\"MX\"";
        formated = SimpleCsvWriter.formatValue(value, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        Assert.assertEquals("Fail: formatValue", 
        		CsvCons.DOUBLE_QUOTE+""+CsvCons.DOUBLE_QUOTE+value+CsvCons.DOUBLE_QUOTE+""+CsvCons.DOUBLE_QUOTE, 
        		formated);
        
        value = "'Ciudad de México, 'CDMX''";
        formated = SimpleCsvWriter.formatValue(value, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
        Assert.assertEquals("Fail: formatValue", "'''Ciudad de México, ''CDMX'''''", formated);
    }

}
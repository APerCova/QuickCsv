package net.apercova.quickcsv.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;

public class SimpleCsvWriterStaticTest {
	
	private static final Logger logger = Logger.getLogger(SimpleCsvWriter.class.getName());
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
    public void Rfc4180Test() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        SimpleCsvWriter.write(writer, csvValues);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: Rfc4180Test",result.startsWith("NAME"));
        logger.info("Rfc4180Test completed successfully");
    }
	
	@Test
    public void CustomTest() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        SimpleCsvWriter.write(writer, csvValues, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: CustomTest",result.startsWith("NAME"+CsvCons.PIPE));
        logger.info("CustomTest completed successfully");
    }
	
	@Test
    public void Rfc4180EscapeHeaderTest() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        SimpleCsvWriter.write(writer, csvValues, true);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: Rfc4180EscapeHeaderTest",result.startsWith("United States of America"));
        logger.info("Rfc4180EscapeHeaderTest completed successfully");
    }
	
	@Test
    public void CustomEscapeHeaderTest() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        SimpleCsvWriter.write(writer, csvValues, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, true);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: CustomEscapeHeaderTest",result.startsWith("United States of America"+CsvCons.PIPE));
        logger.info("CustomEscapeHeaderTest completed successfully");
    }

    @Test
    public void formatValue()  {

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

        logger.info("formatValue completed successfully");

    }

}
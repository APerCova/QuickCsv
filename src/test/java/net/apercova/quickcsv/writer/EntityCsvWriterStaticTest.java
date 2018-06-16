package net.apercova.quickcsv.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.entity.Country;

public class EntityCsvWriterStaticTest {
	
	private static final Logger logger = Logger.getLogger(SimpleCsvWriter.class.getName());
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
    public void Rfc4180Test() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        EntityCsvWriter.write(writer, countries, Country.class);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: Rfc4180Test",result.startsWith("NAME"));
        logger.info("Rfc4180Test completed successfully");
    }
	
	@Test
    public void CustomTest() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        EntityCsvWriter.write(writer, countries, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Country.class);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: CustomTest",result.startsWith("NAME"+CsvCons.PIPE));
        logger.info("CustomTest completed successfully");
    }
	
	@Test
    public void Rfc4180EscapeHeaderTest() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        EntityCsvWriter.write(writer, countries, true, Country.class);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: Rfc4180EscapeHeaderTest",result.startsWith("United States of America"));
        logger.info("Rfc4180EscapeHeaderTest completed successfully");
    }
	
	@Test
    public void CustomEscapeHeaderTest() throws CsvWriterException, IOException {

        Writer writer = new StringWriter();
        EntityCsvWriter.write(writer, countries, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, true, Country.class);
        writer.close();

        String result = ((StringWriter)writer).toString();
        Assert.assertTrue("Fail: CustomEscapeHeaderTest",result.startsWith("United States of America"+CsvCons.PIPE));
        logger.info("CustomEscapeHeaderTest completed successfully");
    }

}

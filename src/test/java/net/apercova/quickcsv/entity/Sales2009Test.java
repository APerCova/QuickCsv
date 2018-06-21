package net.apercova.quickcsv.entity;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvReaderFactory;
import net.apercova.quickcsv.CsvWriterFactory;
import net.apercova.quickcsv.reader.CsvReader;
import net.apercova.quickcsv.reader.CsvReaderException;
import net.apercova.quickcsv.writer.CsvWriter;
import net.apercova.quickcsv.writer.CsvWriterException;

public class Sales2009Test {

	private Reader reader;
	private StringWriter writer;
	
	@Before
	public void init() {
		reader = new InputStreamReader(
				ClassLoader.getSystemResourceAsStream("Sales2009.csv"), 
				Charset.forName("utf8"));
		
		writer = new StringWriter();
	}
	
	@Test
	public void read() throws CsvReaderException, IOException, CsvWriterException {
		List<Sales2009> sales = new ArrayList<Sales2009>();
		
		Assert.assertNotNull(reader);
		CsvReader<Sales2009> csvReader = CsvReaderFactory.newInstance(reader, Sales2009.class);
		csvReader.skipHeader(true);
		
		for(Sales2009 s: csvReader.read()) {//try without read()
			assertTrue(s.getTransactionDate() instanceof Date);
			
			assertTrue(s.getProduct() instanceof String);
			assertTrue(s.getPaymentType() instanceof String);
			assertTrue(s.getName() instanceof String);
			assertTrue(s.getCity() instanceof String);
			assertTrue(s.getState() instanceof String);
			assertTrue(s.getCountry() instanceof String);
			assertTrue(s.getLatitude() instanceof BigDecimal);
			
			assertTrue(s.getPrice() instanceof BigDecimal);
			assertTrue(s.getLongitude() instanceof BigDecimal);
			
			sales.add(s);
		}
		csvReader.close();
		
		Assert.assertNotNull(writer);
		CsvWriter<Sales2009> csvWriter = CsvWriterFactory.newInstance(writer, sales, Sales2009.class);
		csvWriter.escapeHeader(false).write();
		
		System.out.println(writer.toString());
	}
	
}

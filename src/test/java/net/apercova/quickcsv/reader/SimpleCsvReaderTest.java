package net.apercova.quickcsv.reader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.DefaultCsvReaderTest;
//import net.apercova.quickcsv.SimpleCsvReader;
import net.apercova.quickcsv.reader.CsvReader;
import net.apercova.quickcsv.reader.CsvReaderException;
import net.apercova.quickcsv.reader.CsvReaderFactory;

public class SimpleCsvReaderTest {
	
	private static final Logger logger = Logger.getLogger(DefaultCsvReaderTest.class.getName());
	private InputStream monthsStream;
	private InputStream monthsCustomStream;
    
    @Before
    public void init() {
    	monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
    	monthsCustomStream = ClassLoader.getSystemResourceAsStream("MonthsCustom.csv");
    }
    
    @Test
    public void Rfc4180Test() throws CsvReaderException, IOException {
    	Reader reader = new InputStreamReader(monthsStream);
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
        List<List<String>> values = csvReader.read();
        csvReader.close();
        reader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void Rfc4180CustomTest() throws CsvReaderException, IOException {
    	Reader reader = new InputStreamReader(monthsCustomStream);
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.setDelimiter(CsvCons.PIPE);
    	csvReader.setQuote(CsvCons.SINGLE_QUOTE);
    	
        List<List<String>> values = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        logger.info("Rfc4180CustomTest completed successfully");
    }
    
    @Test
    public void Rfc4180EscapeHeader() throws CsvReaderException, IOException {
    	Reader reader = new InputStreamReader(monthsStream);
    	
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.escapeheader(true);
    	
        List<List<String>> values = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 5);
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        logger.info("Rfc4180EscapeHeader completed successfully");
    }
    
    @Test
    public void Rfc4180LimitLines() throws CsvReaderException, IOException {
    	int fromLine = 1;
    	int maxLines = 3;
    	Reader reader = new InputStreamReader(monthsStream);
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<List<String>> values = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == maxLines);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        Assert.assertEquals(values.get(values.size()-1).get(0), "january");
        logger.info("Rfc4180LimitLines completed successfully");
    }
    
    @Test
    public void Rfc4180EscapeHeaderLimitLines() throws CsvReaderException, IOException {
    	int fromLine = 1;
    	int maxLines = 4;
    	Reader reader = new InputStreamReader(monthsStream);
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.escapeheader(true);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<List<String>> values = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == (maxLines - 1));
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        Assert.assertEquals(values.get(values.size()-1).get(0), "janvier");
        logger.info("Rfc4180EscapeHeaderLimitLines completed successfully");
    }

}

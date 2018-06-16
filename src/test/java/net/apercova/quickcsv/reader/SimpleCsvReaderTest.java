package net.apercova.quickcsv.reader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvReaderFactory;

public class SimpleCsvReaderTest {
	
	private static final Logger logger = Logger.getLogger(SimpleCsvReaderTest.class.getName());
	private InputStream monthsStream;
	private InputStream countriesStream;
    
    @Before
    public void init() {
    	monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
    	countriesStream = ClassLoader.getSystemResourceAsStream("Countries.csv");
    }
    
    @Test
    public void Rfc4180Test() throws CsvReaderException, IOException {
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
        List<List<String>> values = csvReader.read();
        csvReader.close();
        reader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void CustomTest() throws CsvReaderException, IOException {
    	Reader reader = new InputStreamReader(countriesStream, Charset.forName("utf-8"));
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.escapeheader(true);
    	csvReader.setDelimiter(CsvCons.PIPE);
    	csvReader.setQuote(CsvCons.SINGLE_QUOTE);
    	
        List<List<String>> countries = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(countries != null);
        Assert.assertEquals(countries.size(), 5);
        Assert.assertEquals(countries.get(1).get(1), "\"Ciudad de México, CDMX\"");
        logger.info("CustomTest completed successfully");
    }
    
    @Test
    public void EscapeHeaderTest() throws CsvReaderException, IOException {
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.escapeheader(true);
    	
        List<List<String>> values = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 5);
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        logger.info("EscapeHeaderTest completed successfully");
    }
    
    @Test
    public void LimitLinesTest() throws CsvReaderException, IOException {
    	int fromLine = 1;
    	int maxLines = 3;
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<List<String>> values = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), maxLines);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        Assert.assertEquals(values.get(values.size()-1).get(0), "january");
        logger.info("LimitLinesTest completed successfully");
    }
    
    @Test
    public void EscapeHeaderLimitLinesTest() throws CsvReaderException, IOException {
    	int fromLine = 1;
    	int maxLines = 4;
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance(reader);
    	csvReader.escapeheader(true);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<List<String>> values = csvReader.read();
        csvReader.close();
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), (maxLines - 1));
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        Assert.assertEquals(values.get(values.size()-1).get(0), "janvier");
        logger.info("EscapeHeaderLimitLinesTest completed successfully");
    }

}

package net.apercova.quickcsv.reader;
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
import net.apercova.quickcsv.entity.Month;
import net.apercova.quickcsv.reader.CsvReader;
import net.apercova.quickcsv.reader.CsvReaderException;
import net.apercova.quickcsv.reader.CsvReaderFactory;

public class EntityCsvReaderTest {
	
	private static final Logger logger = Logger.getLogger(DefaultCsvReaderTest.class.getName());
	private InputStream monthsStream;
	private InputStream monthsCustomStream;
    
    @Before
    public void init() {
    	monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
    	monthsCustomStream = ClassLoader.getSystemResourceAsStream("MonthsCustom.csv");
    }
    
    @Test
    public void Rfc4180Test() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream);
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
        List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 5);
        Assert.assertEquals(values.get(0).getM1(), "'enero'");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void Rfc4180CustomTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsCustomStream);
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
    	csvReader.setDelimiter(CsvCons.PIPE);
    	csvReader.setQuote(CsvCons.SINGLE_QUOTE);
    	
    	List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 5);
        Assert.assertEquals(values.get(0).getM1(), "enero");
        logger.info("Rfc4180CustomTest completed successfully");
    }
    
    @Test
    public void Rfc4180EscapeHeader() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream);
    	
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
    	csvReader.escapeheader(false);
    	
    	List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        logger.info("Rfc4180EscapeHeader completed successfully");
    }
    
    @Test
    public void Rfc4180LimitLines() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 3;
    	Reader reader = new InputStreamReader(monthsStream);
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == (maxLines - 1));
        Assert.assertEquals(values.get(0).getM1(), "'enero'");
        Assert.assertEquals(values.get(values.size()-1).getM1(), "january");
        logger.info("Rfc4180LimitLines completed successfully");
    }
    
    @Test
    public void Rfc4180EscapeHeaderLimitLines() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 4;
    	Reader reader = new InputStreamReader(monthsStream);
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
    	csvReader.escapeheader(false);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == (maxLines));
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        Assert.assertEquals(values.get(values.size()-1).getM1(), "janvier");
        logger.info("Rfc4180EscapeHeaderLimitLines completed successfully");
    }
}

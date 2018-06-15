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
import net.apercova.quickcsv.reader.CsvReaderException;
import net.apercova.quickcsv.reader.SimpleCsvReader;

public class SimpleCsvReaderStaticTest {
	
	private static final Logger logger = Logger.getLogger(SimpleCsvReaderStaticTest.class.getName());
	private InputStream monthsStream;
	private InputStream monthsCustomStream;
	private String daysOfWeek;
    
    @Before
    public void init() {
    	monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
    	monthsCustomStream = ClassLoader.getSystemResourceAsStream("MonthsCustom.csv");
    	daysOfWeek = "sunday,\"\"\"monday\"\"\",\"tues,day\",wednesday,\"\"\"thu,rsday\"\"\",\"\"\"\"\"friday\"\"\"\"\",saturday";
    }
    
    @Test
    public void Rfc4180Test() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream);
        List<List<String>> values = SimpleCsvReader.read(reader);
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void Rfc4180CustomTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsCustomStream);
        List<List<String>> values = SimpleCsvReader.read(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        logger.info("Rfc4180CustomTest completed successfully");
    }
    
    @Test
    public void Rfc4180EscapeHeader() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream);
        List<List<String>> values = SimpleCsvReader.read(reader, true);
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 5);
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        logger.info("Rfc4180EscapeHeader completed successfully");
    }
    
    @Test
    public void Rfc4180LimitLines() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 3;
    	Reader reader = new InputStreamReader(monthsStream);
        List<List<String>> values = SimpleCsvReader.read(reader, fromLine, maxLines);
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == maxLines);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        Assert.assertEquals(values.get(values.size()-1).get(0), "january");
        logger.info("Rfc4180LimitLines completed successfully");
    }
    
    @Test
    public void Rfc4180EscapeHeaderLimitLines() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 4;
    	Reader reader = new InputStreamReader(monthsStream);
        List<List<String>> values = SimpleCsvReader.read(reader, true, fromLine, maxLines);
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == (maxLines - 1));
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        Assert.assertEquals(values.get(values.size()-1).get(0), "janvier");
        logger.info("Rfc4180EscapeHeaderLimitLines completed successfully");
    }
    
    @Test
    public void Rfc4180ReadLine() throws CsvReaderException {
        
    	List<String> values = SimpleCsvReader.readLine(daysOfWeek, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        
        Assert.assertTrue("Error["+values.get(0)+"]","sunday".equals(values.get(0)));
        Assert.assertTrue("Error["+values.get(1)+"]","\"monday\"".equals(values.get(1)));
        Assert.assertTrue("Error["+values.get(2)+"]","tues,day".equals(values.get(2)));
        Assert.assertTrue("Error["+values.get(3)+"]","wednesday".equals(values.get(3)));
        Assert.assertTrue("Error["+values.get(4)+"]","\"thu,rsday\"".equals(values.get(4)));
        Assert.assertTrue("Error["+values.get(5)+"]","\"\"friday\"\"".equals(values.get(5)));
        Assert.assertTrue("Error["+values.get(6)+"]","saturday".equals(values.get(6)));
        logger.info("Rfc4180ReadLine completed successfully");
    }
}

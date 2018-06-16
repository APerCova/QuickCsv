package net.apercova.quickcsv.reader;
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
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
        List<List<String>> values = SimpleCsvReader.read(reader);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void CustomTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsCustomStream, Charset.forName("utf-8"));
        List<List<String>> values = SimpleCsvReader.read(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        logger.info("CustomTest completed successfully");
    }
    
    @Test
    public void EscapeHeaderTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
        List<List<String>> values = SimpleCsvReader.read(reader, true);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 5);
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        logger.info("EscapeHeaderTest completed successfully");
    }
    
    @Test
    public void LimitLinesTest() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 3;
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
        List<List<String>> values = SimpleCsvReader.read(reader, fromLine, maxLines);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), maxLines);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        Assert.assertEquals(values.get(values.size()-1).get(0), "january");
        logger.info("LimitLinesTest completed successfully");
    }
    
    @Test
    public void EscapeHeaderLimitLinesTest() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 4;
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
        List<List<String>> values = AbstractCsvReader.read(reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, fromLine, maxLines);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), (maxLines - 1));
        Assert.assertEquals(values.get(0).get(0), "'enero'");
        Assert.assertEquals(values.get(values.size()-1).get(0), "janvier");
        logger.info("EscapeHeaderLimitLinesTest completed successfully");
    }
    
    @Test
    public void ReadLineTest() throws CsvReaderException {
        
    	List<String> values = AbstractCsvReader.readLine(daysOfWeek, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        
        Assert.assertEquals("sunday", values.get(0));
        Assert.assertEquals("\"monday\"", values.get(1));
        Assert.assertEquals("tues,day", values.get(2));
        Assert.assertEquals("wednesday", values.get(3));
        Assert.assertEquals("\"thu,rsday\"", values.get(4));
        Assert.assertEquals("\"\"friday\"\"", values.get(5));
        Assert.assertEquals("saturday", values.get(6));
        
        logger.info("ReadLineTest completed successfully");
    }
}

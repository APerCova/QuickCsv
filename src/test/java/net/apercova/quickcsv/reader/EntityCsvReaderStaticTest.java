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
import net.apercova.quickcsv.entity.Month;
import net.apercova.quickcsv.reader.CsvReaderException;
import net.apercova.quickcsv.reader.EntityCsvReader;

public class EntityCsvReaderStaticTest {
	
	private static final Logger logger = Logger.getLogger(EntityCsvReaderStaticTest.class.getName());
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
        List<Month> values = EntityCsvReader.read(reader, Month.class);

        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 5);
        Assert.assertEquals(values.get(0).getM1(), "'enero'");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void Rfc4180CustomTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsCustomStream);
        List<Month> values = EntityCsvReader.read(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Month.class);
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 5);
        Assert.assertEquals(values.get(0).getM1(), "enero");
        logger.info("Rfc4180CustomTest completed successfully");
    }
    
    @Test
    public void Rfc4180AllowHeader() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream);
        List<Month> values = EntityCsvReader.read(reader, false, Month.class);
        
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
        List<Month> values = EntityCsvReader.read(reader, fromLine, maxLines, Month.class);
        
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
    	List<Month> values = EntityCsvReader.read(reader, false, fromLine, maxLines, Month.class);
        
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == (maxLines));
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        Assert.assertEquals(values.get(values.size()-1).getM1(), "janvier");
        logger.info("Rfc4180EscapeHeaderLimitLines completed successfully");
    }
}

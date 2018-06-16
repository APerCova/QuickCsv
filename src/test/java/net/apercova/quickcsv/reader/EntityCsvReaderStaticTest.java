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
import net.apercova.quickcsv.converter.DatatypeConversionException;
import net.apercova.quickcsv.entity.Month;
import net.apercova.quickcsv.entity.WeekDays;
import net.apercova.quickcsv.reader.CsvReaderException;
import net.apercova.quickcsv.reader.EntityCsvReader;

public class EntityCsvReaderStaticTest {
	
	private static final Logger logger = Logger.getLogger(EntityCsvReaderStaticTest.class.getName());
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
        List<Month> values = EntityCsvReader.read(reader, Month.class);

        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 5);
        Assert.assertEquals(values.get(0).getM1(), "'enero'");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void CustomTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsCustomStream, Charset.forName("utf-8"));
        List<Month> values = EntityCsvReader.read(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Month.class);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 5);
        Assert.assertEquals(values.get(0).getM1(), "enero");
        logger.info("CustomTest completed successfully");
    }
    
    @Test
    public void AllowHeaderTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
        List<Month> values = EntityCsvReader.read(reader, false, Month.class);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        logger.info("AllowHeaderTest completed successfully");
    }
    
    @Test
    public void LimitLinesTest() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 3;
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
        List<Month> values = EntityCsvReader.read(reader, fromLine, maxLines, Month.class);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), (maxLines - 1));
        Assert.assertEquals(values.get(0).getM1(), "'enero'");
        Assert.assertEquals(values.get(values.size()-1).getM1(), "january");
        logger.info("LimitLinesTest completed successfully");
    }
    
    @Test
    public void EscapeHeaderLimitLinesTest() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 4;
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	List<Month> values = EntityCsvReader.read(reader, false, fromLine, maxLines, Month.class);
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), (maxLines));
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        Assert.assertEquals(values.get(values.size()-1).getM1(), "janvier");
        logger.info("EscapeHeaderLimitLinesTest completed successfully");
    }
    
    @Test
    public void ReadLineTest() throws CsvReaderException, InstantiationException, IllegalAccessException, DatatypeConversionException {
        
    	WeekDays days = EntityCsvReader.readLine(daysOfWeek, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, WeekDays.class);
        
        Assert.assertEquals("sunday", days.getD_01());
        Assert.assertEquals("\"monday\"", days.getD_02());
        Assert.assertEquals("tues,day", days.getD_03());
        Assert.assertEquals("wednesday", days.getD_04());
        Assert.assertEquals("\"thu,rsday\"", days.getD_05());
        Assert.assertEquals("\"\"friday\"\"", days.getD_06());
        Assert.assertEquals("saturday", days.getD_07());
        
        logger.info("ReadLineTest completed successfully");
    }
}

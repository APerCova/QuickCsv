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
import net.apercova.quickcsv.CsvReaderFactory;
import net.apercova.quickcsv.entity.Country;
import net.apercova.quickcsv.entity.Month;

public class EntityCsvReaderTest {
	
	private static final Logger logger = Logger.getLogger(EntityCsvReaderTest.class.getName());
	private InputStream monthsStream;
	private InputStream countriesStream;
    
    @Before
    public void init() {
    	monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
    	countriesStream = ClassLoader.getSystemResourceAsStream("Countries.csv");
    }
    
    @Test
    public void Rfc4180Test() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
        List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 5);
        Assert.assertEquals(values.get(0).getM1(), "'enero'");
        logger.info("Rfc4180Test completed successfully");
    }
    
    @Test
    public void CustomTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(countriesStream, Charset.forName("utf-8"));
    	CsvReader<Country> csvReader = CsvReaderFactory.newInstance(reader, Country.class);
    	csvReader.escapeheader(true);
    	csvReader.setDelimiter(CsvCons.PIPE);
    	csvReader.setQuote(CsvCons.SINGLE_QUOTE);
    	
    	List<Country> countries = csvReader.read();
        
        Assert.assertTrue(countries != null);
        Assert.assertEquals(countries.size(), 5);
        Assert.assertEquals(countries.get(1).getCapital(), "\"Ciudad de México, CDMX\"");
        logger.info("CustomTest completed successfully");
    }
    
    @Test
    public void EscapeHeaderTest() throws CsvReaderException {
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
    	csvReader.escapeheader(false);
    	
    	List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        logger.info("EscapeHeaderTest completed successfully");
    }
    
    @Test
    public void LimitLinesTest() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 3;
    	Reader reader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<Month> values = csvReader.read();
        
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
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(reader, Month.class);
    	csvReader.escapeheader(false);
    	csvReader.fromLine(fromLine);
    	csvReader.maxLines(maxLines);
    	
        List<Month> values = csvReader.read();
        
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), (maxLines));
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        Assert.assertEquals(values.get(values.size()-1).getM1(), "janvier");
        logger.info("EscapeHeaderLimitLinesTest completed successfully");
    }
}

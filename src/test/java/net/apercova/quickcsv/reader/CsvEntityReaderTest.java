package net.apercova.quickcsv.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvReaderFactory;
import net.apercova.quickcsv.entity.Country;
import net.apercova.quickcsv.entity.Month;

public class CsvEntityReaderTest {

	private InputStream monthsStream;
	private InputStream countriesStream;
	private Reader monthsReader;
	private Reader countriesReader;
	private EntityCsvReader<Month> monthsCsvReader;
	private List<Month> monthLines;
	private List<Country> countryLines;
	private String daysOfWeek;
	
	@Before
	public void init() {
		monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
		countriesStream = ClassLoader.getSystemResourceAsStream("Countries.csv");
		monthsReader = new InputStreamReader(monthsStream);
		countriesReader = new InputStreamReader(countriesStream);
		monthsCsvReader = EntityCsvReader.newInstance(monthsReader, Month.class);
		daysOfWeek = "sunday,\"\"\"monday\"\"\",\"tues,day\",wednesday,\"\"\"thu,rsday\"\"\",\"\"\"\"\"friday\"\"\"\"\",saturday";
	}
	
	@Test
	public void test01() throws CsvReaderException {
		monthLines = monthsCsvReader.read();
		assertEquals(5, monthLines.size());
	}
	
	@Test
	public void test02() throws CsvReaderException {
		monthLines = monthsCsvReader.skipHeader(false).read();
		assertEquals(6, monthLines.size());
	}
	
	@Test
	public void test03() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(3).read();
		assertEquals(4, monthLines.size());
	}
	
	@Test
	public void test04() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(3).skipHeader(false).read();
		assertEquals(5, monthLines.size());
	}
	
	@Test
	public void test05() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(4).maxLines(4).read();
		assertEquals(3, monthLines.size());
	}
	
	@Test
	public void test06() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(4).maxLines(4).skipHeader(false).read();
		assertEquals(4, monthLines.size());
	}
	
	@Test
	public void test07() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(-1).maxLines(600).read();
		assertEquals(5, monthLines.size());
	}
	
	@Test
	public void test08() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(-1).maxLines(600).skipHeader(false).read();
		assertEquals(6, monthLines.size());
	}
	
	@Test
	public void test09() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(4).maxLines(1).read();
		assertEquals(1, monthLines.size());
	}
	
	@Test
	public void test10() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(4).maxLines(1).skipHeader(false).read();
		assertEquals(2, monthLines.size());
	}
	
	@Test
	public void test11() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(7).maxLines(1).read();
		assertEquals(0, monthLines.size());
	}
	
	@Test
	public void test12() throws CsvReaderException {
		monthLines = monthsCsvReader.fromLine(7).maxLines(1).skipHeader(false).read();
		assertEquals(1, monthLines.size());
	}
	
	@Test
	public void test13() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1);
		int c = 0;
		for(Month line: monthsCsvReader) {
			c++;
			if(c == 1) {
				assertEquals("'enero'", line.getM1());
			}
		}
		assertEquals(1, c);
	}
	
	@Test
	public void test14() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1).skipHeader(false);
		int c = 0;
		for(Month line: monthsCsvReader) {
			c++;
			if(c == 1) {
				assertEquals("m_01", line.getM1());
			}
			if(c == 2) {
				assertEquals("'enero'", line.getM1());
			}
		}
		assertEquals(2, c);
	}
	
	@Test
	public void test15() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1);
		int c = 0;
		for(Month line: monthsCsvReader.read()) {
			c++;
			if(c == 1) {
				assertEquals("'enero'", line.getM1());
			}
		}
		assertEquals(1, c);
	}
	
	@Test
	public void test16() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1).skipHeader(false);
		int c = 0;
		for(Month line: monthsCsvReader.read()) {
			c++;
			if(c == 1) {
				assertEquals("m_01", line.getM1());
			}
			if(c == 2) {
				assertEquals("'enero'", line.getM1());
			}
		}
		assertEquals(2, c);
	}
	@Test
	public void test17() throws CsvReaderException {
		monthsCsvReader = (EntityCsvReader<Month>) CsvReaderFactory.newInstance(Month.class);
		assertNull(monthsCsvReader.getReader());
		assertEquals(CsvCons.COMMA, monthsCsvReader.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, monthsCsvReader.getQuote());
		
		monthsCsvReader = (EntityCsvReader<Month>) CsvReaderFactory.newInstance(monthsReader, Month.class);
		assertNotNull(monthsCsvReader.getReader());
		assertEquals(CsvCons.COMMA, monthsCsvReader.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, monthsCsvReader.getQuote());
		
		monthsCsvReader = (EntityCsvReader<Month>) CsvReaderFactory.newInstance(monthsReader, CsvCons.PIPE, Month.class);
		assertNotNull(monthsCsvReader.getReader());
		assertEquals(CsvCons.PIPE, monthsCsvReader.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, monthsCsvReader.getQuote());
		
		monthsCsvReader = (EntityCsvReader<Month>) CsvReaderFactory.newInstance(monthsReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Month.class);
		assertNotNull(monthsCsvReader.getReader());
		assertEquals(CsvCons.PIPE, monthsCsvReader.getDelimiter());
		assertEquals(CsvCons.SINGLE_QUOTE, monthsCsvReader.getQuote());
	}
	
	@Test
    public void test18() throws CsvReaderException {
        
    	List<String> values = SimpleCsvReader.readLine(daysOfWeek, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        
        Assert.assertEquals("sunday", values.get(0));
        Assert.assertEquals("\"monday\"", values.get(1));
        Assert.assertEquals("tues,day", values.get(2));
        Assert.assertEquals("wednesday", values.get(3));
        Assert.assertEquals("\"thu,rsday\"", values.get(4));
        Assert.assertEquals("\"\"friday\"\"", values.get(5));
        Assert.assertEquals("saturday", values.get(6));
    }
	
	@Test
    public void test19() throws CsvReaderException {
    	monthLines = EntityCsvReader.read(monthsReader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, Month.class);
    	assertEquals(5, monthLines.size());
    }
	
	@Test
    public void test20() throws CsvReaderException {
    	monthLines = EntityCsvReader.read(monthsReader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, 3, 2, Month.class);
    	assertEquals(3, monthLines.size());
    	assertEquals("m_01", monthLines.get(0).getM1());
    }
	
	@Test
    public void test21() throws CsvReaderException {
    	monthLines = EntityCsvReader.read(monthsReader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, 5, 2 , Month.class);
    	assertEquals(2, monthLines.size());
    	assertEquals("gennaio", monthLines.get(0).getM1());
    }
	@Test
    public void test22() throws CsvReaderException {
    	countryLines = EntityCsvReader.read(countriesReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, false, Country.class);
    	assertEquals(6, countryLines.size());
    	
		assertEquals("Estados Unidos Mexicanos", countryLines.get(2).getName());
		assertEquals("\"Ciudad de México, CDMX\"", countryLines.get(2).getCapital());
		assertEquals("MX", countryLines.get(2).getIsoCode());
    	
    }
	
}

package net.apercova.quickcsv.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvReaderFactory;
import net.apercova.quickcsv.entity.ComplexType;
import net.apercova.quickcsv.entity.Country;
import net.apercova.quickcsv.entity.Month;
import net.apercova.quickcsv.entity.User;

public class CsvEntityReaderTest {

	private InputStream monthsStream;
	private InputStream countriesStream;
	private InputStream usersStream;
	private InputStream complexStream;
	private Reader monthsReader;
	private Reader countriesReader;
	private Reader usersReader;
	private Reader complexReader;
	private EntityCsvReader<Month> monthsCsvReader;
	private EntityCsvReader<User> usersCsvReader;
	private EntityCsvReader<ComplexType> complexCsvReader;
	private List<Month> monthLines;
	private List<Country> countryLines;
	private String daysOfWeek;
	
	@Before
	public void init() {
		monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
		countriesStream = ClassLoader.getSystemResourceAsStream("Countries.csv");
		usersStream = ClassLoader.getSystemResourceAsStream("UserSample.csv");
		complexStream = ClassLoader.getSystemResourceAsStream("ComplexType.csv");
		
		monthsReader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
		countriesReader = new InputStreamReader(countriesStream, Charset.forName("utf-8"));
		usersReader = new InputStreamReader(usersStream, Charset.forName("utf-8"));
		complexReader = new InputStreamReader(complexStream, Charset.forName("utf-8"));
		
		monthsCsvReader = EntityCsvReader.newInstance(monthsReader, Month.class);
		usersCsvReader = EntityCsvReader.newInstance(usersReader, User.class);
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
        
        assertEquals("sunday", values.get(0));
        assertEquals("\"monday\"", values.get(1));
        assertEquals("tues,day", values.get(2));
        assertEquals("wednesday", values.get(3));
        assertEquals("\"thu,rsday\"", values.get(4));
        assertEquals("\"\"friday\"\"", values.get(5));
        assertEquals("saturday", values.get(6));
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
    @Test
    public void test23() throws CsvReaderException, IOException {
        
        monthsCsvReader = (EntityCsvReader<Month>) CsvReaderFactory.newInstance(Month.class);
        
        monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
        monthsCsvReader.setReader(monthsReader);
    	monthLines = monthsCsvReader.read();
    	assertEquals(5, monthLines.size());
    	assertEquals("'enero'", monthLines.get(0).getM1());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
                .skipHeader(true);
    	monthLines = monthsCsvReader.read();
    	assertEquals(5, monthLines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
                .fromLine(4);
    	monthLines = monthsCsvReader.read();
    	assertEquals(3, monthLines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(false)
                .fromLine(1)
                .maxLines(4);
    	monthLines = monthsCsvReader.read();
    	assertEquals(4, monthLines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(true)
                .fromLine(1)
                .maxLines(4);
    	monthLines = monthsCsvReader.read();
    	assertEquals(3, monthLines.size());
    	monthsCsvReader.close();
    	
        
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(false)
                .fromLine(3)
                .maxLines(3);
    	monthLines = monthsCsvReader.read();
    	assertEquals(4, monthLines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(true)
                .fromLine(3)
                .maxLines(3);
    	monthLines = monthsCsvReader.read();
    	assertEquals(3, monthLines.size());
    	monthsCsvReader.close();
            
    }
    @Test
    public void test24() throws CsvReaderException {        
        monthLines = EntityCsvReader.read(monthsReader, Month.class);

        assertEquals(5, monthLines.size());
        assertEquals("'enero'", monthLines.get(0).getM1());
    }
    
    @Test
    public void test25() throws CsvReaderException {
        countryLines = EntityCsvReader.read(countriesReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Country.class);
        
        assertEquals(5, countryLines.size());
        assertEquals("\"Ciudad de México, CDMX\"", countryLines.get(1).getCapital());
    }
    
    @Test
    public void test26() throws CsvReaderException {
        countryLines = EntityCsvReader.read(countriesReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, false, Country.class);
        
        assertEquals(6, countryLines.size());
        assertEquals("\"Ciudad de México, CDMX\"", countryLines.get(2).getCapital());
    }
    
    @Test
    public void test27() throws CsvReaderException {
        monthLines = EntityCsvReader.read(monthsReader, false, Month.class);
        
        assertEquals(6, monthLines.size());
        assertEquals("m_01", monthLines.get(0).getM1());
    }
    
    @Test
    public void test28() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 3;
        monthLines = EntityCsvReader.read(monthsReader, fromLine, maxLines, Month.class);
        
        assertEquals((maxLines - 1), monthLines.size());
        assertEquals("'enero'", monthLines.get(0).getM1());
        assertEquals("january", monthLines.get(monthLines.size()-1).getM1());
    }
    
    @Test
    public void test29() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 4;
    	monthLines = EntityCsvReader.read(monthsReader, false, fromLine, maxLines, Month.class);
        
        assertEquals(maxLines, monthLines.size());
        assertEquals("m_01", monthLines.get(0).getM1());
        assertEquals("janvier", monthLines.get(monthLines.size()-1).getM1());
    }
    
    @Test
    public void test30() throws CsvReaderException {
    	int c = 0;
    	for(User u: usersCsvReader) {
    		c++;
    		if(c == 2) {
    			assertEquals("ben@contoso.com", u.getUserName());
    			assertEquals("123-555-1212", u.getOfficePhone());
    		}
    		if(c == 5) {
    			assertEquals("melissa@contoso.com", u.getUserName());
    			assertEquals("123-555-1215", u.getOfficePhone());
    		}
    	}
    }
    @Test
    public void ComplexTypeTest() throws Exception {
    	
    	complexCsvReader = (EntityCsvReader<ComplexType>) CsvReaderFactory.newInstance(complexReader, ComplexType.class);
        List<ComplexType> values = complexCsvReader.read();
        
        assertNotNull(values);
        assertEquals(3, values.size());
        
        Calendar calendar = Calendar.getInstance();        
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 51);
        calendar.set(Calendar.SECOND, 13);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(""+values.get(1).getDate(),calendar.getTimeInMillis(), values.get(0).getDate().getTime());
        assertEquals(Charset.forName("iso-8859-1").name().toLowerCase(), values.get(0).getCharset().name().toLowerCase());
        assertEquals(MessageDigest.getInstance("sha-512").getAlgorithm().toLowerCase(), values.get(0).getMessageDigest().getAlgorithm().toLowerCase());
        
        
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 36);
        calendar.set(Calendar.SECOND, 45);
        calendar.set(Calendar.MILLISECOND, 150); 
        
        assertEquals(calendar.getTimeInMillis(), values.get(1).getDate().getTime());
        assertEquals(Charset.forName("us-ascii").name().toLowerCase(), values.get(1).getCharset().name().toLowerCase());
        assertEquals(MessageDigest.getInstance("sha").getAlgorithm().toLowerCase(), values.get(1).getMessageDigest().getAlgorithm().toLowerCase());
        
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 9);
        calendar.set(Calendar.SECOND, 23);
        calendar.set(Calendar.MILLISECOND, 945);
        
        assertEquals(calendar.getTimeInMillis(), values.get(2).getDate().getTime());
        assertEquals(Charset.forName("utf-8").name().toLowerCase(), values.get(2).getCharset().name().toLowerCase());
        assertEquals(MessageDigest.getInstance("md5").getAlgorithm().toLowerCase(), values.get(2).getMessageDigest().getAlgorithm().toLowerCase());

}
}

package net.apercova.quickcsv.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvReaderFactory;

public class CsvReaderTest {

	private InputStream monthsStream;
	private InputStream countriesStream;
	private Reader monthsReader;
	private Reader countriesReader;
	private SimpleCsvReader monthsCsvReader;
	private List<List<String>> lines;
	private String daysOfWeek;
	
	@Before
	public void init() {
		monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
		countriesStream = ClassLoader.getSystemResourceAsStream("Countries.csv");
		monthsReader = new InputStreamReader(monthsStream, Charset.forName("utf-8"));
		countriesReader = new InputStreamReader(countriesStream, Charset.forName("utf-8"));
		monthsCsvReader = SimpleCsvReader.newInstance(monthsReader);
		daysOfWeek = "sunday,\"\"\"monday\"\"\",\"tues,day\",wednesday,\"\"\"thu,rsday\"\"\",\"\"\"\"\"friday\"\"\"\"\",saturday";
	}
	
	@Test
	public void test01() throws CsvReaderException {
		lines = monthsCsvReader.read();
		assertEquals(6, lines.size());
	}
	
	@Test
	public void test02() throws CsvReaderException {
		lines = monthsCsvReader.skipHeader(true).read();
		assertEquals(5, lines.size());
	}
	
	@Test
	public void test03() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(3).read();
		assertEquals(5, lines.size());
	}
	
	@Test
	public void test04() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(3).skipHeader(true).read();
		assertEquals(4, lines.size());
	}
	
	@Test
	public void test05() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(4).maxLines(4).read();
		assertEquals(4, lines.size());
	}
	
	@Test
	public void test06() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(4).maxLines(4).skipHeader(true).read();
		assertEquals(3, lines.size());
	}
	
	@Test
	public void test07() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(-1).maxLines(600).read();
		assertEquals(6, lines.size());
	}
	
	@Test
	public void test08() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(-1).maxLines(600).skipHeader(true).read();
		assertEquals(5, lines.size());
	}
	
	@Test
	public void test09() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(4).maxLines(1).read();
		assertEquals(2, lines.size());
	}
	
	@Test
	public void test10() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(4).maxLines(1).skipHeader(true).read();
		assertEquals(1, lines.size());
	}
	
	@Test
	public void test11() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(7).maxLines(1).read();
		assertEquals(1, lines.size());
	}
	
	@Test
	public void test12() throws CsvReaderException {
		lines = monthsCsvReader.fromLine(7).maxLines(1).skipHeader(true).read();
		assertEquals(0, lines.size());
	}
	
	@Test
	public void test13() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1);
		int c = 0;
		for(List<String> line: monthsCsvReader) {
			c++;
			if(c == 1) {
				assertEquals("m_01", line.get(0));
			}
			if(c == 2) {
				assertEquals("'enero'", line.get(0));
			}
		}
		assertEquals(2, c);
	}
	
	@Test
	public void test14() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1).skipHeader(true);
		int c = 0;
		for(List<String> line: monthsCsvReader) {
			c++;
			if(c == 2) {
				assertEquals("'enero'", line.get(0));
			}
		}
		assertEquals(1, c);
	}
	
	@Test
	public void test15() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1);
		int c = 0;
		for(List<String> line: monthsCsvReader.read()) {
			c++;
			if(c == 1) {
				assertEquals("m_01", line.get(0));
			}
			if(c == 2) {
				assertEquals("'enero'", line.get(0));
			}
		}
		assertEquals(2, c);
	}
	
	@Test
	public void test16() throws CsvReaderException {
		monthsCsvReader.fromLine(2).maxLines(1).skipHeader(true);
		int c = 0;
		for(List<String> line: monthsCsvReader.read()) {
			c++;
			if(c == 2) {
				assertEquals("'enero'", line.get(0));
			}
		}
		assertEquals(1, c);
	}
	
	@Test
	public void test17() throws CsvReaderException {
		monthsCsvReader = (SimpleCsvReader) CsvReaderFactory.newInstance();
		assertNull(monthsCsvReader.getReader());
		assertEquals(CsvCons.COMMA, monthsCsvReader.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, monthsCsvReader.getQuote());
		
		monthsCsvReader = (SimpleCsvReader) CsvReaderFactory.newInstance(monthsReader);
		assertNotNull(monthsCsvReader.getReader());
		assertEquals(CsvCons.COMMA, monthsCsvReader.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, monthsCsvReader.getQuote());
		
		monthsCsvReader = (SimpleCsvReader) CsvReaderFactory.newInstance(monthsReader, CsvCons.PIPE);
		assertNotNull(monthsCsvReader.getReader());
		assertEquals(CsvCons.PIPE, monthsCsvReader.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, monthsCsvReader.getQuote());
		
		monthsCsvReader = (SimpleCsvReader) CsvReaderFactory.newInstance(monthsReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
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
    	lines = SimpleCsvReader.read(monthsReader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
    	assertEquals(6, lines.size());
    }
	
	@Test
    public void test20() throws CsvReaderException {
    	lines = SimpleCsvReader.read(monthsReader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, 3, 2 );
    	assertEquals(3, lines.size());
    	assertEquals("m_01", lines.get(0).get(0));
    }
	
	@Test
    public void test21() throws CsvReaderException {
    	lines = SimpleCsvReader.read(monthsReader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, 5, 2 );
    	assertEquals(2, lines.size());
    	assertEquals("gennaio", lines.get(0).get(0));
    }
	@Test
    public void test22() throws CsvReaderException {
    	lines = SimpleCsvReader.read(countriesReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, false);
    	assertEquals(6, lines.size());
    	for(List<String> line: lines) {
    		assertEquals(3, line.size());
    	}
    }
    @Test
    public void test23() throws CsvReaderException, IOException {
        monthsCsvReader = (SimpleCsvReader) CsvReaderFactory.newInstance();
        
        monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
        monthsCsvReader.setReader(monthsReader);
    	lines = monthsCsvReader.read();
    	assertEquals(6, lines.size());
    	assertEquals("m_01", lines.get(0).get(0));
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
                .skipHeader(true);
    	lines = monthsCsvReader.read();
    	assertEquals(5, lines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
                .skipHeader(true)
                .fromLine(4);
    	lines = monthsCsvReader.read();
    	assertEquals(3, lines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(true)
                .fromLine(1)
                .maxLines(4);
    	lines = monthsCsvReader.read();
    	assertEquals(3, lines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(false)
                .fromLine(1)
                .maxLines(4);
    	lines = monthsCsvReader.read();
    	assertEquals(4, lines.size());
    	monthsCsvReader.close();
    	
        
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(true)
                .fromLine(3)
                .maxLines(3);
    	lines = monthsCsvReader.read();
    	assertEquals(3, lines.size());
    	monthsCsvReader.close();
    	
    	
    	monthsReader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	monthsCsvReader.setReader(monthsReader)
    			.skipHeader(false)
                .fromLine(3)
                .maxLines(3);
    	lines = monthsCsvReader.read();
    	assertEquals(4, lines.size());
    	monthsCsvReader.close();
    	
    }
    @Test
    public void test24() throws CsvReaderException {        
        lines = SimpleCsvReader.read(monthsReader);

        assertEquals(6, lines.size());
        assertEquals("m_01", lines.get(0).get(0));
    }
    
    @Test
    public void test25() throws CsvReaderException {
    	lines = SimpleCsvReader.read(countriesReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
        
        assertEquals(6, lines.size());
        assertEquals("\"Ciudad de México, CDMX\"", lines.get(2).get(1));
    }
    
    @Test
    public void test26() throws CsvReaderException {
    	lines = SimpleCsvReader.read(countriesReader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, true);
        
        assertEquals(5, lines.size());
        assertEquals("\"Ciudad de México, CDMX\"", lines.get(1).get(1));
    }
    
    @Test
    public void test27() throws CsvReaderException {
    	lines = SimpleCsvReader.read(monthsReader, false);
        
        assertEquals(6, lines.size());
        assertEquals("m_01", lines.get(0).get(0));
    }
    
    @Test
    public void test28() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 3;
    	lines = SimpleCsvReader.read(monthsReader, fromLine, maxLines);
        
    	assertEquals(maxLines, lines.size());
        assertEquals("m_01", lines.get(0).get(0));
        assertEquals("january", lines.get(lines.size()-1).get(0));
    }
    
    @Test
    public void test29() throws CsvReaderException {
    	int fromLine = 1;
    	int maxLines = 4;
    	lines = SimpleCsvReader.read(monthsReader, true, fromLine, maxLines);
        
    	assertEquals((maxLines - 1), lines.size());
        assertEquals("'enero'", lines.get(0).get(0));
        assertEquals("janvier", lines.get(lines.size()-1).get(0));
    }
	
}

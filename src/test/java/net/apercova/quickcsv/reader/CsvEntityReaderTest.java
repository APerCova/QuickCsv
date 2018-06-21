package net.apercova.quickcsv.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvReaderFactory;

public class CsvEntityReaderTest {

	private InputStream monthsStream;
	private Reader reader;
	private SimpleCsvReader csvReader;
	private CsvReader<List<String>> csvReaderBase;
	private List<List<String>> lines;
	
	@Before
	public void init() {
		monthsStream = ClassLoader.getSystemResourceAsStream("Months.csv");
		reader = new InputStreamReader(monthsStream);
		csvReader = SimpleCsvReader.newInstance(reader);
	}
	
	@Test
	public void test01() throws CsvReaderException {
		lines = csvReader.read();
		assertEquals(6, lines.size());
	}
	
	@Test
	public void test02() throws CsvReaderException {
		lines = csvReader.skipHeader(true).read();
		assertEquals(5, lines.size());
	}
	
	@Test
	public void test03() throws CsvReaderException {
		lines = csvReader.fromLine(3).read();
		assertEquals(5, lines.size());
	}
	
	@Test
	public void test04() throws CsvReaderException {
		lines = csvReader.fromLine(3).skipHeader(true).read();
		assertEquals(4, lines.size());
	}
	
	@Test
	public void test05() throws CsvReaderException {
		lines = csvReader.fromLine(4).maxLines(4).read();
		assertEquals(4, lines.size());
	}
	
	@Test
	public void test06() throws CsvReaderException {
		lines = csvReader.fromLine(4).maxLines(4).skipHeader(true).read();
		assertEquals(3, lines.size());
	}
	
	@Test
	public void test07() throws CsvReaderException {
		lines = csvReader.fromLine(-1).maxLines(600).read();
		assertEquals(6, lines.size());
	}
	
	@Test
	public void test08() throws CsvReaderException {
		lines = csvReader.fromLine(-1).maxLines(600).skipHeader(true).read();
		assertEquals(5, lines.size());
	}
	
	@Test
	public void test09() throws CsvReaderException {
		lines = csvReader.fromLine(4).maxLines(1).read();
		assertEquals(2, lines.size());
	}
	
	@Test
	public void test10() throws CsvReaderException {
		lines = csvReader.fromLine(4).maxLines(1).skipHeader(true).read();
		assertEquals(1, lines.size());
	}
	
	@Test
	public void test11() throws CsvReaderException {
		lines = csvReader.fromLine(7).maxLines(1).read();
		assertEquals(1, lines.size());
	}
	
	@Test
	public void test12() throws CsvReaderException {
		lines = csvReader.fromLine(7).maxLines(1).skipHeader(true).read();
		assertEquals(0, lines.size());
	}
	
	@Test
	public void test13() throws CsvReaderException {
		csvReader.fromLine(2).maxLines(1);
		int c = 0;
		for(List<String> line: csvReader) {
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
		csvReader.fromLine(2).maxLines(1).skipHeader(true);
		int c = 0;
		for(List<String> line: csvReader) {
			c++;
			if(c == 2) {
				assertEquals("'enero'", line.get(0));
			}
		}
		assertEquals(1, c);
	}
	
	@Test
	public void test15() throws CsvReaderException {
		csvReader.fromLine(2).maxLines(1);
		int c = 0;
		for(List<String> line: csvReader.read()) {
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
		csvReader.fromLine(2).maxLines(1).skipHeader(true);
		int c = 0;
		for(List<String> line: csvReader.read()) {
			c++;
			if(c == 2) {
				assertEquals("'enero'", line.get(0));
			}
		}
		assertEquals(1, c);
	}
	
	@Test
	public void test17() throws CsvReaderException {
		csvReaderBase = CsvReaderFactory.newInstance();
		assertNull(csvReaderBase.getReader());
		assertEquals(CsvCons.COMMA, csvReaderBase.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, csvReaderBase.getQuote());
		
		csvReaderBase = CsvReaderFactory.newInstance(reader);
		assertNotNull(csvReaderBase.getReader());
		assertEquals(CsvCons.COMMA, csvReaderBase.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, csvReaderBase.getQuote());
		
		csvReaderBase = CsvReaderFactory.newInstance(reader, CsvCons.PIPE);
		assertNotNull(csvReaderBase.getReader());
		assertEquals(CsvCons.PIPE, csvReaderBase.getDelimiter());
		assertEquals(CsvCons.DOUBLE_QUOTE, csvReaderBase.getQuote());
		
		csvReaderBase = CsvReaderFactory.newInstance(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
		assertNotNull(csvReaderBase.getReader());
		assertEquals(CsvCons.PIPE, csvReaderBase.getDelimiter());
		assertEquals(CsvCons.SINGLE_QUOTE, csvReaderBase.getQuote());
	}
	
	
}

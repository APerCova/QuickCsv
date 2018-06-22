package net.apercova.quickcsv.reader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvReaderFactory;
import net.apercova.quickcsv.CsvWriterFactory;
import net.apercova.quickcsv.entity.Country;
import net.apercova.quickcsv.writer.CsvWriter;
import net.apercova.quickcsv.writer.CsvWriterException;

public class Borrame {

	@Test
	public void test() throws UnsupportedEncodingException {
		Reader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Countries.csv"), "utf-8");
		CsvReader<Country> csvReader = CsvReaderFactory.newInstance(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Country.class);
		for(Country ct: csvReader) {
			System.out.println(ct);
		}
	}
	
	@Test
	public void test2() throws CsvReaderException, CsvWriterException, IOException {
		Reader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Countries.csv"), "utf-8");
		CsvReader<Country> csvReader = CsvReaderFactory.newInstance(reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, Country.class);
		List<Country> countries = csvReader.read();
		for(Country ct: csvReader) {
			System.out.println(ct);
		}
		csvReader.close();
		
		System.out.println();
		CsvWriter<Country> csvWriter = CsvWriterFactory.newInstance(Country.class);
		csvWriter.setWriter(new OutputStreamWriter(System.out, "utf-8"))
		.setLines(countries)
		.write();		
		csvWriter.close();
	}
}

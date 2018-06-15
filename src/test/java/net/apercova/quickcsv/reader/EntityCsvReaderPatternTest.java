package net.apercova.quickcsv.reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.DefaultCsvReaderTest;
import net.apercova.quickcsv.entity.Month;
import net.apercova.quickcsv.reader.CsvReader;
import net.apercova.quickcsv.reader.CsvReaderException;
import net.apercova.quickcsv.reader.CsvReaderFactory;

public class EntityCsvReaderPatternTest {
	
	private static final Logger logger = Logger.getLogger(DefaultCsvReaderTest.class.getName());
    
    @Before
    public void init() {
    }
    
    @Test
    public void Rfc4180Iterator() throws CsvReaderException, IOException {
    	CsvReader<Month> csvReader = CsvReaderFactory.newInstance(Month.class);
    	
    	//Getting an Iterator
    	Reader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader);
    	List<Month> values = new ArrayList<Month>();
    	Iterator<Month> it = csvReader.iterator();
    	
    	int currIt = 0;
    	while(it.hasNext()){
    		currIt++;
    		Month row = it.next();
            int rnum = csvReader.getLineNumber();
            Assert.assertTrue(currIt == rnum);
            values.add(row);
        }
    	
    	csvReader.close();
    	Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).getM1(), "m_01");
    	
        //CsvReader instance is actually an Iterator
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader);
    	values = new ArrayList<Month>();
    	
    	currIt = 0;
    	while(csvReader.hasNext()){
    		currIt++;
    		Month row = csvReader.next();
            int rnum = csvReader.getLineNumber();
            Assert.assertTrue(currIt == rnum);
            values.add(row);
        }
    	
        csvReader.close();
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        
        //CsvReader instance can be used inside enhanced for loop
        reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader);
    	values = new ArrayList<Month>();
    	
    	currIt = 0;
    	for(Month row : csvReader){
    		currIt++;
    		//Get Line number holds state of last row returned
            int rnum = csvReader.getLineNumber();
            Assert.assertTrue(currIt == rnum);
            values.add(row);
        }
        
    	csvReader.close();
        Assert.assertTrue(values != null);
        Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        Assert.assertEquals(values.get(0).getM1(), "m_01");
        
        logger.info("Rfc4180Iterator completed successfully");
    }
    
    @Test
    public void Rfc4180ReuseReader() throws CsvReaderException, IOException {
    	Reader reader = null;
    	CsvReader<Month> csvReader = null;
        List<Month> rows = null;
        
        csvReader = CsvReaderFactory.newInstance(Month.class);
        
        reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader);
    	rows = csvReader.read();
    	Assert.assertTrue("Fail: default format. ", rows.size() == 5);
    	Assert.assertTrue("Fail: default format.", "'enero'".equals(rows.get(0).getM1()));
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader)
                .escapeheader(true);
    	rows = csvReader.read();
    	Assert.assertTrue("Fail: escapeheader(true).", rows.size() == 5);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader)
                .fromLine(4);
    	rows = csvReader.read();
    	Assert.assertTrue("Fail: escapeheader(true), fromLine(4).", rows.size() == 3);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader)
    			.escapeheader(false)
                .fromLine(1)
                .maxLines(4);
    	rows = csvReader.read();
    	Assert.assertTrue("Fail: escapeheader(false), fromLine(1), maxLines(4).", rows.size() == 4);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader)
    			.escapeheader(true)
                .fromLine(1)
                .maxLines(4);
    	rows = csvReader.read();
    	Assert.assertTrue("Fail: escapeheader(true), fromLine(1), maxLines(4).", rows.size() == 3);
    	csvReader.close();
    	
        
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader)
    			.escapeheader(false)
                .fromLine(3)
                .maxLines(3);
    	rows = csvReader.read();
    	Assert.assertTrue("Fail: escapeheader(false), fromLine(3), maxLines(3).", rows.size() == 4);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
    	csvReader.setReader(reader)
    			.escapeheader(true)
                .fromLine(3)
                .maxLines(3);
    	rows = csvReader.read();
    	Assert.assertTrue("Fail: escapeheader(true), fromLine(3), maxLines(3).", rows.size() == 3);
    	csvReader.close();
    	
        
        logger.info("Rfc4180ReuseReader completed successfully");
            
    }
}

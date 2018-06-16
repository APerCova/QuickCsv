package net.apercova.quickcsv.reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.apercova.quickcsv.CsvReaderFactory;

public class SimpleCsvReaderPatternTest {
	
	private static final Logger logger = Logger.getLogger(SimpleCsvReaderPatternTest.class.getName());
    
    @Before
    public void init() {
    }
    
    @Test
    public void IteratorTest() throws CsvReaderException, IOException {
    	CsvReader<List<String>> csvReader = CsvReaderFactory.newInstance();
    	
    	//Getting an Iterator
    	Reader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader);
    	List<List<String>> values = new ArrayList<List<String>>();
    	Iterator<List<String>> it = csvReader.iterator();
    	
    	int currIt = 0;
    	while(it.hasNext()){
    		currIt++;
            List<String> row = it.next();
            int rnum = csvReader.getLineNumber();
            Assert.assertTrue(currIt == rnum);
            Assert.assertTrue(row.size() == 12);
            values.add(row);
        }
    	
    	csvReader.close();
    	Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
    	
        //CsvReader instance is actually an Iterator
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader);
    	values = new ArrayList<List<String>>();
    	
    	currIt = 0;
    	while(csvReader.hasNext()){
    		currIt++;
            List<String> row = csvReader.next();
            int rnum = csvReader.getLineNumber();
            Assert.assertTrue(currIt == rnum);
            Assert.assertTrue(row.size() == 12);
            values.add(row);
        }
    	
        csvReader.close();
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        
        //CsvReader instance can be used inside enhanced for loop
        reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader);
    	values = new ArrayList<List<String>>();
    	
    	currIt = 0;
    	for(List<String> row : csvReader){
    		currIt++;
    		//Get Line number holds state of last row returned
            int rnum = csvReader.getLineNumber();
            Assert.assertTrue(currIt == rnum);
            Assert.assertTrue(row.size() == 12);
            values.add(row);
        }
        
    	csvReader.close();
        Assert.assertTrue(values != null);
        Assert.assertEquals(values.size(), 6);
        Assert.assertEquals(values.get(0).get(0), "m_01");
        
        logger.info("IteratorTest completed successfully");
    }
    
    @Test
    public void ReuseReaderTest() throws CsvReaderException, IOException {
    	Reader reader = null;
    	CsvReader<List<String>> csvReader = null;
        List<List<String>> rows = null;
        
        csvReader = CsvReaderFactory.newInstance();
        
        reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader);
    	rows = csvReader.read();
    	Assert.assertEquals(rows.size(), 6);
    	Assert.assertEquals("m_01", rows.get(0).get(0));
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader)
                .escapeheader(true);
    	rows = csvReader.read();
    	Assert.assertEquals(rows.size(), 5);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader)
                .escapeheader(true)
                .fromLine(4);
    	rows = csvReader.read();
    	Assert.assertEquals(rows.size(), 3);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader)
    			.escapeheader(true)
                .fromLine(1)
                .maxLines(4);
    	rows = csvReader.read();
    	Assert.assertEquals(rows.size(), 3);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader)
    			.escapeheader(false)
                .fromLine(1)
                .maxLines(4);
    	rows = csvReader.read();
    	Assert.assertEquals(rows.size(), 4);
    	csvReader.close();
    	
        
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader)
    			.escapeheader(true)
                .fromLine(3)
                .maxLines(3);
    	rows = csvReader.read();
    	Assert.assertEquals(rows.size(), 3);
    	csvReader.close();
    	
    	
    	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"), Charset.forName("utf-8"));
    	csvReader.setReader(reader)
    			.escapeheader(false)
                .fromLine(3)
                .maxLines(3);
    	rows = csvReader.read();
    	Assert.assertEquals(rows.size(), 4);
    	csvReader.close();
    	
        
        logger.info("ReuseReaderTest completed successfully");
            
    }
}

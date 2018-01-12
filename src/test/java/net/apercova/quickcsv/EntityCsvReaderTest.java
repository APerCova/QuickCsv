package net.apercova.quickcsv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.apercova.quickcsv.entity.Month;
import net.apercova.quickcsv.entity.WeekDays;

public class EntityCsvReaderTest {

	public InputStream inStream;
    public Reader reader;
  
    @Test
    public void test01() throws Exception {
        System.out.println("========== ObjectCsvReaderTest#test01()==========");
       
        CsvReader<Month> csvReader = null;
        List<Month> rows = null;
        csvReader = EntityCsvReader.getInstance(Month.class);
        try{
        	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
        	csvReader.setReader(reader);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: default fromat.", rows.size() == 5);
        	
        	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
        	csvReader.setReader(reader)
                    .escapeheader(true)
                    .fromLine(4);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: escapeheader(true), fromLine(4).", rows.size() == 3);

        	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
        	csvReader.setReader(reader)
        			.escapeheader(true)
                    .fromLine(1)
                    .maxLines(4);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: escapeheader(true), fromLine(1), maxLines(4).", rows.size() == 3);
            
        	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
        	csvReader.setReader(reader)
        			.escapeheader(false)
                    .fromLine(2)
                    .maxLines(4);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: escapeheader(false), fromLine(2), maxLines(5).", rows.size() == 5);
            
            reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
        	csvReader.setReader(reader)
        			.escapeheader(true)
                    .fromLine(3)
                    .maxLines(3);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: escapeheader(true), fromLine(3), maxLines(3).", rows.size() == 3);
        	
        	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
        	csvReader.setReader(reader)
        			.escapeheader(false)
                    .fromLine(3)
                    .maxLines(3);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: escapeheader(false), fromLine(3), maxLines(3).", rows.size() == 4);
        	
            for(Month row: rows){
                System.out.println(row);
            }
            
        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
            	csvReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }
    
    @Test
    public void test02() throws Exception {
        System.out.println("========== ObjectCsvReaderTest#test02()==========");
        String line = "sunday,\"\"\"monday\"\"\",\"tues,day\",wednesday,\"\"\"thu,rsday\"\"\",\"\"\"\"\"friday\"\"\"\"\",saturday";
        System.out.println("source: "+line);

        
        WeekDays values = EntityCsvReader.readLine(WeekDays.class, line, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);

        Assert.assertTrue("Error["+values.getD_01()+"]","sunday".equals(values.getD_01()));
        Assert.assertTrue("Error["+values.getD_02()+"]","\"monday\"".equals(values.getD_02()));
        Assert.assertTrue("Error["+values.getD_03()+"]","tues,day".equals(values.getD_03()));
        Assert.assertTrue("Error["+values.getD_04()+"]","wednesday".equals(values.getD_04()));
        Assert.assertTrue("Error["+values.getD_05()+"]","\"thu,rsday\"".equals(values.getD_05()));
        Assert.assertTrue("Error["+values.getD_06()+"]","\"\"friday\"\"".equals(values.getD_06()));
        Assert.assertTrue("Error["+values.getD_07()+"]","saturday".equals(values.getD_07()));

        System.out.println("read: "+values);
        System.out.println("======================================================");
    }

    @Test
    public void test03(){
        System.out.println("========== ObjectCsvReaderTest#test03()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        List<Month> rows = new ArrayList<Month>();
        CsvReader<Month> csvReader = null;
        try{
        	csvReader = EntityCsvReader.getInstance(Month.class);
        	csvReader.setReader(reader);

            while(csvReader.hasNext()){
                Month row = csvReader.next();
                int rnum = csvReader.getLineNumber();
                rows.add(row);
                System.out.printf("%d-%s %n", rnum, row);
            }

            Assert.assertTrue("Empty list", rows.size() == 6);
        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
            	csvReader.close();
                inStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }
    
    @Test
    @SuppressWarnings("rawtypes")
	public void test04(){
        System.out.println("========== ObjectCsvReaderTest#test04()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        List<Month> rows = new ArrayList<Month>();
        CsvReader<Month> csvReader = null;
        try{
        	csvReader = EntityCsvReader.getInstance(Month.class);
        	csvReader.setReader(reader);

            Iterator<Month> it = csvReader.iterator();
            while(it.hasNext()){
                Month row = it.next();
                int rnum = ((CsvReader)it).getLineNumber();
                rows.add(row);
                System.out.printf("%d-%s %n", rnum, row);
            }

            Assert.assertTrue("Empty list", rows.size() == 6);
        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
            	csvReader.close();
                inStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }

    @Test
    public void test05(){
        System.out.println("========== ObjectCsvReaderTest#test05()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        List<Month> rows = new ArrayList<Month>();
        CsvReader<Month> csvReader = null;
        try{
        	csvReader = EntityCsvReader.getInstance(Month.class);
        	csvReader.setReader(reader);

            for(Month row: csvReader){
                int rnum = csvReader.getLineNumber();
                rows.add(row);
                System.out.printf("%d-%s %n", rnum, row);
            }

            Assert.assertTrue("Empty list", rows.size() == 6);
        }catch(Exception e){
        	e.printStackTrace();
            Assert.fail(e.getMessage());
        } finally {
            try {
            	csvReader.close();
                inStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }
   
}

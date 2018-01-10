package net.apercova.quickcsv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SimpleCsvReaderTest {

	public InputStream inStream;
    public Reader reader;
    
    @Test
    public void test01() throws Exception {
        System.out.println("========== SimpleCsvReaderTest#test01()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        CsvReader<List<String>> csvReader = null;
        try{
        	csvReader = new SimpleCsvReader();
            csvReader.setReader(reader)
                     .setDelimiter(CsvCons.COMMA)
                     .setQuote(CsvCons.DOUBLE_QUOTE);
            
            List<List<String>> rows = csvReader.read();
            
            Assert.assertTrue("Empty list", rows.size() > 0);
            
            for(List<String> row: rows){
                System.out.println(row);
            }

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
    public void test02() throws Exception {
        System.out.println("========== SimpleCsvReaderTest#test02()==========");
        String line = "sunday,\"\"\"monday\"\"\",\"tues,day\",wednesday,\"\"\"thu,rsday\"\"\",\"\"\"\"\"friday\"\"\"\"\",saturday";
        System.out.println("source: "+line);

        List<String> values = SimpleCsvReader.readLine(line, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);

        Assert.assertTrue("Error["+values.get(0)+"]","sunday".equals(values.get(0)));
        Assert.assertTrue("Error["+values.get(1)+"]","\"monday\"".equals(values.get(1)));
        Assert.assertTrue("Error["+values.get(2)+"]","tues,day".equals(values.get(2)));
        Assert.assertTrue("Error["+values.get(3)+"]","wednesday".equals(values.get(3)));
        Assert.assertTrue("Error["+values.get(4)+"]","\"thu,rsday\"".equals(values.get(4)));
        Assert.assertTrue("Error["+values.get(5)+"]","\"\"friday\"\"".equals(values.get(5)));
        Assert.assertTrue("Error["+values.get(6)+"]","saturday".equals(values.get(6)));


        System.out.println("read: "+Arrays.toString(values.toArray()));
        System.out.println("======================================================");
    }

    @Test
    public void test03(){
        System.out.println("========== SimpleCsvReaderTest#test03()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        List<List<String>> rows = new ArrayList<List<String>>();
        CsvReader<List<String>> csvReader = null;
        try{
        	csvReader = new SimpleCsvReader();
        	csvReader.setReader(reader)
                    .setDelimiter(CsvCons.COMMA)
                    .setQuote(CsvCons.DOUBLE_QUOTE);

            while(csvReader.hasNext()){
                List<String> row = csvReader.next();
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
        System.out.println("========== SimpleCsvReaderTest#test04()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        List<List<String>> rows = new ArrayList<List<String>>();
        CsvReader<List<String>> csvReader = null;
        try{
        	csvReader = new SimpleCsvReader();
        	csvReader.setReader(reader)
                    .setDelimiter(CsvCons.COMMA)
                    .setQuote(CsvCons.DOUBLE_QUOTE);

            Iterator<List<String>> it = csvReader.iterator();
            while(it.hasNext()){
                List<String> row = it.next();
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
        System.out.println("========== SimpleCsvReaderTest#test05()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        List<List<String>> rows = new ArrayList<List<String>>();
        CsvReader<List<String>> csvReader = null;
        try{
        	csvReader = new SimpleCsvReader();
        	csvReader.setReader(reader)
                    .setDelimiter(CsvCons.COMMA)
                    .setQuote(CsvCons.DOUBLE_QUOTE);

            for(List<String> row: csvReader){
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

    @Test
    public void test06() throws Exception {
        System.out.println("========== SimpleCsvReaderTest#test06()==========");
       
        CsvReader<List<String>> csvReader = null;
        List<List<String>> rows = null;
        csvReader = new SimpleCsvReader();
        try{
        	reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("Months.csv"));
        	csvReader.setReader(reader)
                    .escapeheader(true);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: escapeheader(true).", rows.size() == 5);
        	
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
                    .fromLine(1)
                    .maxLines(4);
        	
        	rows = csvReader.read();
        	Assert.assertTrue("Fail: escapeheader(false), fromLine(1), maxLines(4).", rows.size() == 4);
            
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
        	
            for(List<String> row: rows){
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
    
}

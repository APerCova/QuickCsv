package net.apercova.quickcsv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DefaultCsvReaderStaticTest {

    public InputStream inStream;
    public Reader reader;

    @Test
    public void test01() throws Exception {

        System.out.println("========== SimpleCsvReaderStaticTest#test01()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
            reader = new InputStreamReader(inStream);
            //Defaulr RFC 4180 format
            List<List<String>> values = DefaultCsvReader.read(reader);

            Assert.assertTrue(values != null);
            for(List<String> row: values){
                Assert.assertTrue("Row: " + String.valueOf(row),row.size() == 12);
                System.out.println("ROW: "+row+"");
            }

            Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        }catch(CsvReaderException e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                inStream.close();
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        System.out.println("======================================================");

    }
    
    @Test
    public void test02() throws Exception {

        System.out.println("========== SimpleCsvReaderStaticTest#test02()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
            reader = new InputStreamReader(inStream);
            //Defaulr RFC 4180 format escaping header
            List<List<String>> values = DefaultCsvReader.read(
            		reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, 0, 0);

            Assert.assertTrue(values != null);
            for(List<String> row: values){
                Assert.assertTrue("Row: " + String.valueOf(row),row.size() == 12);
                System.out.println("ROW: "+row+"");
            }

            Assert.assertTrue("Size: "+values.size(), values.size() == 5);
        }catch(CsvReaderException e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                inStream.close();
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        System.out.println("======================================================");

    }
    
    @Test  
    public void test03() throws Exception {

        System.out.println("========== SimpleCsvReaderStaticTest#test03()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
            reader = new InputStreamReader(inStream);
            //Default RFC 4180 format limited to a fixed fow range
            List<List<String>> values = DefaultCsvReader.read(
            		reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, 1, 3 );

            Assert.assertTrue(values != null);
            for(List<String> row: values){
                Assert.assertTrue("Row: " + String.valueOf(row),row.size() == 12);
                System.out.println("ROW: "+row+"");
            }

            Assert.assertTrue("Size: "+values.size(), values.size() == 2);
        }catch(CsvReaderException e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                inStream.close();
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        System.out.println("======================================================");

    }
    
    @Test
    public void test04() throws Exception {

        System.out.println("========== SimpleCsvReaderStaticTest#test04()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("MonthsCustom.csv");
            reader = new InputStreamReader(inStream);
            //Custom Format
            List<List<String>> values = DefaultCsvReader.read(
            		reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, false, 0, 0);

            Assert.assertTrue(values != null);
            for(List<String> row: values){
                Assert.assertTrue("Row: " + String.valueOf(row),row.size() == 12);
                System.out.println("ROW: "+row+"");
            }

            Assert.assertTrue("Size: "+values.size(), values.size() == 6);
        }catch(CsvReaderException e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                inStream.close();
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        System.out.println("======================================================");

    }
}

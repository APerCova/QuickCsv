package net.apercova.quickcsv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ObjectCsvReaderStaticTest {

    public InputStream inStream;
    public Reader reader;

    @Test
    public void test01() throws Exception {

        System.out.println("========== ObjectCsvReaderStaticTest#test01()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
            reader = new InputStreamReader(inStream);
            //Default RFC 4180 format
            List<Month> values = ObjectCsvReader.read(Month.class, reader);

            Assert.assertTrue(values != null);
            for(Month row: values){
                Assert.assertTrue("Row: " + String.valueOf(row),row.getM12().length() > 0);
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
    public void test02() throws Exception {

        System.out.println("========== ObjectCsvReaderStaticTest#test02()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
            reader = new InputStreamReader(inStream);
            //Default RFC 4180 format limited to a fixed fow range
            List<Month> values = ObjectCsvReader.read(
            		Month.class, reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, 1, 3 );

            Assert.assertTrue(values != null);
            for(Month row: values){
                Assert.assertTrue("Row: " + String.valueOf(row),row.getM12().length() > 0);
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
    public void test03() throws Exception {

        System.out.println("========== ObjectCsvReaderStaticTest#test03()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("MonthsCustom.csv");
            reader = new InputStreamReader(inStream);
            //Custom Format
            List<Month> values = ObjectCsvReader.read(
            		Month.class, reader, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, 0, 0);

            Assert.assertTrue(values != null);
            for(Month row: values){
                Assert.assertTrue("Row: " + String.valueOf(row),row.getM12().length() > 0);
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
}

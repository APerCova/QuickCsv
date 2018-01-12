package net.apercova.quickcsv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.apercova.quickcsv.entity.CompositeType;
import net.apercova.quickcsv.entity.Month;

public class EntityCsvReaderStaticTest {

    public InputStream inStream;
    public Reader reader;

    @Test
    public void test01() throws Exception {

        System.out.println("========== ObjectCsvReaderStaticTest#test01()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
            reader = new InputStreamReader(inStream);
            //Default RFC 4180 format
            List<Month> values = EntityCsvReader.read(Month.class, reader);

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
            List<Month> values = EntityCsvReader.read(
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
            List<Month> values = EntityCsvReader.read(
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
    
    @Test
    public void test04() throws Exception {

        System.out.println("========== ObjectCsvReaderStaticTest#test04()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("MonthsCustom.csv");
            reader = new InputStreamReader(inStream);
            //Custom Format
            List<Month> values = EntityCsvReader.read(
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

    @Test
    public void test05() throws Exception {

        System.out.println("========== ObjectCsvReaderStaticTest#test05()==========");
        try{
        	inStream = ClassLoader.getSystemResourceAsStream("CompositeTypes.csv");
            reader = new InputStreamReader(inStream);
            //Datatype conversion 
            List<CompositeType> values = EntityCsvReader.read(
            		CompositeType.class, reader, true);

            Assert.assertTrue("Empty list",values.size() > 0);
            
            for(CompositeType row: values) {
            	Assert.assertTrue("Null Date: "+row,values.get(0).getDate() != null);
                Assert.assertTrue("Null Charset: "+row,values.get(0).getCharset() != null);
                Assert.assertTrue("Null MessageDigest: "+row,values.get(0).getMessageDigest() != null);
            }      
            
            System.out.println(values);
            
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

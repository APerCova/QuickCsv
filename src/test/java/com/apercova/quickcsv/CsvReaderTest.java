package com.apercova.quickcsv;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CsvReaderTest {

    public InputStream inStream;
    public Reader reader;

    @Test
    public void read() throws Exception {
        System.out.println("========== CsvReaderTest#read()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        char delimiter = CsvCons.COMMA;
        char quote = CsvCons.DOUBLE_QUOTE;

        CsvReader csvReader = new CsvReader()
                .from(reader)
                .withDelimiter(delimiter)
                .withQuote(quote);
        try{

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
                inStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }

    @Test
    public void readStatic() throws Exception {

        System.out.println("========== CsvReaderTest#readStatic()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);
        try{
            //Defaulr RFC 4180 format
            List<List<String>> values = CsvReader.read(reader);

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
    public void readStaticCustom() throws Exception {
        System.out.println("========== CsvReaderTest#readStaticCustom()==========");
        inStream = ClassLoader.getSystemResourceAsStream("MonthsCustom.csv");
        reader = new InputStreamReader(inStream);
        char delimiter = CsvCons.PIPE;
        char quote = CsvCons.SINGLE_QUOTE;
        try{
            //Custom delimiter and quote gives same result
            List<List<String>> values = CsvReader.read(reader, delimiter, quote);

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
    public void readLine() throws Exception {
        System.out.println("========== CsvReaderTest#readLine()==========");
        String line = "sunday,\"\"\"monday\"\"\",\"tues,day\",wednesday,\"\"\"thu,rsday\"\"\",\"\"\"\"\"friday\"\"\"\"\",saturday";
        System.out.println("source: "+line);

        char delimiter = CsvCons.COMMA;
        char quote = CsvCons.DOUBLE_QUOTE;

        List<String> values = CsvReader.readLine(line, delimiter, quote);

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
    public void readByLineIterableWhile(){
        System.out.println("========== CsvReaderTest#readByLineIterableWhile()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        char delimiter = CsvCons.COMMA;
        char quote = CsvCons.DOUBLE_QUOTE;

        List<List<String>> rows = new ArrayList<List<String>>();
        CsvReader csvReader = new CsvReader()
                .from(reader)
                .withDelimiter(delimiter)
                .withQuote(quote);
        try{


            while(csvReader.hasNext()){
                List<String> row = csvReader.next();
                int rnum = csvReader.getLineNumber();
                rows.add(row);
                System.out.printf("%d-%s %n", rnum, row);
            }

            Assert.assertTrue("Empty list", rows.size() > 0);
        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }

    @Test
    public void readByLineIteratorWhile(){
        System.out.println("========== CsvReaderTest#readByLineIteratorWhile()==========");
        inStream = ClassLoader.getSystemResourceAsStream("Months.csv");
        reader = new InputStreamReader(inStream);

        char delimiter = CsvCons.COMMA;
        char quote = CsvCons.DOUBLE_QUOTE;

        List<List<String>> rows = new ArrayList<List<String>>();
        CsvReader csvReader = new CsvReader()
                .from(reader)
                .withDelimiter(delimiter)
                .withQuote(quote);
        try{

            Iterator<List<String>> it = csvReader.iterator();
            while(it.hasNext()){
                List<String> row = it.next();
                int rnum = ((CsvReader)it).getLineNumber();
                rows.add(row);
                System.out.printf("%d-%s %n", rnum, row);
            }

            Assert.assertTrue("Empty list", rows.size() > 0);
        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }

    @Test
    public void readByLineForEach(){
        System.out.println("========== CsvReaderTest#readByLineForEach()==========");
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("Months.csv");

        char delimiter = CsvCons.COMMA;
        char quote = CsvCons.DOUBLE_QUOTE;

        List<List<String>> rows = new ArrayList<List<String>>();
        CsvReader reader = new CsvReader()
                .from(new InputStreamReader(inputStream))
                .withDelimiter(delimiter)
                .withQuote(quote);
        try{

            for(List<String> row: reader){
                int rnum = reader.getLineNumber();
                rows.add(row);
                System.out.printf("%d-%s %n", rnum, row);
            }

            Assert.assertTrue("Empty list", rows.size() > 0);
        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======================================================");
    }

}
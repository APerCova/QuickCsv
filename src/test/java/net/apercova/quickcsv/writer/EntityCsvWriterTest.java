package net.apercova.quickcsv;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.apercova.quickcsv.entity.Country;

public class EntityCsvWriterTest {

    @Test
    public void test01() throws Exception {
        System.out.println("========== ObjectCsvWriterTest#test01()==========");

        Writer writer = null;
        CsvWriter<Country> csvWriter = EntityCsvWriter.getInstance(Country.class);
        try {
        	List<Country> countries = new ArrayList<Country>();
        	countries.add(new Country("United States of America","Washington D.C.","US"));
        	countries.add(new Country("Estados Unidos Mexicanos","Ciudad de México, \"CDMX\"","MX"));
        	countries.add(new Country("Australia","Sidney","AU"));
        	countries.add(new Country("United Kingdom","London","OK"));
        	countries.add(new Country("Belize","Belmopán","BZ"));

            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setLines(countries)
                    .write();

            String result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: complete writing", result.startsWith("NAME"+CsvCons.COMMA));
            writer.close();
            
            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setDelimiter(CsvCons.COMMA)
                    .setQuote(CsvCons.DOUBLE_QUOTE)
                    .setLines(countries)
                    .escapeHeader(true)
                    .setAutoflush(true)
                    .write();
            
            result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: escape header", result.startsWith("United States of America"+CsvCons.COMMA));
            writer.close();
            
            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setDelimiter(CsvCons.PIPE)
                    .setLines(countries)
                    .escapeHeader(true)
                    .setAutoflush(true)
                    .write();
            
            result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: custom delimiter", result.startsWith("United States of America"+CsvCons.PIPE));
            writer.close();
            
            

        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
            	if(csvWriter != null) {
            		csvWriter.close();
            	}
                if(writer != null) {
                	writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("========================================================");
    }

}

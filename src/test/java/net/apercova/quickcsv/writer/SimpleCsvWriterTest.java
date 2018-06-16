package net.apercova.quickcsv;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultCsvWriterTest {

    @Test
    public void write() throws Exception {
        System.out.println("========== CsvWriterTest#write()==========");

        Writer writer = null;
        CsvWriter<List<String>> csvWriter = new DefaultCsvWriter();
        try {
            
            List<List<String>> csvValues = new ArrayList<List<String>>();
            csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
            csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
            csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "Ciudad de México, \"CDMX\"", "MX"}));
            csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
            csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
            csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));

            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setLines(csvValues)
                    .write();

            String result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: complete writing", result.startsWith("NAME"+CsvCons.COMMA));
            writer.close();
            
            writer = new StringWriter();
            csvWriter.setWriter(writer)
                    .setDelimiter(CsvCons.COMMA)
                    .setQuote(CsvCons.DOUBLE_QUOTE)
                    .setLines(csvValues)
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
                    .setLines(csvValues)
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

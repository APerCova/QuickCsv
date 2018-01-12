package net.apercova.quickcsv;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultCsvWriterStaticTest {
	
    @Test
    public void test01() throws Exception {
        System.out.println("========== SimpleCsvWriterStaticTest#test01()==========");

        Writer writer = null;

        try {
            List<List<String>> csvValues = new ArrayList<List<String>>();
            csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
            csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
            csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "Ciudad de México, \"CDMX\"", "MX"}));
            csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
            csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
            csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));

            writer = new StringWriter();
            
            DefaultCsvWriter.write(writer, csvValues);
            writer.flush();

            String result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: complete writing",result.startsWith("NAME"));
            
            writer = new StringWriter();
            DefaultCsvWriter.write(writer, csvValues, true);
            writer.flush();

            result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: escape header",result.startsWith("United States of America"));
            
            writer = new StringWriter();
            DefaultCsvWriter.write(writer, csvValues, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, true);
            writer.flush();

            result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: custom delimiter",result.startsWith("United States of America"+CsvCons.PIPE));

        }catch(Exception e){
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                if(writer != null)
                    writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("========================================================");
    }

    @Test
    public void test02() throws Exception {
        System.out.println("========== SimpleCsvWriterStaticTest#test02()==========");

        String value = "México";
        String formated = DefaultCsvWriter.formatValue(value, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        Assert.assertTrue("Simple value format error", value.equals(formated));
        System.out.printf("from (%s) to (%s)%n", value, formated);

        value = "Ciudad de México, CDMX";
        formated = DefaultCsvWriter.formatValue(value, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        Assert.assertTrue("Delimiter in value format error",
                formated.equals("\"Ciudad de México, CDMX\""));
        System.out.printf("from (%s) to (%s)%n", value, formated);

        value = "\"MX\"";
        formated = DefaultCsvWriter.formatValue(value, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        Assert.assertTrue("Quote in value format error",
                formated.equals(CsvCons.DOUBLE_QUOTE+""+CsvCons.DOUBLE_QUOTE+value+CsvCons.DOUBLE_QUOTE+""+CsvCons.DOUBLE_QUOTE));
        System.out.printf("from (%s) to (%s)%n", value, formated);
        
        value = "'Ciudad de México, 'CDMX''";
        formated = DefaultCsvWriter.formatValue(value, CsvCons.PIPE, CsvCons.SINGLE_QUOTE);
        Assert.assertTrue("Quote in value format error",
                formated.equals("'''Ciudad de México, ''CDMX'''''"));
        System.out.printf("from (%s) to (%s)%n", value, formated);

        System.out.println("========================================================");

    }

}

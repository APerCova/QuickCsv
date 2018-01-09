package net.apercova.quickcsv;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvWriterTest {
    public char delimiter;
    public char quote;
    public boolean autoflush;

    @Test
    public void write() throws Exception {
        System.out.println("========== CsvWriterTest#write()==========");

        Writer writer = null;
        delimiter = CsvCons.PIPE;
        quote= CsvCons.SINGLE_QUOTE;
        autoflush = true;

        try {
            writer = new StringWriter();
            List<List<String>> csvValues = new ArrayList<List<String>>();
            csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
            csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
            csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "'Ciudad de México, ''CDMX''", "'MX'"}));
            csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
            csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
            csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));

            CsvWriter<List<String>> csvWriter = new SimpleCsvWriter()
                    .setWriter(writer)
                    .setDelimiter(delimiter)
                    .setQuote(quote)
                    .setLines(csvValues)
                    .setAutoflush(autoflush);

            csvWriter.write();

            String dest = ((StringWriter)writer).toString();
            Assert.assertTrue(dest.length() > 0);
            System.out.println(dest);

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
    public void writeStatic1() throws Exception {
        System.out.println("========== CsvWriterTest#writeStatic1()==========");

        Writer writer = null;
        delimiter = CsvCons.COMMA;
        quote= CsvCons.DOUBLE_QUOTE;

        try {
            writer = new StringWriter();
            List<List<String>> csvValues = new ArrayList<List<String>>();
            csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
            csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
            csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "Ciudad de México, \"CDMX\"", "MX"}));
            csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
            csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
            csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));

            SimpleCsvWriter.write(writer, csvValues, delimiter, quote);
            writer.flush();

            String dest = ((StringWriter)writer).toString();
            Assert.assertTrue(dest.length() > 0);
            System.out.println(dest);

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
    public void writeStatic2() throws Exception {
        System.out.println("========== CsvWriterTest#writeStatic1()==========");

        Writer writer = null;
        delimiter = CsvCons.COMMA;
        quote= CsvCons.DOUBLE_QUOTE;
        autoflush = true;

        try {
            writer = new StringWriter();
            List<List<String>> csvValues = new ArrayList<List<String>>();
            csvValues.add(Arrays.asList(new String[]{"NAME", "CAPITAL", "ISO_CODE"}));
            csvValues.add(Arrays.asList(new String[]{"United States of America", "Washington D.C.", "US"}));
            csvValues.add(Arrays.asList(new String[]{"Estados Unidos Mexicanos", "Ciudad de México, \"CDMX\"", "MX"}));
            csvValues.add(Arrays.asList(new String[]{"Australia", "Sidney", "AU"}));
            csvValues.add(Arrays.asList(new String[]{"United Kingdom", "London", "UK"}));
            csvValues.add(Arrays.asList(new String[]{"Belize", "Belmopán", "BZ"}));

            SimpleCsvWriter.write(writer, csvValues, delimiter, quote);
            writer.flush();

            String dest = ((StringWriter)writer).toString();
            Assert.assertTrue(dest.length() > 0);
            System.out.println(dest);

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
    public void formatValue() throws Exception {
        System.out.println("========== CsvWriterTest#formatValue()==========");

        delimiter = CsvCons.COMMA;
        quote = CsvCons.DOUBLE_QUOTE;

        String value = "México";
        String formated = SimpleCsvWriter.formatValue(value, delimiter, quote);
        Assert.assertTrue("Simple value format error", value.equals(formated));
        System.out.printf("from (%s) to (%s)%n", value, formated);

        value = "Ciudad de México, CDMX";
        formated = SimpleCsvWriter.formatValue(value, delimiter, quote);
        Assert.assertTrue("Delimiter in value format error",
                formated.equals("\"Ciudad de México, CDMX\""));
        System.out.printf("from (%s) to (%s)%n", value, formated);

        value = "\"MX\"";
        formated = SimpleCsvWriter.formatValue(value, delimiter, quote);
        Assert.assertTrue("Quote in value format error",
                formated.equals(quote+""+quote+value+quote+""+quote));
        System.out.printf("from (%s) to (%s)%n", value, formated);

        delimiter = CsvCons.PIPE;
        quote = CsvCons.SINGLE_QUOTE;
        value = "'Ciudad de México, 'CDMX''";
        formated = SimpleCsvWriter.formatValue(value, delimiter, quote);
        Assert.assertTrue("Quote in value format error",
                formated.equals("'''Ciudad de México, ''CDMX'''''"));
        System.out.printf("from (%s) to (%s)%n", value, formated);

        System.out.println("========================================================");

    }

}
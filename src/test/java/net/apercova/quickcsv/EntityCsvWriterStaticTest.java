package net.apercova.quickcsv;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.apercova.quickcsv.entity.CompositeType;
import net.apercova.quickcsv.entity.Country;

public class EntityCsvWriterStaticTest {
	
    @Test
    public void test01() throws Exception {
        System.out.println("========== ObjectCsvWriterStaticTest#test01()==========");

        Writer writer = null;

        try {
        	List<Country> countries = new ArrayList<Country>();
        	countries.add(new Country("United States of America","Washington D.C.","US"));
        	countries.add(new Country("Estados Unidos Mexicanos","Ciudad de México, \"CDMX\"","MX"));
        	countries.add(new Country("Australia","Sidney","AU"));
        	countries.add(new Country("United Kingdom","London","OK"));
        	countries.add(new Country("Belize","Belmopán","BZ"));

            writer = new StringWriter();
            
            EntityCsvWriter.write(Country.class, writer, countries);
            writer.flush();

            String result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: complete writing",result.startsWith("NAME"));
            
            writer = new StringWriter();
            EntityCsvWriter.write(Country.class, writer, countries, true);
            writer.flush();

            result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: escape header",result.startsWith("United States of America"));
            
            writer = new StringWriter();
            EntityCsvWriter.write(Country.class, writer, countries, CsvCons.PIPE, CsvCons.SINGLE_QUOTE, true);
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
        System.out.println("========== ObjectCsvWriterStaticTest#test02()==========");

        Writer writer = null;

        try {
        	List<CompositeType> compTypes = new ArrayList<CompositeType>();
        	compTypes.add(new CompositeType(
        			Calendar.getInstance().getTime(),
        			Charset.defaultCharset(),
        			MessageDigest.getInstance("MD5")));

            writer = new StringWriter();
            EntityCsvWriter.write(CompositeType.class, writer, compTypes);
            writer.flush();

            String result = ((StringWriter)writer).toString();
            System.out.println(result);
            Assert.assertTrue("Fail: complete writing",result.startsWith("DATE"));
            
            writer = new StringWriter();
            EntityCsvWriter.write(CompositeType.class, writer, compTypes, true);
            writer.flush();

            result = ((StringWriter)writer).toString();
            System.out.println(result);
            DateFormat testdf = new SimpleDateFormat("yyyy-MM-dd");
            String test = testdf.format(Calendar.getInstance().getTime());
            Assert.assertTrue("Fail: complete writing",result.startsWith("2018-01-12"));
            
            

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
}

# QuickCsv
Quick and easy handle of comma-separated values (CSV) as described in RFC 4180.  
<a href="https://tools.ietf.org/html/rfc4180">Common Format and MIME Type for Comma-Separated Values (CSV) Files</a>  
  
#### See API:  (https://apercova.github.io/QuickCsv/apidocs/)  
  
#### See docs on the wiki:  (https://github.com/apercova/QuickCsv/wiki)
  
***
  
## Reading a file the quickest way:
  
> - First the imports...
```java
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.quickcsv.CsvReader;
import com.quickcsv.CsvReaderException;
import com.quickcsv.util.IterableLineNumberReader;
```
> - java up to 1.6...
```java
    Reader reader;
    try {
        reader = new InputStreamReader(
                new FileInputStream("CsvFile.csv"), 
                Charset.forName("utf-8"));
        
        List<List<String>> values = CsvReader.read(reader);
        
    } catch (IOException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
        
    } catch (CsvReaderException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
        
    } finally {
        try {
            reader.close();
            reader = null;
        } catch (IOException e) {
            logger.log(Level.FINE, "Unable to close reader", e);	
        }
    }
```
> - Now using java 1.7+...
```java
    try (Reader reader = new InputStreamReader(
            new FileInputStream("CsvFile.csv"), 
            Charset.forName("utf-8"))
        ) {
        
        List<List<String>> values = CsvReader.read(reader);
        
    } catch(IOException | CsvReaderException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
    }
```
#### See more csv reading usecases <a href="https://github.com/apercova/QuickCsv/wiki">here</a>. 
  
***
## Writing a file the quickest way:
  
> - First the imports...
```java
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.quickcsv.CsvCons;
import com.quickcsv.CsvWriter;
import com.quickcsv.CsvWriterException;
```
> - java up to 1.6...
```java
    Writer writer = null;
    try {
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("CsvFile.csv"), 
                Charset.forName("utf-8")));
        
        List<List<String>> values = new LinkedList<List<String>>();
        values.add(Arrays.asList(new String[] {"ISO_CODE","NAME","CAPITAL"}));
        values.add(Arrays.asList(new String[] {"US","United States of America",""}));
        values.add(Arrays.asList(new String[] {"MX","Estados Unidos Mexicanos","Ciudad de México, \"CDMX\""}));
        values.add(Arrays.asList(new String[] {"AU","Austalia","Sidney"}));
        
        CsvWriter.write(writer, values, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        
    } catch(IOException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
    } catch (CsvWriterException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
    } finally {
        try {
            writer.close();
            writer = null;
        } catch (IOException e) {
            logger.log(Level.FINE, "Failed to close resource", e);	
        }
    }
```
> - Now using java 1.7+...
```java
    try (
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("CsvFile.csv"), 
                Charset.forName("utf-8")));
        ) {

        
        List<List<String>> values = new LinkedList<List<String>>();
        values.add(Arrays.asList(new String[] {"ISO_CODE","NAME","CAPITAL"}));
        values.add(Arrays.asList(new String[] {"US","United States of America",""}));
        values.add(Arrays.asList(new String[] {"MX","Estados Unidos Mexicanos","Ciudad de México, \"CDMX\""}));
        values.add(Arrays.asList(new String[] {"AU","Austalia","Sidney"}));
        
        CsvWriter.write(writer, values, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE);
        
    } catch(IOException | CsvWriterException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
    }
```
#### See more csv writing usecases <a href="https://github.com/apercova/QuickCsv/wiki">here</a>.  

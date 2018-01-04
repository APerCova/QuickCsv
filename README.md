# QuickCsv
Quick and easy-of-use handling of comma-separated values (CSV) as described in RFC 4180.  
<a href="https://tools.ietf.org/html/rfc4180">Common Format and MIME Type for Comma-Separated Values (CSV) Files</a>  
  
[![Build Status](https://travis-ci.org/apercova/QuickCsv.svg?branch=master)](https://travis-ci.org/apercova/QuickCsv)
  
## Setting it Up
### Setup on local /.m2 repository
> Note: Currently, QuickCsv is not available on Maven Central.  

To setup on your local repository, you need to follow this steps:

> - <a href="https://github.com/apercova/QuickCsv.git" >Clone</a> or <a href="https://github.com/apercova/QuickCsv/archive/master.zip">download</a> the repository.  
> - Unzip the content in case of download.
> - Change your current directory to the ***QuickCsv*** dir.
> - Execute:  
```bash
        $ mvn clean install
```

***
### Setup on Maven project

Add the dependency in your pom.xml  
```xml
 	<dependency>
  		<groupId>net.apercova</groupId>
  		<artifactId>quickcsv</artifactId>
  		<version>1.0.1712</version>
  	</dependency>
```
***
> See API:  (https://apercova.github.io/QuickCsv/apidocs/)  
  
> See docs on the wiki:  (https://github.com/apercova/QuickCsv/wiki)

> See complete usecases code: (https://github.com/apercova/QuickCsv/tree/master/usecases) 
  
***
  
## Reading a file the quickest way:
  
### Up to java 1.6
```java
    Reader reader = null;
    try {
        //Getting a reader for CsvFile.csv
        reader = new InputStreamReader(
                new FileInputStream("CsvFile.csv"), 
                Charset.forName("utf-8"));
        
        //Values are read as a row list
        List<List<String>> values = CsvReader.read(reader);
        
    } catch (IOException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
        
    } catch (CsvReaderException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
        
    } finally {
        try {
            if(reader != null)
                reader.close();
            reader = null;
        } catch (IOException e) {
            logger.log(Level.FINE, "Unable to close reader", e);	
        }
    }
```
### Using java 1.7+
```java
    try (
        //Getting a reader for CsvFile.csv
        Reader reader = new InputStreamReader(
                new FileInputStream("CsvFile.csv"),
                Charset.forName("utf-8"));				
        ) {
    
        //Values are read as a row list
        List<List<String>> values = CsvReader.read(reader);
    
    } catch (IOException | CsvReaderException e) {
        logger.log(Level.SEVERE, "Can't perform reading", e);
    }
```
#### See more csv reading usecases <a href="https://github.com/apercova/QuickCsv/wiki">here</a>. 
  
***
## Writing a file the quickest way:
  
### Up to java 1.6
```java
    Writer writer = null;
    try {
        //Getting a writer for Countries.csv
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("Countries.csv"), 
                Charset.forName("utf-8")));
        
        List<List<String>> values = new LinkedList<List<String>>();
        values.add(Arrays.asList(new String[] {"ISO_CODE","NAME","CAPITAL"}));
        values.add(Arrays.asList(new String[] {"US","United States of America",""}));
        values.add(Arrays.asList(new String[] {"MX","Estados Unidos Mexicanos","Ciudad de México, \"CDMX\""}));
        values.add(Arrays.asList(new String[] {"AU","Austalia","Sidney"}));
        
        //Writing out values
        CsvWriter.write(writer, values, true);//autoflush
        
        //also direct flush
        writer.flush();
        
    } catch(IOException e) {
        logger.log(Level.SEVERE, "Can't perform writing", e);
    } catch (CsvWriterException e) {
        logger.log(Level.SEVERE, "Can't perform writing", e);
    } finally {
        try {
            if(writer != null)
        		    writer.close();
                writer = null;
        } catch (IOException e) {
            logger.log(Level.FINE, "Failed to close resource", e);	
        }
    }
```
### Using java 1.7+
```java
    try (
        //Getting a writer for Countries.csv
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("Countries.csv"), 
                    Charset.forName("utf-8")));
        ){
        
        List<List<String>> values = new LinkedList<List<String>>();
        values.add(Arrays.asList(new String[] {"ISO_CODE","NAME","CAPITAL"}));
        values.add(Arrays.asList(new String[] {"US","United States of America",""}));
        values.add(Arrays.asList(new String[] {"MX","Estados Unidos Mexicanos","Ciudad de México, \"CDMX\""}));
        values.add(Arrays.asList(new String[] {"AU","Austalia","Sidney"}));
        
        //Writing out values
        CsvWriter.write(writer, values, true);//autoflush
        
        //also direct flush
        writer.flush();
        
    } catch(IOException | CsvWriterException e) {
        logger.log(Level.SEVERE, "Can't perform writing", e);
    } 
```
#### See more csv writing usecases <a href="https://github.com/apercova/QuickCsv/wiki">here</a>.  

package net.apercova.quickcsv.reader;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

public class EntityDataTypeConverterCsvReaderTest {

	private static final Logger logger = Logger.getLogger(EntityDataTypeConverterCsvReaderTest.class.getName());
	private InputStream complexTypeStream;
    
    @Before
    public void init() {
    	complexTypeStream = ClassLoader.getSystemResourceAsStream("ComplexType.csv");
    }
    
    @Test
    public void ComplexTypeTest() throws CsvReaderException, NoSuchAlgorithmException {
//    	Reader reader = new InputStreamReader(complexTypeStream, Charset.forName("utf-8"));
//    	CsvReader<ComplexType> csvReader = CsvReaderFactory.newInstance(reader, ComplexType.class);
//        List<ComplexType> values = csvReader.read();
//        
//        Assert.assertTrue(values != null);
//        Assert.assertTrue("Size: "+values.size(), values.size() == 4);
//        
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2015);
//        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
//        calendar.set(Calendar.DAY_OF_MONTH, 30);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        
//        Assert.assertEquals(calendar.getTimeInMillis(), values.get(0).getDate().getTime());
//        Assert.assertEquals(Charset.forName("iso-8859-1").name().toLowerCase(), values.get(0).getCharset().name().toLowerCase());
//        Assert.assertEquals(MessageDigest.getInstance("md5").getAlgorithm().toLowerCase(), values.get(0).getMessageDigest().getAlgorithm().toLowerCase());
//        
//        calendar.set(Calendar.YEAR, 2016);
//        calendar.set(Calendar.MONTH, Calendar.JANUARY);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 9);
//        calendar.set(Calendar.MINUTE, 51);
//        calendar.set(Calendar.SECOND, 13);
//        calendar.set(Calendar.MILLISECOND, 0);
//        
//        Assert.assertEquals(calendar.getTimeInMillis(), values.get(1).getDate().getTime());
//        Assert.assertEquals(Charset.forName("iso-8859-1").name().toLowerCase(), values.get(1).getCharset().name().toLowerCase());
//        Assert.assertEquals(MessageDigest.getInstance("sha-512").getAlgorithm().toLowerCase(), values.get(1).getMessageDigest().getAlgorithm().toLowerCase());
//        
//        
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2017);
//        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
//        calendar.set(Calendar.DAY_OF_MONTH, 2);
//        calendar.set(Calendar.HOUR_OF_DAY, 10);
//        calendar.set(Calendar.MINUTE, 36);
//        calendar.set(Calendar.SECOND, 45);
//        calendar.set(Calendar.MILLISECOND, 150);
//        int tzOffset = calendar.getTimeZone().getOffset(calendar.getTimeInMillis()); 
//        
//        Assert.assertEquals(calendar.getTimeInMillis() + tzOffset, values.get(2).getDate().getTime());
//        Assert.assertEquals(Charset.forName("us-ascii").name().toLowerCase(), values.get(2).getCharset().name().toLowerCase());
//        Assert.assertEquals(MessageDigest.getInstance("sha").getAlgorithm().toLowerCase(), values.get(2).getMessageDigest().getAlgorithm().toLowerCase());
//        
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2018);
//        calendar.set(Calendar.MONTH, Calendar.MARCH);
//        calendar.set(Calendar.DAY_OF_MONTH, 13);
//        calendar.set(Calendar.HOUR_OF_DAY, 13);
//        calendar.set(Calendar.MINUTE, 9);
//        calendar.set(Calendar.SECOND, 23);
//        calendar.set(Calendar.MILLISECOND, 945);
//        
//        Assert.assertEquals(calendar.getTimeInMillis(), values.get(3).getDate().getTime());
//        Assert.assertEquals(Charset.forName("utf-8").name().toLowerCase(), values.get(3).getCharset().name().toLowerCase());
//        Assert.assertEquals(MessageDigest.getInstance("sha-256").getAlgorithm().toLowerCase(), values.get(3).getMessageDigest().getAlgorithm().toLowerCase());
//
//        
//        logger.info("ComplexTypeTest completed successfully");
    }
}

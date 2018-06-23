package net.apercova.quickcsv.reader;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.apercova.io.IterableLineNumberReader;
import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvEntityHelper;
import net.apercova.quickcsv.annotation.CsvDataTypeConverter;
import net.apercova.quickcsv.converter.DataTypeConversionException;
import net.apercova.quickcsv.converter.DataTypeConverter;
import net.apercova.quickcsv.converter.DefaultBooleanConverter;
import net.apercova.quickcsv.converter.DefaultNumberConverter;
import net.apercova.quickcsv.converter.DefaultStringConverter;

public class EntityCsvReader<T> extends AbstractCsvReader<T>{

	public static final Logger logger = Logger.getLogger(EntityCsvReader.class.getName());
	
	protected Class<T> type;
	
	protected EntityCsvReader(Class<T> type) {
		super();
		//Prevents header invalid casting to entity
		this.skipHeader = true;
		this.type = type;
	}
	protected EntityCsvReader(Reader reader, Class<T> type) {
		super(reader);
		//Prevents header invalid casting to entity
		this.skipHeader = true;
		this.type = type;
	}
	protected EntityCsvReader(Reader reader, char delimiter, Class<T> type) {
		super(reader, delimiter);
		//Prevents header invalid casting to entity
		this.skipHeader = true;
		this.type = type;
	}
	protected EntityCsvReader(Reader reader, char delimiter, char quote, Class<T> type) {
		super(reader, delimiter, quote);
		//Prevents header invalid casting to entity
		this.skipHeader = true;
		this.type = type;
	}
	
	public static <T> EntityCsvReader<T> newInstance(Class<T> type) {
		return new EntityCsvReader<T>(type);
	}
	public static  <T> EntityCsvReader<T> newInstance(Reader reader, Class<T> type) {
		return new EntityCsvReader<T>(reader, type);
	}
	public static  <T> EntityCsvReader<T> newInstance(Reader reader, char delimiter, Class<T> type) {
		return new EntityCsvReader<T>(reader, delimiter, type);
	}
	public static  <T> EntityCsvReader<T> newInstance(Reader reader, char delimiter, char quote, Class<T> type) {
		return new EntityCsvReader<T>(reader, delimiter, quote, type);
	}
	
	public T next() {	
		try {
			return readEntity(readLine(((IterableLineNumberReader) reader).next(), delimiter, quote), type);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Can not read entity", e);
			throw new IllegalStateException(e);
		}
	}
	
    public static <T> List<T> read(Reader reader, Class<T> type) {
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, 0, 0, type);
    }

    public static <T> List<T> read(Reader reader, long fromLine, Class<T> type) {
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, fromLine, 0, type);
    }
    
    public static <T> List<T> read(Reader reader, long fromLine, long maxLines, Class<T> type) {
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, fromLine, maxLines, type);
    }
        
    public static <T> List<T> read(Reader reader, boolean skipHeader, Class<T> type) {
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, skipHeader, 0, 0, type);
    }
    
    public static <T> List<T> read(Reader reader, boolean skipHeader, long fromLine, Class<T> type) {
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, 0, type);
    }
		
	public static <T> List<T> read(Reader reader, boolean skipHeader, long fromLine, long maxLines, Class<T> type) {
		return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, maxLines, type);
	}
	
    public static <T> List<T> read(Reader reader, char delimiter, Class<T> type) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, true, 0, 0, type);
    }

    public static <T> List<T> read(Reader reader, char delimiter, long fromLine, Class<T> type) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, true, fromLine, 0, type);
    }
    
    public static <T> List<T> read(Reader reader, char delimiter, long fromLine, long maxLines, Class<T> type) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, true, fromLine, maxLines, type);
    }
        
    public static <T> List<T> read(Reader reader, char delimiter, boolean skipHeader, Class<T> type) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, skipHeader, 0, 0, type);
    }
    
    public static <T> List<T> read(Reader reader, char delimiter, boolean skipHeader, long fromLine, Class<T> type) {
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, 0, type);
    }
		
	public static <T> List<T> read(Reader reader, char delimiter, boolean skipHeader, long fromLine, long maxLines, Class<T> type) {
		return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, skipHeader, fromLine, maxLines, type);
	}
    
    public static <T> List<T> read(Reader reader, char delimiter, char quote, Class<T> type) {
    	return read(reader,delimiter, quote, true, 0, 0, type);
    }

    public static <T> List<T> read(Reader reader, char delimiter, char quote, long fromLine, Class<T> type) {
    	return read(reader, delimiter, quote, true, fromLine, 0, type);
    }
    
    public static <T> List<T> read(Reader reader, char delimiter, char quote, long fromLine, long maxLines, Class<T> type) {
    	return read(reader, delimiter, quote, true, fromLine, maxLines, type);
    }
        
    public static <T> List<T> read(Reader reader, char delimiter, char quote, boolean skipHeader, Class<T> type) {
    	return read(reader, delimiter, quote, skipHeader, 0, 0, type);
    }
    
    public static <T> List<T> read(Reader reader, char delimiter, char quote, boolean skipHeader, long fromLine, Class<T> type) {
    	return read(reader, delimiter, quote, skipHeader, fromLine, 0, type);
    }
    
    public static <T> List<T> read(Reader reader, char delimiter, char quote, boolean skipHeader, long fromLine, long maxLines, Class<T> type) {
    	List<T>  lines = new LinkedList<T>();
    	EntityCsvReader<T> csvReader = null;
    	try {
    		csvReader = EntityCsvReader.newInstance(type);
    		csvReader.setReader(reader)
    		.setDelimiter(delimiter)
    		.setQuote(quote)
    		.skipHeader(skipHeader)
    		.fromLine(fromLine)
    		.maxLines(maxLines);
    		
    		while(csvReader.hasNext()) {
    			lines.add(csvReader.next());
    		}
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		} finally {
			try {
				if(csvReader != null) 
					csvReader.close();
			} catch (IOException e2) {
				logger.log(Level.WARNING, e2.getMessage(), e2);
			}
		}
		return lines;
	}
    public static <T> T readLine(String line, Class<T> type) throws CsvReaderException {
    	return readLine(line, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, type);
    }
    public static <T> T readLine(String line, char delimiter, Class<T> type) throws CsvReaderException {
    	return readLine(line, delimiter, CsvCons.DOUBLE_QUOTE, type);
    }
	public static <T> T readLine(String line, char delimiter, char quote, Class<T> type) throws CsvReaderException {
		try {
			return readEntity(readLine(line, delimiter, quote), type);
		} catch (Exception e) {//InstantiationException, IllegalAccessException, DataTypeConversionException
			throw new CsvReaderException(e);
		}
	}
	
	protected static <T> T readEntity(List<String> values, Class<T> type) 
			throws InstantiationException, IllegalAccessException, DataTypeConversionException {
		T entity = type.newInstance();
		Map<Field, Integer> fieldMap = CsvEntityHelper.findAnnotatedFields(type);
		
		for(Field field :fieldMap.keySet()) {
			if(!field.isAnnotationPresent(CsvDataTypeConverter.class)) {
				setDefaultFieldValue(field, values.get(fieldMap.get(field)), entity);
			}else {
				setCustomFieldValue(field, values.get(fieldMap.get(field)), entity);
			}
		}
		return entity;
	}
	
	protected static <T> void setCustomFieldValue(Field field, String value, T entity) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, DataTypeConversionException {
		field.setAccessible(true);
		CsvDataTypeConverter converterTag = field.getAnnotation(CsvDataTypeConverter.class);
		final Class<? extends DataTypeConverter<?>> converterClass = converterTag.value();
		DataTypeConverter<?> converter = converterClass.newInstance();
		field.set(entity, converter.parse(value) );		
	}
	
	protected static <T> void setDefaultFieldValue(Field field, String value, T entity) 
			throws IllegalAccessException, DataTypeConversionException {
		field.setAccessible(true);
		Class<?> type = field.getType();
		
		try {
			
			DataTypeConverter<?> vc = null;
			
			if(String.class.equals(type)) {
				vc = new DefaultStringConverter();
				field.set(entity, ((DefaultStringConverter)vc).parse(value) );
			}
			
			if(Boolean.class.equals(type) || boolean.class.equals(type)) {
				vc = new DefaultBooleanConverter();
				field.set(entity, ((DefaultBooleanConverter)vc).parse(value) );
			}
			
			if(Number.class.isAssignableFrom(type) ||
					byte.class.equals(type) ||
					short.class.equals(type) ||
					int.class.equals(type) ||
					long.class.equals(type) ||
					float.class.equals(type) ||
					double.class.equals(type)
					) {				
				try {
					vc = new DefaultNumberConverter();
					Number raw = ((DefaultNumberConverter)vc).parse(value);
					if(Byte.class.equals(type) || byte.class.equals(type)) {
						field.set(entity, Byte.valueOf(String.valueOf(raw)));
					}
					if(Short.class.equals(type) || short.class.equals(type)) {
						field.set(entity, Short.valueOf(String.valueOf(raw)));
					}
					if(Integer.class.equals(type) || int.class.equals(type)) {
						field.set(entity, Integer.valueOf(String.valueOf(raw)));
					}
					if(Long.class.equals(type) || long.class.equals(type)) {
						field.set(entity, Long.valueOf(String.valueOf(raw)));
					}
					if(Float.class.equals(type) || float.class.equals(type)) {
						field.set(entity, Float.valueOf(String.valueOf(raw)));
					}
					if(Double.class.equals(type) || double.class.equals(type)) {
						field.set(entity, Double.valueOf(String.valueOf(raw)));
					}
					if(BigInteger.class.equals(type)) {
						field.set(entity, new BigInteger(String.valueOf(raw)));
					}
					if(BigDecimal.class.equals(type)) {
						field.set(entity, new BigDecimal(String.valueOf(raw)));
					}
				} catch (NumberFormatException e) {
					throw new DataTypeConversionException(String.format("Not a valid number for type %s : %s", type, value), e);
				}
			}
			
		} catch (IndexOutOfBoundsException e) {
			field.set(entity, null);
		}
	}

}

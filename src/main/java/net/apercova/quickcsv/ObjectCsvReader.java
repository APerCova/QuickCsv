package net.apercova.quickcsv;

import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.apercova.io.IterableLineNumberReader;
import net.apercova.quickcsv.annotation.CsvDatatypeConverter;
import net.apercova.quickcsv.converter.DatatypeConversionException;
import net.apercova.quickcsv.converter.DatatypeConverter;
import net.apercova.quickcsv.converter.SimpleBooleanConverter;
import net.apercova.quickcsv.converter.SimpleNumberConverter;
import net.apercova.quickcsv.converter.SimpleStringConverter;

public class ObjectCsvReader<T> extends AbstractCsvReader<T>{

	public static final Logger logger = Logger.getLogger(ObjectCsvReader.class.getName());
	
	protected Class<T> type;
	
	protected ObjectCsvReader(Class<T> type) {
		super();
		//Prevents header invalid casting to entity
		this.escapeHeader = true;
		this.type = type;
	}
	
	protected ObjectCsvReader(Class<T> type, Reader reader) {
		super(reader);
		//Prevents header invalid casting to entity
		this.escapeHeader = true;
		this.type = type;
	}

	public int getLineNumber() {
		return ((IterableLineNumberReader) reader).getLineNumber();
	}

	public List<T> read() throws CsvReaderException {
		return ObjectCsvReader.read(type, reader, delimiter, quote, escapeHeader, fromLine, maxLines);
	}

	public boolean hasNext() {
		return ((IterableLineNumberReader) reader).hasNext();
	}

	public T next() {
		try {
			return ObjectCsvReader.readObject(
					type,
					SimpleCsvReader.readLine(((IterableLineNumberReader) reader).next(), delimiter, quote));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error reading next entity", e);
			return null;
		}
		
	}

	public Iterator<T> iterator() {
		return this;
	}
	
	public static <E> ObjectCsvReader<E> getInstance(Class<E> type){
		return new ObjectCsvReader<E>(type);
	}
	
	public static <E> ObjectCsvReader<E> getInstance(Class<E> type, Reader reader){
		return new ObjectCsvReader<E>(type, reader);
	}
	
 	public static <E> List<E> read(Class<E> type, Reader reader) 
    		throws CsvReaderException{
    	return read(type, reader, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, 0, 0);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, boolean escapeHeader) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, 0, 0);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, boolean escapeHeader, long fromLine) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, 0);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, boolean escapeHeader, long fromLine, long maxLines) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, maxLines);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, CsvCons.DOUBLE_QUOTE, true, 0, 0);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, char quote, long fromLine) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, quote, true, fromLine, 0);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, char quote, long fromLine, long maxLines) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, quote, true, fromLine, maxLines);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, char quote) 
    		throws CsvReaderException{
    	return read(type, reader,delimiter, quote, true, 0, 0);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, char quote, boolean escapeHeader) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, quote, escapeHeader, 0, 0);
    }
    
    public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine) 
    		throws CsvReaderException{
    	return read(type, reader, delimiter, quote, escapeHeader, fromLine, 0);
    }
		
	public static <E> List<E> read(Class<E> type, Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine, long maxLines) 
			throws CsvReaderException{
		try {
			List<E> entities = new LinkedList<E>();
			List<List<String>> lines = SimpleCsvReader.read(reader, delimiter, quote, escapeHeader, fromLine, maxLines);
						
			for(int cLine = 0; cLine < lines.size(); cLine++) {
				List<String> line = lines.get(cLine);
				entities.add(readObject(type, line));
			}
			
			return entities;	
		} catch (Exception e) {
			//InstantiationException, IllegalAccessException, DatatypeConversionException
			throw new CsvReaderException(e);
		}
	}
	
	public static <E> E readLine(Class<E> type, String line, char delimiter, char quote) 
			throws InstantiationException, IllegalAccessException, DatatypeConversionException{
		return readObject(type, SimpleCsvReader.readLine(line, delimiter, quote) );
	}
	
	protected static <E> E readObject(Class<E> type, List<String> values) 
			throws InstantiationException, IllegalAccessException, DatatypeConversionException{
		E entity = type.newInstance();
		Map<Integer,Field> fieldMap = ObjectCsvHelper.getAnnotatedFields(type);
		for(Integer k :fieldMap.keySet()) {
			Field field = fieldMap.get(k);
			if(!field.isAnnotationPresent(CsvDatatypeConverter.class)) {
				setDefaultFieldValue(entity, field, values.get(k));
			}else {
				setCustomFieldValue(entity, field, values.get(k));
			}
		}
		return entity;
	}
	
	protected static <E> void setCustomFieldValue(E entity, Field field, String value) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, DatatypeConversionException {
		field.setAccessible(true);
		CsvDatatypeConverter converterTag = field.getAnnotation(CsvDatatypeConverter.class);
		final Class<? extends DatatypeConverter<?>> converterClass = converterTag.value();
		DatatypeConverter<?> converter = converterClass.newInstance();
		field.set(entity, converter.parse(value) );		
	}
	
	protected static <E> void setDefaultFieldValue(E entity, Field field, String value) 
			throws IllegalAccessException, DatatypeConversionException {
		field.setAccessible(true);
		Class<?> type = field.getType();
		
		try {
			
			DatatypeConverter<?> vc = null;
			
			if(String.class.equals(type)) {
				vc = new SimpleStringConverter();
				field.set(entity, ((SimpleStringConverter)vc).parse(value) );
			}
			
			if(Boolean.class.equals(type) || boolean.class.equals(type)) {
				vc = new SimpleBooleanConverter();
				field.set(entity, ((SimpleBooleanConverter)vc).parse(value) );
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
					vc = new SimpleNumberConverter();
					Number raw = ((SimpleNumberConverter)vc).parse(value);
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
					throw new DatatypeConversionException(String.format("Not a valid number for type %s : %s", type, value), e);
				}
			}
			
		} catch (IndexOutOfBoundsException e) {
			field.set(entity, null);
		}
	}

}

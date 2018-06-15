package net.apercova.quickcsv.reader;

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
import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.EntityCsvHelper;
import net.apercova.quickcsv.annotation.CsvDatatypeConverter;
import net.apercova.quickcsv.converter.DatatypeConversionException;
import net.apercova.quickcsv.converter.DatatypeConverter;
import net.apercova.quickcsv.converter.SimpleBooleanConverter;
import net.apercova.quickcsv.converter.SimpleNumberConverter;
import net.apercova.quickcsv.converter.SimpleStringConverter;

public class EntityCsvReader<T> extends AbstractCsvReader<T>{

	public static final Logger logger = Logger.getLogger(EntityCsvReader.class.getName());
	
	protected Class<T> type;
	
	protected EntityCsvReader(Class<T> type) {
		super();
		//Prevents header invalid casting to entity
		this.escapeHeader = true;
		this.type = type;
	}
	
	protected EntityCsvReader(Reader reader, Class<T> type) {
		super(reader);
		//Prevents header invalid casting to entity
		this.escapeHeader = true;
		this.type = type;
	}

	public List<T> read() throws CsvReaderException {
		return EntityCsvReader.read(reader, delimiter, quote, escapeHeader, fromLine, maxLines, type);
	}

	public boolean hasNext() {
		return ((IterableLineNumberReader) reader).hasNext();
	}

	public T next() {
		try {
			return EntityCsvReader.readObject(
					type,
					readLine(((IterableLineNumberReader) reader).next(), delimiter, quote));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error reading next entity", e);
			return null;
		}
		
	}

	public Iterator<T> iterator() {
		return this;
	}
	
	public static <E> EntityCsvReader<E> newInstance(Class<E> type){
		return new EntityCsvReader<E>(type);
	}
	
	public static <E> EntityCsvReader<E> newInstance(Reader reader, Class<E> type){
		return new EntityCsvReader<E>(reader, type);
	}
	
    public static <E> List<E> read(Reader reader, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, 0, 0, type);
    }

    public static <E> List<E> read(Reader reader, long fromLine, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, fromLine, 0, type);
    }
    
    public static <E> List<E> read(Reader reader, long fromLine, long maxLines, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, true, fromLine, maxLines, type);
    }
        
    public static <E> List<E> read(Reader reader, boolean escapeHeader, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader, 0, 0, type);
    }
    
    public static <E> List<E> read(Reader reader, boolean escapeHeader, long fromLine, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, 0, type);
    }
		
	public static <E> List<E> read(Reader reader, boolean escapeHeader, long fromLine, long maxLines, Class<E> type) 
			throws CsvReaderException{
		return read(reader,CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, maxLines, type);
	}
	
    public static <E> List<E> read(Reader reader, char delimiter, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, true, 0, 0, type);
    }

    public static <E> List<E> read(Reader reader, char delimiter, long fromLine, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, true, fromLine, 0, type);
    }
    
    public static <E> List<E> read(Reader reader, char delimiter, long fromLine, long maxLines, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, true, fromLine, maxLines, type);
    }
        
    public static <E> List<E> read(Reader reader, char delimiter, boolean escapeHeader, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, 0, 0, type);
    }
    
    public static <E> List<E> read(Reader reader, char delimiter, boolean escapeHeader, long fromLine, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, 0, type);
    }
		
	public static <E> List<E> read(Reader reader, char delimiter, boolean escapeHeader, long fromLine, long maxLines, Class<E> type) 
			throws CsvReaderException{
		return read(reader, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader, fromLine, maxLines, type);
	}
    
    public static <E> List<E> read(Reader reader, char delimiter, char quote, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader,delimiter, quote, true, 0, 0, type);
    }

    public static <E> List<E> read(Reader reader, char delimiter, char quote, long fromLine, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, true, fromLine, 0, type);
    }
    
    public static <E> List<E> read(Reader reader, char delimiter, char quote, long fromLine, long maxLines, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, true, fromLine, maxLines, type);
    }
        
    public static <E> List<E> read(Reader reader, char delimiter, char quote, boolean escapeHeader, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, escapeHeader, 0, 0, type);
    }
    
    public static <E> List<E> read(Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine, Class<E> type) 
    		throws CsvReaderException{
    	return read(reader, delimiter, quote, escapeHeader, fromLine, 0, type);
    }
		
	public static <E> List<E> read(Reader reader, char delimiter, char quote, boolean escapeHeader, long fromLine, long maxLines, Class<E> type) 
			throws CsvReaderException{
		try {
			List<E> entities = new LinkedList<E>();
			List<List<String>> lines = read(reader, delimiter, quote, escapeHeader, fromLine, maxLines);
						
			for(int cLine = 0; cLine < lines.size(); cLine++) {
				List<String> line = lines.get(cLine);
				entities.add(readObject(type, line));
			}
			
			return entities;	
		} catch (Exception e) {
			throw new CsvReaderException(e);
		}
	}
	
	public static <E> E readLine(Class<E> type, String line, char delimiter, char quote) 
			throws InstantiationException, IllegalAccessException, DatatypeConversionException{
		return readObject(type, readLine(line, delimiter, quote) );
	}
	
	protected static <E> E readObject(Class<E> type, List<String> values) 
			throws InstantiationException, IllegalAccessException, DatatypeConversionException{
		E entity = type.newInstance();
		Map<Integer,Field> fieldMap = EntityCsvHelper.getAnnotatedFields(type);
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

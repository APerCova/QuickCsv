package net.apercova.quickcsv.writer;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.apercova.quickcsv.CsvCons;
import net.apercova.quickcsv.CsvEntityHelper;
import net.apercova.quickcsv.annotation.CsvDataTypeConverter;
import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.converter.DataTypeConversionException;
import net.apercova.quickcsv.converter.DataTypeConverter;

public class EntityCsvWriter<E> extends AbstractCsvWriter<E>{

	protected Class<E> type;
	
	protected EntityCsvWriter() {
	}
	protected EntityCsvWriter(Class<E> type) {
		this.type = type;
	}
	protected EntityCsvWriter(Writer writer, Class<E> type) {
		super(writer);
		this.type = type;
	}
	protected EntityCsvWriter(Writer writer, Collection<E> lines, Class<E> type) {
		super(writer, lines);
		this.type = type;
	}
	
	public static <E> EntityCsvWriter<E> newInstance(Class<E> type) {
		return new EntityCsvWriter<E>(type);
	}
	public static <E> EntityCsvWriter<E> newInstance(Writer writer, Class<E> type) {
		return new EntityCsvWriter<E>(writer, type);
	}
	public static <E> EntityCsvWriter<E> newInstance(Writer writer, Collection<E> lines, Class<E> type) {
		return new EntityCsvWriter<E>(writer, lines, type);
	}
	
	public void write() throws CsvWriterException {
		EntityCsvWriter.write(writer, lines, delimiter, quote, skipHeader, type);
	}
	
	public static <E> void write(Writer writer, Collection<E> lines, Class<E> type) 
    		throws CsvWriterException {
    	write(writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false, type);
    }
    public static <E> void write(Writer writer, Collection<E> lines, boolean skipHeader, Class<E> type) 
    		throws CsvWriterException {
    	write(writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, skipHeader, type);
    }
    public static <E> void write(Writer writer, Collection<E> lines, char delimiter, Class<E> type) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, false, type);
    }
    public static <E> void write(Writer writer, Collection<E> lines, char delimiter, boolean skipHeader, Class<E> type) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, skipHeader, type);
    }
    public static <E> void write(Writer writer, Collection<E> lines, char delimiter, char quote, Class<E> type) 
    		throws CsvWriterException {
    	write(writer, lines, delimiter, quote, false, type);
    	
    }
	public static <E> void write(Writer writer, Collection<E> lines, char delimiter, char quote, boolean skipHeader, Class<E> type)
            throws CsvWriterException{
		try {
			write(writer, readCollectionValues(lines, type), delimiter, quote, skipHeader);
		} catch (Exception e) {
			throw new CsvWriterException(e);
		}
	}
	
	public static <E> void writeLine(Writer writer, E line, char delimiter, char quote, Class<E> type) 
			throws CsvWriterException {
		try {
			writeLine(writer, readObjectValues(line, CsvEntityHelper.findAnnotatedFields(type)), delimiter, quote);
		} catch (Exception e) {
			throw new CsvWriterException(e.getMessage(), e);
		}
	}	
	protected static <E> Collection<Collection<String>> readCollectionValues(Collection<E> lines, Class<E> type) 
			throws IllegalAccessException, IllegalArgumentException, InstantiationException, DataTypeConversionException {
		Collection<Collection<String>> values = new LinkedList<Collection<String>>();
		
		if(!type.isAnnotationPresent(CsvEntity.class)) {
			throw new IllegalArgumentException(String.format("Not a valid csv entity class: %s%n", type.getName()));
		}
		if(!lines.isEmpty()) {
			
			CsvEntity rowMeta = type.getAnnotation(CsvEntity.class);
			
			//resolve header values
			if(rowMeta.headers().length > 0) {
				values.add(Arrays.asList(rowMeta.headers()));
			}
			
			//Parse fields as CsvCollection
			for(E line: lines) {
				values.add(readObjectValues(line, CsvEntityHelper.findAnnotatedFields(type) ));
			}
		}
		
		return values;
	}	
	@SuppressWarnings("unchecked")
	protected static <E> Collection<String> readObjectValues(E obj, Map<Field, Integer> fieldMap) 
			throws IllegalArgumentException, IllegalAccessException, InstantiationException, DataTypeConversionException {
		
		//reversing map to write index based columns
		Map<Integer, Field> indexMap = new TreeMap<Integer, Field>();
		for(Map.Entry<Field, Integer> entry : fieldMap.entrySet()){
			indexMap.put(entry.getValue(), entry.getKey());
		}
		
		Collection<String> values = new LinkedList<String>();
		int i = 0;
		for(Entry<Integer, Field> entry: indexMap.entrySet()) {
			Field field = entry.getValue();
			field.setAccessible(true);
			while(!(entry.getKey().equals(i))) {
				values.add("");
				i++;
			}
			
			Object value = field.get(obj);
			String sValue = String.valueOf(value);
			
			if(field.isAnnotationPresent(CsvDataTypeConverter.class)) {
				CsvDataTypeConverter converterTag = field.getAnnotation(CsvDataTypeConverter.class);
				Class<? extends DataTypeConverter<?>> converterClazz = converterTag.value();
				DataTypeConverter<?> converter = converterClazz.newInstance();
				sValue = ((DataTypeConverter<Object>) converter).format(value);
			}
			
			values.add( ((value != null && sValue.length() > 0)?sValue:"")  );
			i++;
		}
		
		return values;
	}

}
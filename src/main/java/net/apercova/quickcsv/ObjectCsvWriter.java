package net.apercova.quickcsv;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.apercova.quickcsv.annotation.CsvEntity;

public class ObjectCsvWriter<E> extends AbstractCsvWriter<E>{

	protected Class<E> type;
	
	public ObjectCsvWriter(Class<E> type) {
		super();
		this.type = type;
	}
	
	public ObjectCsvWriter(Writer writer, Class<E> type) {
		super(writer);
		this.type = type;
	}

	public ObjectCsvWriter(Writer writer, Collection<E> lines, Class<E> type) {
		super(writer, lines);
		this.type = type;
	}

	public void write() throws CsvWriterException {
	}
	public static <E> void write(Writer writer, Collection<E> lines, char delimiter, char quote, boolean writeHeader, Class<E> type)
            throws CsvWriterException{
		try {
			SimpleCsvWriter.write(writer, readCollectionValues(lines, type, writeHeader), delimiter, quote, writeHeader);
		} catch (Exception e) {
			throw new CsvWriterException(e.getMessage(), e);
		}
	}
	
	public static <E> void writeLine(Writer writer, E line, char delimiter, char quote, Class<E> type) throws CsvWriterException {
		try {
			SimpleCsvWriter.writeLine(writer, readObjectValues(line, ObjectCsvHelper.getAnnotatedFields(type)), delimiter, quote);
		} catch (Exception e) {
			throw new CsvWriterException(e.getMessage(), e);
		}
		
	}
	
	protected static <E> List<List<String>> readCollectionValues(Collection<E> lines, Class<E> type, boolean writeHeader) 
			throws IllegalAccessException {
		List<List<String>> values = new LinkedList<List<String>>();
		
		if(!type.isAnnotationPresent(CsvEntity.class)) {
			throw new IllegalArgumentException(String.format("Not a valid csv entity class: %s%n", type.getName()));
		}
		if(!lines.isEmpty()) {
			
			CsvEntity rowMeta = type.getAnnotation(CsvEntity.class);
			
			//resolve header values
			if(rowMeta.headers().length > 0 && writeHeader) {
				values.add(Arrays.asList(rowMeta.headers()));
			}
			
			//Parse fields as CsvCollection
			for(E line: lines) {
				values.add(readObjectValues(line, ObjectCsvHelper.getAnnotatedFields(type) ));
			}
		}
		
		return values;
	}
		
	protected static <E> List<String> readObjectValues(E obj, Map<Integer, Field> fieldMap) 
			throws IllegalArgumentException, IllegalAccessException{
		List<String> values = new LinkedList<String>();
		int i = 0;
		for(Integer k: fieldMap.keySet()) {
			Field field_ = fieldMap.get(k);
			field_.setAccessible(true);
			
			while(!k.equals(i)) {
				values.add("");
				i++;
			}
			
			Object value = field_.get(obj);
			String sValue = String.valueOf(value);
			if(value != null && sValue.length() > 0) {
				values.add(sValue);
			}else {
				values.add("");
			}
			i++;
		}
		return values;
	}
	

}

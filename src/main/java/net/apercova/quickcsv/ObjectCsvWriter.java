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
	
	protected ObjectCsvWriter() {
	}
	
	protected ObjectCsvWriter(Class<E> type) {
		this.type = type;
	}
	
	protected ObjectCsvWriter(Class<E> type, Writer writer) {
		super(writer);
		this.type = type;
	}
	
	protected ObjectCsvWriter(Class<E> type, Writer writer, Collection<E> lines) {
		super(writer, lines);
		this.type = type;
	}
	
	public void write() throws CsvWriterException {
		ObjectCsvWriter.write(type, writer, lines, delimiter, quote, escapeHeader);
	}
	
	public static <E> ObjectCsvWriter<E> getInstance(Class<E> type) {
		return new ObjectCsvWriter<E>(type);
	}
	
	public static <E> ObjectCsvWriter<E> getInstance(Class<E> type, Writer writer) {
		return new ObjectCsvWriter<E>(type, writer);
	}

	public static <E> ObjectCsvWriter<E> getInstance(Class<E> type, Writer writer, Collection<E> lines) {
		return new ObjectCsvWriter<E>(type, writer, lines);
	}

	public static <E> void write(Class<E> type, Writer writer, Collection<E> lines) 
    		throws CsvWriterException {
    	write(type, writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, false);
    }
    
    public static <E> void write(Class<E> type, Writer writer, Collection<E> lines, boolean escapeHeader) 
    		throws CsvWriterException {
    	write(type, writer, lines, CsvCons.COMMA, CsvCons.DOUBLE_QUOTE, escapeHeader);
    }
    
    public static <E> void write(Class<E> type, Writer writer, Collection<E> lines, char delimiter) 
    		throws CsvWriterException {
    	write(type, writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, false);
    }
    
    public static <E> void write(Class<E> type, Writer writer, Collection<E> lines, char delimiter, boolean escapeHeader) 
    		throws CsvWriterException {
    	write(type, writer, lines, delimiter, CsvCons.DOUBLE_QUOTE, escapeHeader);
    }
    
    public static <E> void write(Class<E> type, Writer writer, Collection<E> lines, char delimiter, char quote) 
    		throws CsvWriterException {
    	write(type, writer, lines, delimiter, quote, false);
    	
    }
	
	public static <E> void write(Class<E> type, Writer writer, Collection<E> lines, char delimiter, char quote, boolean escapeHeader)
            throws CsvWriterException{
		try {
			SimpleCsvWriter.write(writer, readCollectionValues(lines, type), delimiter, quote, escapeHeader);
		} catch (Exception e) {
			throw new CsvWriterException(e);
		}
	}
	
	public static <E> void writeLine(Class<E> type, Writer writer, E line, char delimiter, char quote) 
			throws CsvWriterException {
		try {
			SimpleCsvWriter.writeLine(writer, readObjectValues(line, ObjectCsvHelper.getAnnotatedFields(type)), delimiter, quote);
		} catch (Exception e) {
			throw new CsvWriterException(e.getMessage(), e);
		}
	}
	
	protected static <E> List<List<String>> readCollectionValues(Collection<E> lines, Class<E> type) 
			throws IllegalAccessException {
		List<List<String>> values = new LinkedList<List<String>>();
		
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
				values.add(readObjectValues(line, ObjectCsvHelper.getAnnotatedFields(type) ));
			}
		}
		
		return values;
	}
		
	protected static <E> List<String> readObjectValues(E obj, Map<Integer, Field> fieldMap) 
			throws IllegalArgumentException, IllegalAccessException {
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

package net.apercova.quickcsv;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.apercova.quickcsv.annotation.CsvDatatypeConverter;
import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.converter.DatatypeConversionException;
import net.apercova.quickcsv.converter.DatatypeConverter;

public class EntityCsvWriter<E> extends AbstractCsvWriter<E>{

	protected Class<E> type;
	
	protected EntityCsvWriter() {
	}
	
	protected EntityCsvWriter(Class<E> type) {
		this.type = type;
	}
	
	protected EntityCsvWriter(Class<E> type, Writer writer) {
		super(writer);
		this.type = type;
	}
	
	protected EntityCsvWriter(Class<E> type, Writer writer, Collection<E> lines) {
		super(writer, lines);
		this.type = type;
	}
	
	public void write() throws CsvWriterException {
		EntityCsvWriter.write(type, writer, lines, delimiter, quote, escapeHeader);
	}
	
	public static <E> EntityCsvWriter<E> getInstance(Class<E> type) {
		return new EntityCsvWriter<E>(type);
	}
	
	public static <E> EntityCsvWriter<E> getInstance(Class<E> type, Writer writer) {
		return new EntityCsvWriter<E>(type, writer);
	}

	public static <E> EntityCsvWriter<E> getInstance(Class<E> type, Writer writer, Collection<E> lines) {
		return new EntityCsvWriter<E>(type, writer, lines);
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
			DefaultCsvWriter.write(writer, readCollectionValues(lines, type), delimiter, quote, escapeHeader);
		} catch (Exception e) {
			throw new CsvWriterException(e);
		}
	}
	
	public static <E> void writeLine(Class<E> type, Writer writer, E line, char delimiter, char quote) 
			throws CsvWriterException {
		try {
			DefaultCsvWriter.writeLine(writer, readObjectValues(line, EntityCsvHelper.getAnnotatedFields(type)), delimiter, quote);
		} catch (Exception e) {
			throw new CsvWriterException(e.getMessage(), e);
		}
	}
	
	protected static <E> List<List<String>> readCollectionValues(Collection<E> lines, Class<E> type) 
			throws IllegalAccessException, IllegalArgumentException, InstantiationException, DatatypeConversionException {
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
				values.add(readObjectValues(line, EntityCsvHelper.getAnnotatedFields(type) ));
			}
		}
		
		return values;
	}
		
	@SuppressWarnings("unchecked")
	protected static <E> List<String> readObjectValues(E obj, Map<Integer, Field> fieldMap) 
			throws IllegalArgumentException, IllegalAccessException, InstantiationException, DatatypeConversionException {
		List<String> values = new LinkedList<String>();
		int i = 0;
		for(Entry<Integer, Field> entry: fieldMap.entrySet()) {
			Field field = entry.getValue();
			field.setAccessible(true);
			while(!(entry.getKey().equals(i))) {
				values.add("");
				i++;
			}
			
			Object value = field.get(obj);
			String sValue = String.valueOf(value);
			
			if(field.isAnnotationPresent(CsvDatatypeConverter.class)) {
				CsvDatatypeConverter converterTag = field.getAnnotation(CsvDatatypeConverter.class);
				Class<? extends DatatypeConverter<?>> converterClazz = converterTag.value();
				DatatypeConverter<?> converter = converterClazz.newInstance();
				sValue = ((DatatypeConverter<Object>) converter).format(value);
			}
			
			values.add( ((value != null && sValue.length() > 0)?sValue:"")  );
			i++;
		}
		
		return values;
	}

}

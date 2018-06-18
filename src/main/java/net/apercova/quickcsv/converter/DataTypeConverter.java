package net.apercova.quickcsv.converter;

public interface DataTypeConverter<T> {
	
	T parse(String value) throws DataTypeConversionException;
	
	String format(T value) throws DataTypeConversionException;
	
}

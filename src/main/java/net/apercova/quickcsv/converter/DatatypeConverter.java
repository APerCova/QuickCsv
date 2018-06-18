package net.apercova.quickcsv.converter;

public interface DatatypeConverter<T> {
	
	T parse(String value) throws DatatypeConversionException;
	
	String format(T value) throws DatatypeConversionException;
	
}

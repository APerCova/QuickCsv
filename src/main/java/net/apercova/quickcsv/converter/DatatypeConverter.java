package net.apercova.quickcsv.converter;

public interface DatatypeConverter<T> {
	
	public T parse(String value) throws DatatypeConversionException;
	
	public String format(T value) throws DatatypeConversionException;
	
}

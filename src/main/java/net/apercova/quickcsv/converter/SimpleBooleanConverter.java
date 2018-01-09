package net.apercova.quickcsv.converter;

public class SimpleBooleanConverter implements DatatypeConverter<Boolean>{

	public Boolean parse(String value) throws DatatypeConversionException {
		return Boolean.valueOf(value);
	}

	public String format(Boolean value) throws DatatypeConversionException {
		return String.valueOf(Boolean.valueOf(value));
	}

}

package net.apercova.quickcsv.converter;

import java.util.Locale;

public class SimpleStringConverter implements DatatypeConverter<String>{

	public String parse(String value) throws DatatypeConversionException {
		return value;
	}

	public String format(String value) throws DatatypeConversionException {
		return String.format(Locale.getDefault(), value);
	}

}

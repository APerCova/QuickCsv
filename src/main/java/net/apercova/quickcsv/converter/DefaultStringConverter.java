package net.apercova.quickcsv.converter;

import java.util.Locale;

public class DefaultStringConverter implements DataTypeConverter<String> {

	public String parse(String value) throws DataTypeConversionException {
		return value;
	}

	public String format(String value) throws DataTypeConversionException {
		return String.format(Locale.getDefault(), value);
	}

}
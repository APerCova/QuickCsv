package net.apercova.quickcsv.converter;

import java.nio.charset.Charset;

public class CharsetConverter implements DataTypeConverter<Charset> {

	public Charset parse(String value) throws DataTypeConversionException {
		try {
			return Charset.forName(value);
		} catch (Exception e) {
			throw new DataTypeConversionException("For: "+value, e);
		}
	}

	public String format(Charset value) throws DataTypeConversionException {
		return value!= null? value.name(): "";
	}

}

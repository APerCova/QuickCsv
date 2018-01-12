package net.apercova.quickcsv.converter;

import java.nio.charset.Charset;

public class CharsetConverter implements DatatypeConverter<Charset>{

	public Charset parse(String value) throws DatatypeConversionException {
		try {
			return Charset.forName(value);
		} catch (Exception e) {
			throw new DatatypeConversionException("For: "+value, e);
		}
	}

	public String format(Charset value) throws DatatypeConversionException {
		return value!= null? value.name(): "";
	}

}

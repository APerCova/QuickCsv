package net.apercova.quickcsv.converter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultNumberConverter implements DatatypeConverter<Number>  {

	protected static final String NUM_REGEX = "\\+([\\-]?[\\d]+[.,\\,]?[\\d]+)";

	public String format(Number value) {
		return NumberFormat.getInstance(Locale.getDefault()).format(value);
	}

	public Number parse(String value) throws DatatypeConversionException{
		value = normalize(value);
		try {
			return NumberFormat.getInstance(Locale.getDefault()).parse(value);
		} catch (ParseException e) {
			throw new DatatypeConversionException(String.format("Not a valid number: %s", value), e);
		}
	}
	
	protected String normalize(String value) {
		if(value != null) {
			//normalize
			Pattern p = Pattern.compile(DefaultNumberConverter.NUM_REGEX);
			Matcher m = p.matcher(value);
			if(m.find()) {
				value = m.group(1);
			}
		}
		return value;
	}
	
}

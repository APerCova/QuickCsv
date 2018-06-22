package net.apercova.quickcsv.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleISODateConverter implements DataTypeConverter<Date>{

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
	
	public Date parse(String value) throws DataTypeConversionException {
		try {
			return df.parse(value);
		} catch (ParseException e) {
			throw new DataTypeConversionException(e);
		}
	}

	public String format(Date value) throws DataTypeConversionException {
		return df.format(value);
	}

}

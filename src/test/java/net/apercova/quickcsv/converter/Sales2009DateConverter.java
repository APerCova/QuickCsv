package net.apercova.quickcsv.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sales2009DateConverter implements DataTypeConverter<Date>{

	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
	
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

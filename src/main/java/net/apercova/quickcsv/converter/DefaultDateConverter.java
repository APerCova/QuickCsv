package net.apercova.quickcsv.converter;

import java.util.Calendar;
import java.util.Date;

public class DefaultDateConverter implements DataTypeConverter<Date> {
	
	@SuppressWarnings("restriction")
	public Date parse(String value) throws DataTypeConversionException {
		return javax.xml.bind.DatatypeConverter.parseDateTime(value).getTime();
	}

	@SuppressWarnings("restriction")
	public String format(Date value) throws DataTypeConversionException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(value);
		return javax.xml.bind.DatatypeConverter.printDateTime(cal);
	}
	
}

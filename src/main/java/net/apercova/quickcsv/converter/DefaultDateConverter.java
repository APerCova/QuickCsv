package net.apercova.quickcsv.converter;

import java.util.Calendar;
import java.util.Date;

public class DefaultDateConverter implements DatatypeConverter<Date> {
	
	@SuppressWarnings("restriction")
	public Date parse(String value) throws DatatypeConversionException {
		return javax.xml.bind.DatatypeConverter.parseDateTime(value).getTime();
	}

	@SuppressWarnings("restriction")
	public String format(Date value) throws DatatypeConversionException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(value);
		return javax.xml.bind.DatatypeConverter.printDateTime(cal);
	}
	
}

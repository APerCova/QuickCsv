package net.apercova.quickcsv.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateConverter implements DatatypeConverter<Date> {

	public static final DateFormat ISO_ZULU_DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	public static final DateFormat ISO_COMBINED_DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static final DateFormat ISO_DATE_DF = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat ISO_TIME_DF = new SimpleDateFormat("HH:mm:ss");
	
	public Date parse(String value) throws DatatypeConversionException {
		try {
			return parseISOZulu(value);
		} catch (ParseException e) {
			try {
				return parseISOCombined(value);
			} catch (ParseException e1) {
				try {
					return parseISOTime(value);
				} catch (ParseException e2) {
					try {
						return parseISODate(value);
					} catch (ParseException e3) {
						throw new DatatypeConversionException( String.format("Not a valid date: %s", value) , e);
					}
				}
			}
		}
	}

	public String format(Date value) throws DatatypeConversionException {
		return ISO_ZULU_DF.format(value);
	}

	protected Date parseISOZulu(String value) throws ParseException {
		return ISO_ZULU_DF.parse(value);
	}
	protected Date parseISOCombined(String value) throws ParseException {
		return ISO_COMBINED_DF.parse(value);
	}
	protected Date parseISOTime(String value) throws ParseException {
		return ISO_TIME_DF.parse(value);
	}
	protected Date parseISODate(String value) throws ParseException {
		return ISO_DATE_DF.parse(value);
	}
}

package net.apercova.quickcsv.converter;

public class DefaultBooleanConverter implements DataTypeConverter<Boolean> {

	public Boolean parse(String value) throws DataTypeConversionException {
		return Boolean.valueOf(value);
	}

	public String format(Boolean value) throws DataTypeConversionException {
		return String.valueOf(Boolean.valueOf(value));
	}

}
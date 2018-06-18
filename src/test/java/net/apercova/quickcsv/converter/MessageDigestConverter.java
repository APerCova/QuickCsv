package net.apercova.quickcsv.converter;

import java.security.MessageDigest;

public class MessageDigestConverter implements DataTypeConverter<MessageDigest> {

	public MessageDigest parse(String value) throws DataTypeConversionException {
		try {
			return MessageDigest.getInstance(value);
		} catch (Exception e) {
			throw new DataTypeConversionException("For: "+value, e);
		}
	}

	public String format(MessageDigest value) throws DataTypeConversionException {
		return value != null? value.getAlgorithm(): "";
	}

}

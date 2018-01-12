package net.apercova.quickcsv.converter;

import java.security.MessageDigest;

public class MessageDigestConverter implements DatatypeConverter<MessageDigest> {

	public MessageDigest parse(String value) throws DatatypeConversionException {
		try {
			return MessageDigest.getInstance(value);
		} catch (Exception e) {
			throw new DatatypeConversionException("For: "+value, e);
		}
	}

	public String format(MessageDigest value) throws DatatypeConversionException {
		return value != null? value.getAlgorithm(): "";
	}

}

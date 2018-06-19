package net.apercova.quickcsv.entity;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import net.apercova.quickcsv.annotation.CsvDataTypeConverter;
import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;
import net.apercova.quickcsv.converter.CharsetConverter;
import net.apercova.quickcsv.converter.MessageDigestConverter;

@CsvEntity(headers= {"DATE","CHARSET","MESSAGE_DIGEST"})
public class ComplexType {
	
	@CsvValue(index=0)
	private String date;
	@CsvValue(index=1)
	@CsvDataTypeConverter(CharsetConverter.class)
	private Charset charset;
	@CsvValue(index=2)
	@CsvDataTypeConverter(MessageDigestConverter.class)
	private MessageDigest messageDigest;
	
	public ComplexType() {
	}
	
	public ComplexType(String date, Charset charset, MessageDigest messageDigest) {
		super();
		this.date = date;
		this.charset = charset;
		this.messageDigest = messageDigest;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public MessageDigest getMessageDigest() {
		return messageDigest;
	}

	public void setMessageDigest(MessageDigest messageDigest) {
		this.messageDigest = messageDigest;
	}

	@Override
	public String toString() {
		return "CompositeType [date=" + date + ", charset=" + charset + ", messageDigest=" + messageDigest + "]";
	}
}
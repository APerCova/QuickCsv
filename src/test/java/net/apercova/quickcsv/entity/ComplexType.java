package net.apercova.quickcsv.entity;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Date;

import net.apercova.quickcsv.annotation.CsvDatatypeConverter;
import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;
import net.apercova.quickcsv.converter.CharsetConverter;
import net.apercova.quickcsv.converter.DefaultDateConverter;
import net.apercova.quickcsv.converter.MessageDigestConverter;

@CsvEntity(headers= {"DATE","CHARSET","MESSAGE_DIGEST"})
public class ComplexType {
	
	@CsvValue(index=0)
	@CsvDatatypeConverter(DefaultDateConverter.class)
	private Date date;
	@CsvValue(index=1)
	@CsvDatatypeConverter(CharsetConverter.class)
	private Charset charset;
	@CsvValue(index=2)
	@CsvDatatypeConverter(MessageDigestConverter.class)
	private MessageDigest messageDigest;
	
	public ComplexType() {
	}
	
	public ComplexType(Date date, Charset charset, MessageDigest messageDigest) {
		super();
		this.date = date;
		this.charset = charset;
		this.messageDigest = messageDigest;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
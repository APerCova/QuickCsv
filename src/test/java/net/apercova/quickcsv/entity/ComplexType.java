package net.apercova.quickcsv.entity;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Date;

import net.apercova.quickcsv.annotation.CsvDatatypeConverter;
import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;
import net.apercova.quickcsv.converter.CharsetConverter;
import net.apercova.quickcsv.converter.MessageDigestConverter;
import net.apercova.quickcsv.converter.SimpleDateConverter;

@CsvEntity(headers= {"DATE","CHARSET","MESSAGE_DIGEST"})
public class CompositeType {
	
	@CsvValue(colnum=0)
	@CsvDatatypeConverter(SimpleDateConverter.class)
	private Date date;
	@CsvValue(colnum=1)
	@CsvDatatypeConverter(CharsetConverter.class)
	private Charset charset;
	@CsvValue(colnum=2)
	@CsvDatatypeConverter(MessageDigestConverter.class)
	private MessageDigest messageDigest;
	
	public CompositeType() {
	}
	
	public CompositeType(Date date, Charset charset, MessageDigest messageDigest) {
		super();
		this.date = date;
		this.charset = charset;
		this.messageDigest = messageDigest;
	}

	public Date getDate() {
		return date;
	}
	
	public Charset getCharset() {
		return charset;
	}
	
	public MessageDigest getMessageDigest() {
		return messageDigest;
	}
	
	@Override
	public String toString() {
		return "CompositeType [date=" + date + ", charset=" + charset + ", messageDigest=" + messageDigest + "]";
	}
}

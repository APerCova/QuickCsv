package net.apercova.quickcsv.entity;

import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;

@CsvEntity(headers= {"NAME","CAPITAL","ISO_CODE"})
public class Country {
	
	@CsvValue(header="NAME")
	private String name;
	@CsvValue(header="CAPITAL")
	private String capital;
	@CsvValue(header="ISO_CODE")
	private String isoCode;
	
	public Country() {
		super();
	}
	
	public Country(String name, String capital, String isoCode) {
		super();
		this.name = name;
		this.capital = capital;
		this.isoCode = isoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", capital=" + capital + ", isoCode=" + isoCode + "]";
	}
	
	
}

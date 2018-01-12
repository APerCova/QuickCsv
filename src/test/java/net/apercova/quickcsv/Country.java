package net.apercova.quickcsv;

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

	@Override
	public String toString() {
		return "Country [name=" + name + ", capital=" + capital + ", isoCode=" + isoCode + "]";
	}
	
	
}

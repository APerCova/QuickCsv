package net.apercova.quickcsv.entity;

import java.math.BigDecimal;
import java.util.Date;

import net.apercova.quickcsv.annotation.CsvDataTypeConverter;
import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;
import net.apercova.quickcsv.converter.Sales2009DateConverter;

@CsvEntity(headers= {
		"Transaction_date",
		"Product",
		"Price",
		"Payment_Type",
		"Name",
		"City",
		"State",
		"Country",
		"Continent",
		"Latitude",
		"Longitude"})
public class Sales2009 {
	
	@CsvValue(index=0)
	@CsvDataTypeConverter(Sales2009DateConverter.class)
	private Date transactionDate;
	@CsvValue(index=1)
	private String product;
	@CsvValue(index=2)
	private BigDecimal price;
	@CsvValue(index=3)
	private String paymentType;
	@CsvValue(index=4)
	private String name;
	@CsvValue(index=5)
	private String city;
	@CsvValue(index=6)
	private String state;
	@CsvValue(index=7)
	private String country;
	@CsvValue(index=9)
	private BigDecimal latitude;
	@CsvValue(index=10)
	private BigDecimal longitude;
	
	public Sales2009() {
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sales2009 other = (Sales2009) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sales2009 [transactionDate=" + transactionDate + ", product=" + product + ", price=" + price
				+ ", paymentType=" + paymentType + ", name=" + name + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}

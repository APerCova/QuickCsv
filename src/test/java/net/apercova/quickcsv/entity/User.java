package net.apercova.quickcsv.entity;

import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;

@CsvEntity(headers={
	"﻿User Name",
	"First Name",
	"Last Name",
	"Display Name",
	"Job Title",
	"Department",
	"Office Number",
	"Office Phone",
	"Mobile Phone",
	"Fax",
	"Address",
	"City",
	"State or Province",
	"ZIP or Postal Code",
	"Country or Region"
})
public class User {
	
	@CsvValue(header="﻿User Name")
	private String userName;
	@CsvValue(header="First Name")
	private String fistName;
	@CsvValue(header="Last Name")
	private String lastName;
	@CsvValue(header="Display Name")
	private String displayName;
	@CsvValue(header="Job Title")
	private String jobTitle;
	@CsvValue(header="Department")
	private String department;
	@CsvValue(header="Office Number")
	private int officeNumber;
	@CsvValue(header="Office Phone")
	private String officePhone;
	@CsvValue(header="Mobile Phone")
	private String mobilePhone;
	@CsvValue(header="Fax")
	private String fax;
	@CsvValue(header="Address")
	private String address;
	@CsvValue(header="City")
	private String city;
	@CsvValue(header="State or Province")
	private String state;
	@CsvValue(header="ZIP or Postal Code")
	private String zip;
	@CsvValue(header="Country or Region")
	private String country;
	
	public User() {
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(int officeNumber) {
		this.officeNumber = officeNumber;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", fistName=" + fistName + ", lastName=" + lastName + ", displayName="
				+ displayName + ", jobTitle=" + jobTitle + ", department=" + department + ", officeNumber="
				+ officeNumber + ", officePhone=" + officePhone + ", mobilePhone=" + mobilePhone + ", fax=" + fax
				+ ", address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip + ", country="
				+ country + "]";
	}

}

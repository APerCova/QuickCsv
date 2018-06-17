package net.apercova.quickcsv.entity;

import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;

@CsvEntity
public class WeekDays {

	@CsvValue(index=0)
	private String d_01;
	@CsvValue(index=1)
	private String d_02;
	@CsvValue(index=2)
	private String d_03;
	@CsvValue(index=3)
	private String d_04;
	@CsvValue(index=4)
	private String d_05;
	@CsvValue(index=5)
	private String d_06;
	@CsvValue(index=6)
	private String d_07;
	
	public String getD_01() {
		return d_01;
	}
	public void setD_01(String d_01) {
		this.d_01 = d_01;
	}
	public String getD_02() {
		return d_02;
	}
	public void setD_02(String d_02) {
		this.d_02 = d_02;
	}
	public String getD_03() {
		return d_03;
	}
	public void setD_03(String d_03) {
		this.d_03 = d_03;
	}
	public String getD_04() {
		return d_04;
	}
	public void setD_04(String d_04) {
		this.d_04 = d_04;
	}
	public String getD_05() {
		return d_05;
	}
	public void setD_05(String d_05) {
		this.d_05 = d_05;
	}
	public String getD_06() {
		return d_06;
	}
	public void setD_06(String d_06) {
		this.d_06 = d_06;
	}
	public String getD_07() {
		return d_07;
	}
	public void setD_07(String d_07) {
		this.d_07 = d_07;
	}

	@Override
	public String toString() {
		return "WeekDays [d_01=" + d_01 + ", d_02=" + d_02 + ", d_03=" + d_03 + ", d_04=" + d_04 + ", d_05=" + d_05
				+ ", d_06=" + d_06 + ", d_07=" + d_07 + "]";
	}
	
}

package net.apercova.quickcsv.entity;

import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;

@CsvEntity
public class WeekDays {

	@CsvValue(colnum=0)
	private String d_01;
	@CsvValue(colnum=1)
	private String d_02;
	@CsvValue(colnum=2)
	private String d_03;
	@CsvValue(colnum=3)
	private String d_04;
	@CsvValue(colnum=4)
	private String d_05;
	@CsvValue(colnum=5)
	private String d_06;
	@CsvValue(colnum=6)
	private String d_07;
	
	public String getD_01() {
		return d_01;
	}

	public String getD_02() {
		return d_02;
	}

	public String getD_03() {
		return d_03;
	}

	public String getD_04() {
		return d_04;
	}

	public String getD_05() {
		return d_05;
	}

	public String getD_06() {
		return d_06;
	}

	public String getD_07() {
		return d_07;
	}

	@Override
	public String toString() {
		return "WeekDays [d_01=" + d_01 + ", d_02=" + d_02 + ", d_03=" + d_03 + ", d_04=" + d_04 + ", d_05=" + d_05
				+ ", d_06=" + d_06 + ", d_07=" + d_07 + "]";
	}
	
	
	
}

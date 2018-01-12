package net.apercova.quickcsv.entity;

import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;

@CsvEntity(headers= {"m1","m2","m3","m4","m5","m6","m7","m8","m9","m10","m11","m12"})
public class Month {

	@CsvValue(colnum=0)
	private String m1;
	@CsvValue(colnum=1)
	private String m2;
	@CsvValue(colnum=2)
	private String m3;
	@CsvValue(colnum=3)
	private String m4;
	@CsvValue(colnum=4)
	private String m5;
	@CsvValue(colnum=5)
	private String m6;
	@CsvValue(colnum=6)
	private String m7;
	@CsvValue(colnum=7)
	private String m8;
	@CsvValue(colnum=8)
	private String m9;
	@CsvValue(colnum=9)
	private String m10;
	@CsvValue(colnum=10)
	private String m11;
	@CsvValue(colnum=11)
	private String m12;
	
	public Month() {
	}
	
	public String getM1() {
		return m1;
	}

	public String getM2() {
		return m2;
	}

	public String getM3() {
		return m3;
	}

	public String getM4() {
		return m4;
	}

	public String getM5() {
		return m5;
	}

	public String getM6() {
		return m6;
	}

	public String getM7() {
		return m7;
	}

	public String getM8() {
		return m8;
	}

	public String getM9() {
		return m9;
	}

	public String getM10() {
		return m10;
	}

	public String getM11() {
		return m11;
	}

	public String getM12() {
		return m12;
	}

	@Override
	public String toString() {
		return "Month [m1=" + m1 + ", m2=" + m2 + ", m3=" + m3 + ", m4=" + m4 + ", m5=" + m5 + ", m6=" + m6 + ", m7="
				+ m7 + ", m8=" + m8 + ", m9=" + m9 + ", m10=" + m10 + ", m11=" + m11 + ", m12=" + m12 + "]";
	}
}

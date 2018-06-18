package net.apercova.quickcsv.converter;

public class DataTypeConversionException extends Exception {

	private static final long serialVersionUID = -470438774820727258L;

	public DataTypeConversionException() {
		super();
	}

	public DataTypeConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataTypeConversionException(String message) {
		super(message);
	}

	public DataTypeConversionException(Throwable cause) {
		super(cause);
	}
	
}

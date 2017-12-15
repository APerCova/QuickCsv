package net.apercova.quickcsv;

/**
 * Exception for a Csv reading error.
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @version 1.0 2017.08
 */
public class CsvReaderException extends Exception{

	private static final long serialVersionUID = 2070949263453202075L;

	public CsvReaderException() {
    }

    public CsvReaderException(String message) {
        super(message);
    }

    public CsvReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvReaderException(Throwable cause) {
        super(cause);
    }
}

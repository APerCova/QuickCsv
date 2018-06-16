package net.apercova.quickcsv.writer;

/**
 * Exception for a Csv writing error.
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @since 1.0
 */
public class CsvWriterException extends Exception{

	private static final long serialVersionUID = 5894028277291150032L;

	public CsvWriterException() {
    }

    public CsvWriterException(String message) {
        super(message);
    }

    public CsvWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvWriterException(Throwable cause) {
        super(cause);
    }
}

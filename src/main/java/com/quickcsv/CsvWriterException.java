package com.quickcsv;

/**
 * Exception for a Csv writing error.
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @version 1.0 2017.08
 */
public class CsvWriterException extends Exception{

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

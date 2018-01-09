package net.apercova.quickcsv.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.apercova.quickcsv.converter.DatatypeConverter;

@Retention(RUNTIME)
@Target(FIELD)
public @interface CsvDatatypeConverter {
	Class<? extends DatatypeConverter<?>> value();
}

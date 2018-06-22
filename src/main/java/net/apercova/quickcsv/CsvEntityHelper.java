package net.apercova.quickcsv;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;

public final class CsvEntityHelper<T> {
	
	
	protected CsvEntityHelper() {
	}
	
	public static <E> Map<Field, Integer> findAnnotatedFields(Class<E> type){
		
		Map<Field, Integer> fieldMap = new HashMap<Field, Integer>();
		
		CsvEntity rowMeta = type.getAnnotation(CsvEntity.class);
		String[] headers = rowMeta.headers();
		
		CsvValue valMeta = null;
		for(Field f: type.getDeclaredFields()) {
			if(f.isAnnotationPresent(CsvValue.class)) {
				valMeta = f.getAnnotation(CsvValue.class);
				if(valMeta.header() != null && valMeta.header().length() > 0 
						&& headers.length > 0) {
					//Resolve position by header
					int hpos = findHeaderPosition(headers, valMeta.header());
					if(hpos >= 0) {
						fieldMap.put(f, hpos);
					}
				}else {
					//Resolve position by index
					int hpos = valMeta.index();
					if(hpos >= 0) {
						fieldMap.put(f,hpos);
					}
				}
			}
		}
		
		return fieldMap;
	}
	
	public static int findHeaderPosition(String[] headers, String lookup) {
		int hpos = -1;
		if(lookup != null && lookup.length() > 0) {
			String test = null;
			for(int i = 0; i < headers.length; i++) {
				test = headers[i];
				if(lookup.equals(test)) {
					hpos = i;
					break;
				}
			}
		}
		return hpos;
	}
	
}

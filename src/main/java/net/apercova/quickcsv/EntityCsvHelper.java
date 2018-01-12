package net.apercova.quickcsv;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.apercova.quickcsv.annotation.CsvEntity;
import net.apercova.quickcsv.annotation.CsvValue;

public class EntityCsvHelper<T> {
	
	
	protected EntityCsvHelper() {
	}
	
	static <E> Map<Integer, Field> getAnnotatedFields(Class<E> type){
		Map<Integer, Field> fieldMap = new TreeMap<Integer, Field>();
		
		CsvEntity rowMeta = type.getAnnotation(CsvEntity.class);
		List<String> headerList = Arrays.asList(rowMeta.headers());
		
		CsvValue valMeta = null;
		for(Field f: type.getDeclaredFields()) {
			if(f.isAnnotationPresent(CsvValue.class)) {
				valMeta = f.getAnnotation(CsvValue.class);
				if(valMeta.header() != null && valMeta.header().length() > 0 
						&& !headerList.isEmpty()) {
					//Resolve position by header
					int hpos = getHeaderPosition(headerList, valMeta.header());
					if(hpos >= 0) {
						fieldMap.put(hpos, f);
					}else {
						Object[] kArr = fieldMap.keySet().toArray();
						int lastPos = (Integer)kArr[kArr.length-1];
						if(!fieldMap.containsKey(lastPos)) {
							fieldMap.put(lastPos, f);
						}else {
							fieldMap.put(lastPos + 1, f);
						}						
					}
				}else {
					//Resolve position by index
					int hpos = valMeta.colnum();
					if(hpos >= 0) {
						fieldMap.put(hpos, f);
					}else {
						fieldMap.put(fieldMap.size(), f);
					}
				}
			}
		}
		
		return fieldMap;
	}
	
	static int getHeaderPosition(List<String> headers, String lookup) {
		int hpos = -1;
		if(lookup != null && lookup.length() > 0) {
			for(String h: headers) {
				if(h.equals(lookup)) {
					hpos = headers.indexOf(h);
					break;
				}
			}
		}
		return hpos;
	}
	
}

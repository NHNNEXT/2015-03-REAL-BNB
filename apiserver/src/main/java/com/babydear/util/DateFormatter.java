package com.babydear.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateFormatter {
	
	public Integer calculateFromBabyToCard(String b, String c){
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt1 = formatter.parseDateTime(b);
		DateTime dt2 = formatter.parseDateTime(c);
		Days daysBetween = Days.daysBetween(dt1.toLocalDate(), dt2.toLocalDate());
		return daysBetween.getDays();
	}
	
}

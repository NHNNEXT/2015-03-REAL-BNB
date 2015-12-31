package com.babydear.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateFormatter {
	
	public String calculateFromBabyToCard(String b, String c){
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt1 = formatter.parseDateTime(b);
		DateTime dt2 = formatter.parseDateTime(c);
		Integer between = Days.daysBetween(dt1.toLocalDate(), dt2.toLocalDate()).getDays();
		
		if(between <0){
			return "D"+between+"일";
		}else if(between <30){
			return between+"일";
		}else if(between <365){
			return Months.monthsBetween(dt1.toLocalDate(), dt2.toLocalDate()).getMonths()+"개월";
		}else {
			return Years.yearsBetween(dt1.toLocalDate(), dt2.toLocalDate()).getYears()+"살";
		}
	}
}

package br.com.mymarket.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String formatDate(Date date){
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	public static String formatHour(Date date){
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}
	
	public static Date toDate(String data) throws ParseException{
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(data);
	}
	
	@SuppressWarnings("deprecation")
	public static void zerarData(Date date){
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
	}
}

package com.skycober.coberchat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.content.Context;

public class CalendarUtil {
	private static CalendarUtil _instance;
	
	public static CalendarUtil getInstance(Context context){
		if(null == _instance){
			_instance = new CalendarUtil();
		}
		return _instance;
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getFormatDate(DateDisplayType type, long date, boolean isMilliSecond){
		long desDate = isMilliSecond ? date : date * 1000l;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(desDate);
		SimpleDateFormat sdf = null;
		if(type == DateDisplayType.YMDHMS){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			sdf = new SimpleDateFormat();
		}
		return sdf.format(cal.getTime());
	}
	
	public enum DateDisplayType{
		/**
		 * yyyy-MM-dd HH:mm:ss
		 */
		YMDHMS,
		/**
		 * other
		 */
		Other
	}
}

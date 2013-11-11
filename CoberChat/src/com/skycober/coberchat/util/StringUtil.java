package com.skycober.coberchat.util;

import android.content.Context;

public class StringUtil {
	private static StringUtil _instance;
	public static StringUtil getInstance(Context context){
		if(null == _instance){
			_instance = new StringUtil();
		}
		return _instance;
	}
	
	public boolean IsEmpty(String str){
		return null == str || "".equalsIgnoreCase(str);
	}
}

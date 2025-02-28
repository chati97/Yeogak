package com.ssafy.enjoytrip.util;

public class ParameterCheck {

	public static String nullToBlank(String str) {
		return str == null ? "" : str;
	}
	
	public static String nullToOff(String str) {
		return str == null ? "off" : str;
	}
	
	public static int notNumberToZero(String str) {
		if(isNumber(str))
			return Integer.parseInt(str);
		else
			return 0;
	}
	
	public static int notNumberToOne(String str) {
		if(isNumber(str))
			return Integer.parseInt(str);
		else
			return 1;
	}
	
	public static int notNumberToInteger(String str, int n) {
		if (isNumber(str))
			return Integer.parseInt(str);
		else
			return n;
	}

	private static boolean isNumber(String str) {
		boolean isNum = true;
		if(str == null || str.length() == 0)
			isNum = false;
		else {
			for (int i = 0; i < str.length(); i++) {
				int num = str.charAt(i) - 48;
				if(num < 0 || num > 9) {
					isNum = false;
					break;
				}
			}
		}
		return isNum;
	}
	
}

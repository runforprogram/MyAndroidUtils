package com.run.util;

public class Until {
	public static String formatLongToTimeStr(int millisecond) {
		long second = (millisecond / 1000) % 60;
		long minute = (millisecond / (1000 * 60)) % 60;
		long hour = (millisecond / (1000 * 60 * 60)) % 24;
        if (hour>0) {
          return	 String.format("%02d:%02d:%02d", hour, minute, second);
		}else {
			return  String.format("%02d:%02d",minute, second);
		}
	}
}

package hbc315.HIDC.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QueryDateUtil {
	/**
	 * 返回几个月前的时间（yyyyMM），num=0时为本月时间，1时为上月时间
	 * @param num
	 * @return
	 */
	public static String getBeforeMonth(int num){
	   Calendar c = Calendar.getInstance();
	   c.add(Calendar.MONTH, -num);
	   SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
	   String time = format.format(c.getTime());
	   return time;
	}
	
	/**
	 * 返回当天时间（yyyyMMdd）
	 * @param num
	 * @return
	 */
	public static String getToday(){
	   Calendar c = Calendar.getInstance();
	   SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	   String time = format.format(c.getTime());
	   return time;
	}
	
	public static int getDayNow(){
	   Calendar c = Calendar.getInstance();
	   c.add(Calendar.DATE, -18);
	   SimpleDateFormat format = new SimpleDateFormat("dd");
	   String time = format.format(c.getTime());
	   return Integer.valueOf(time);
	}
	//2016-07-08 18:07:50
	public static String getTimestamp(){
		   Calendar c = Calendar.getInstance();
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
		   String time = format.format(c.getTime());
		   return time;
		}
	
	public static void main(String[] args){
//		System.out.println(getBeforeMonth(0));
		System.out.println(getTimestamp());
//		System.out.println(getToday());
	}
}

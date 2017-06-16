package hbc315.HIDC.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomNumUtil {

	public static String getRandomNumber_16() {
	
		DecimalFormat dcmFmt = new DecimalFormat("0.0000000000000000");
		Random rand = new Random();
		
		float f = rand.nextFloat();
		return dcmFmt.format(f).toString();
	}

	public static String getRandomNumber_18() {
	
		DecimalFormat dcmFmt = new DecimalFormat("0.000000000000000000");
		Random rand = new Random();
		
		float f = rand.nextFloat();
		return dcmFmt.format(f).toString();
	}
	
	public static String getTimeAndRandom17(){
		   Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		   String dateString = formatter.format(currentTime);
		   
		   DecimalFormat dcmFmt = new DecimalFormat("###.0000000000000000");
		   Random rand = new Random();		
		   float f = rand.nextFloat();
		   
		   return dateString + dcmFmt.format(f).toString();
	}
	
	public static String getRandomNumber6_9() {
		
		DecimalFormat dcmFmt = new DecimalFormat("0.000000000");
		Random rand = new Random();
		
		float f = rand.nextFloat();
		f = f + f * 1000000;
		return dcmFmt.format(f).toString();
	}
	
	public static void main(String args[]){
		System.out.println(getRandomNumber_18());
	}
}
package demo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date getDateStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return new Date(calendar.getTime().getTime());
	}
	
	public static String getStringFromDate(Date date){
		SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdfnew.format(date);
		return dateString;
	}
	
	public static Date getDateFromString(String date){
		SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateString = null;
		try {
			dateString = sdfnew.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	public static Date getDateEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return new Date(calendar.getTime().getTime());
	}
	public static Calendar getDate(Integer offset){
		Calendar cal=Calendar.getInstance();//使用日历类
		if(offset==null){
			offset = new Integer(0);
		}
		cal.add(Calendar.DATE,offset);
		return cal;
	}
	public static String getDateString(Integer offset,String format){
		Calendar cal=Calendar.getInstance();//使用日历类
		if(offset==null){
			offset = new Integer(0);
		}
		cal.add(Calendar.DATE,offset);
		return new SimpleDateFormat(format).format(cal.getTime());
	}
	public static boolean compare(String date1,String date2) throws ParseException{  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        Date a = sdf.parse(date1);  
        Date b = sdf.parse(date2);   
        if(a.getTime()-b.getTime()<0) {
        	return true; 
        }else {
        	return false; 
        }
        
	}
	public static int getday(Date date1,Date date2){
		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			date1 = sdf.parse(sdf.format(date1));
			date2 = sdf.parse(sdf.format(date2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long dd = date1.getTime()-date2.getTime();
		if(dd<=0){
			return -1;
		}
		return (int) (dd/1000/60/60/24);
	}

	public static String getNow(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = sdf.format(new Date());
		return now;
	}
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(new Date());
		return now;
	}
}

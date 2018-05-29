package demo.common;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
/**   
 * 日期工具
 */
public class DateUtils {
	// 一天的毫秒数 60*60*1000*24
	public final static long DAY_MILLIS = 86400000;

	// 一小时的毫秒数 60*60*1000
	private final static long HOUR_MILLIS = 3600000;

	// 一分钟的毫秒数 60*1000
	private final static long MINUTE_MILLIS = 60000;
	
	/**默认时区名称**/
	private final static String DEFAULT_TIME_ZONE = "Asia/Shanghai";


	@SuppressWarnings("deprecation")
	public static Date getDaysDate(Date date, Integer days) {
		date.setDate(date.getDate() + (days));
		return date;
	}
	
	/**
	 * 获取某个月的第一天
	 * @param dt
	 * @param formatStr
	 * @return
	 */
	public static String getFirstDay(Date dt,String formatStr) {
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(dt);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		return day_first;
	}
   
	/**
	 * 获取某个月的最后一天
	 * @param dt
	 * @param formatStr
	 * @return
	 */
	public static String getLastDay(Date dt,String formatStr) {
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = df.format(dt);
		return last;
	}
	/**
	 * 计算两个日期相差天数 bengin >=end 返回0 bengin < end返回相差天数
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int countDays(String begin, String end) {
		int days = 0;

		DateFormat dateFormate = new SimpleDateFormat("yyyyMMdd");
		Calendar beginDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();

		try {
			beginDate.setTime(dateFormate.parse(begin));
			endDate.setTime(dateFormate.parse(end));

			while (beginDate.before(endDate)) {
				days++;
				beginDate.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}

		return days;
	}

	/**
	 * 计算两个日期之差的间隔天数,开始时间必须小于结束时间。
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getDays(Date start, Date end) {
		if (start.getTime() > end.getTime()) {
			throw new RuntimeException("开始时间必须小于结束时间!");
		}
		long temp = end.getTime() - start.getTime();
		return (int) (temp / DAY_MILLIS);
	}

	/**
	 * 计算两个日期相差的天、小时、分钟
	 * 
	 * @param start
	 * @param end
	 */
	public static String show(Date start, Date end) {
		long temp = end.getTime() - start.getTime();
		String leavingTime = temp / DAY_MILLIS + "天" + (temp % DAY_MILLIS) / HOUR_MILLIS + "小时" + ((temp % DAY_MILLIS) % HOUR_MILLIS) / MINUTE_MILLIS + "分";
		return leavingTime;
	}

	/**
	 * 计算当前24小时前的时间
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static Date getDateBeforeOneDay() {
		// 当前时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 24小时之前的时间
		currentTime.setDate(currentTime.getDate() - 1);
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 计算当前时间
	 * 
	 */
	public static Date getCurrentDate() {
		// 当前时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 定自义格式当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date.getTime());
	}
	
	/**
	 * 日期转换
	 * 
	 * @param format
	 * @return
	 */
	public static Date parseDate(String dateStr, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(dateStr, pos);
	}

	/**
	 * 计算的7天后时间
	 * 
	 */
	public static Date getSevenDaysDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.DATE, 7);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formatter.format(new Date(c.getTimeInMillis()));
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}
	
	/**
	 * 计算N天后的日期
	 * 
	 * @param date
	 * @param par
	 * @return
	 */
	public static Date getAfterDay(Date date, int par) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.DATE, par);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(new Date(c.getTimeInMillis()));
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 计算N个月后的日期
	 * 
	 * @param date
	 * @param par
	 * @return
	 */
	public static Date getAfterMonth(Date date, int par) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.MONTH, par);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(new Date(c.getTimeInMillis()));
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}
	
	/**
	 * 计算N个月后的日期（带顺延），如1月31号加1个月为2月28号，则顺延一天变为3月1号
	 *
	 * @author  wangjf
	 * @date    2015年8月22日 下午3:26:10
	 * @param date
	 * @param par
	 * @return
	 */
	public static Date getAfterMonthNext(Date date, int par) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		int oldDate = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.MONTH, par);
		int newDate = c.get(Calendar.DAY_OF_MONTH);
		// 判断新月天数是否小于旧月天数，若小于则表明出现了28 < 30, 30 < 31之类的，需顺延
		if(newDate < oldDate) {
			c.add(Calendar.DATE, 1); // 加1天
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(new Date(c.getTimeInMillis()));
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 获得yyyy/MM/dd日期格式
	 * 
	 */
	public static Date getDateyyyyMMdd() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String d = formatter.format(date);
		Date r = null;
		try {
			r = formatter.parse(d);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return r;
	}

	/**
	 * 获得yyyy/MM/dd日期格式
	 * 
	 */
	public static Date setDateyyyyMMDD(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String d = formatter.format(date);
		Date r = null;
		try {
			r = formatter.parse(d);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return r;
	}

	/**
	 * 获取时间,年月日格式（yyyy-MM-dd）
	 */
	// 获取时间（yyyy-MM-dd）
	public static String getYMDTime(Date date) {
		String dateformat = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		return dateFormat.format(date);
	}

	/**
	 * 获取时间,时分秒格式（HH:mm:ss）
	 */
	// 获取时间（HH:mm:ss）
	public static String getHMSTime(Date date) {
		String dateformat = "HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		return dateFormat.format(date);
	}

	/**
	 * 获取首页列表的时间列表
	 * 
	 * @author Yuan Changchun
	 * @date 2013-10-25 下午03:20:37
	 * @param monthNum
	 * @return
	 */
	public static List<Date> getRecentNumMonDateList(Integer monthNum) {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		currentDate = calendar.getTime();
		List<Date> resultList = new ArrayList<Date>();
		for (int i = 0; i < monthNum; i++) {
			resultList.add(currentDate);
			currentDate = getPrevMonthDate(currentDate);
		}
		return resultList;
	}

	private static Date getPrevMonthDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 
	 * 获得指定业务日期的所在年份
	 * 
	 * @author HuangXiaodong 2014-10-08, 20:28:47
	 * @param date
	 * @return
	 */
	public static String getYear(String date) {
		String year = null;
		try {
			year = date.substring(0, 4);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return year;
	}

	/**
	 * 获得指定业务日期的所在月份
	 * 
	 * @author HuangXiaodong 2014-10-08, 20:28:47
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getMonth(String date) throws ParseException {
		int month = Integer.parseInt(date.substring(4, 6));
		return month;
	}

	/**
	 * 判断两个日期之间相差多少月
	 * 
	 * @param begindate
	 * @param enddate
	 * @return
	 * @author HuangXiaodong 2014-10-08, 20:28:47
	 * @modified
	 */
	public static int compareDateMonth(String begindate, String enddate) {
		try {
			int year1 = Integer.parseInt(getYear(begindate));
			int month1 = getMonth(begindate);
			int day1 = Integer.parseInt(begindate.substring(6, 8));
			int year2 = Integer.parseInt(getYear(enddate));
			int month2 = getMonth(enddate);
			int day2 = Integer.parseInt(enddate.substring(6, 8));
			int month = (year2 - year1) * 12 + month2 - month1;
			if (day2 - day1 > 0)
				return month + 1;
			else
				return month;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回当前web机器的系统日期
	 * 
	 * @param format
	 *            ps: yyyyMMdd or yyyyMMdd HH:mm:ss or yyyy-MM-dd
	 * @return java.lang.String
	 * @author HuangXiaodong 2014-10-09, 09:23
	 */
	public static String getSysDate(String format) {
		String sSysdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar rightNow = Calendar.getInstance();
		sSysdate = sdf.format(rightNow.getTime());
		return sSysdate;
	}

	/**
	 * 返回当前web机器的系统日期yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getSysDate() {
		String sSysdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		sSysdate = sdf.format(rightNow.getTime());
		return sSysdate;
	}

	/**
	 * 获取指定日期 指定分钟后的日期
	 * 
	 * @author caoyi
	 * @date 2014-10-9 下午03:34:37
	 * @param monthNum
	 * @return
	 */
	public static Date getDateAfterByMinute(Date date, Integer minute) {
		Date returnDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			returnDate = sdf.parse(sdf.format(date.getTime() + MINUTE_MILLIS * minute));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return returnDate;
	}

	public static boolean compare_date(Date date1, Date date2) {
		boolean flag = false;
		if (date1.getTime() >= date2.getTime()) {
			return true;
		}
		return flag;
	}

	/**
	 * 
	 * 获得指定日期的前推或后移若干自然日的日期
	 * 
	 * @param date
	 *            指定日期
	 * @param num
	 *            大于0后移，小于0前推
	 * @param format
	 *            ps: yyyyMMdd or yyyyMMdd HH:mm:ss or yyyy-MM-dd      
	 * @author HuangXiaodong ,2014-11-17
	 * @modified
	 */
	public static String addStringDa(String date, Integer num,String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date datetime;
		try {
			datetime = simpleDateFormat.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(datetime);
			calendar.add(Calendar.DATE, num);
			datetime = calendar.getTime();
			return simpleDateFormat.format(datetime);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 计算下一还款日
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	static public Date nextPayDate(Calendar date, String day) {
		Objects.requireNonNull(date);
		int dateDD = date.get(Calendar.DAY_OF_MONTH);
		int dd = Integer.parseInt(day);
		date.set(Calendar.DAY_OF_MONTH, dd);
		if (dateDD >= dd) {
			date.add(Calendar.MONTH, 1);
		}
		return date.getTime();
	}

	static public Date nextPayDate(Date date, String day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return nextPayDate(cal, day);
	}

	/**
	 * 下一还款日包含还款日当天
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	static public Date nextPayDateIncludeToday(Calendar date, String day) {
		Objects.requireNonNull(date);
		int dateDD = date.get(Calendar.DAY_OF_MONTH);
		int dd = Integer.parseInt(day);
		date.set(Calendar.DAY_OF_MONTH, dd);
		if (dateDD > dd) {
			date.add(Calendar.MONTH, 1);
		}
		return date.getTime();
	}

	/**
	 * 获取当前系统时间在这个月是第几天
	 */
	static public String getNowDayTrimZero() {
		Calendar cal = Calendar.getInstance();
		return "" + cal.get(Calendar.DAY_OF_MONTH);
	}

	static public String getNowDayTrimZero(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return "" + cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回指定格式时间
	 * 
	 * @param format
	 *            ps: yyyyMMdd or yyyyMMdd HH:mm:ss or yyyy-MM-dd or yyyy-MM-dd
	 *            HH:mm:ss
	 * @return java.lang.String
	 * @author HuangXiaodong 2014-12-08, 16:55
	 */
	public static String getSysDates(Date date, String format) {
		String sSysdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		sSysdate = sdf.format(rightNow.getTime());
		return sSysdate;
	}

	public static String getLastDaysString(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long times = cal.getTimeInMillis() - System.currentTimeMillis();
		long day = times / (24 * 60 * 60 * 1000);
		long hour = (times / (60 * 60 * 1000) - day * 24);
		long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String lastDate = day + "天" + hour + "小时" + min + "分" + sec + "秒";
		return lastDate;
	}

	/**
	 * 获取上一个还款日
	 * 
	 * @param day
	 * @return
	 */
	public static Calendar lastPayDateWithPattern(Date date, String day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dd = Integer.parseInt(day);
		int date_dd = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, dd);
		if (date_dd < dd) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		}
		return cal;
	}

	/**
	 * 判断是否需要风险金垫付
	 * 
	 * @param date
	 * @param repaymentDay
	 * @param interval
	 * @return
	 */
	public static boolean isRiskAdvanceDay(Date date, String repaymentDay, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis() / DAY_MILLIS - lastPayDateWithPattern(date, repaymentDay).getTimeInMillis() / DAY_MILLIS == interval;
	}

	/**
	 * 查找还款日前 count 天 集合
	 * 
	 * @param paymentDayStr
	 *            还款日
	 * @param count
	 *            总数
	 * @param includePaymentDay
	 *            是否包含还款日本身
	 * @return
	 */
	public static int[] getBeforePayDayDays(String paymentDayStr, int count, boolean includePaymentDay) {
		int paymentDay = Integer.parseInt(paymentDayStr);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, paymentDay);
		int[] result = new int[count];
		int i = 0;
		if (includePaymentDay) {
			result[0] = paymentDay;
			i++;
		}
		for (; i < count; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			result[i] = calendar.get(Calendar.DAY_OF_MONTH);
		}
		return result;
	}
	/**
	 * 将字符串时间格式字符串转换为date时间格式
	 * @author HuangXiaodong  2015-04-21
	 * @param strDate  指定字符串时间
	 * 
	 * @param format
	 *            ps: yyyyMMdd or yyyyMMdd HH:mm:ss or yyyy-MM-dd
	 * 
	 * @return
	 */
	public static Date strToDate(String strDate,String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * 
	 * 获得指定日期的前推或后移若干自然日的日期返回指定的类型
	 * 
	 * @param date
	 *            指定日期
	 * @param num
	 *            大于0后移，小于0前推
	 *            
	 * @param format
	 *            ps: yyyyMMdd or yyyyMMdd HH:mm:ss or yyyy-MM-dd
	 * @return Date
	 * @author HuangXiaodong ,2015-04-21
	 * @modified
	 */
	public static Date addDays(String date, Integer num,String format){
		return strToDate(addStringDa(date, num,format), format);
	}
	
	/**
 	 * 
 	 * 计算两个日期之间相差几天
 	 * 
 	 * @param beginDate
 	 *            开始日期 
 	 * @param endDate
 	 *            截止日期 
 	 * @return 相差天数
 	 * @author zhoudl
 	 */
	public static int datePhaseDiffer(Date beginDate, Date endDate) {
 		try {
 			return (int) ((beginDate.getTime() - endDate.getTime()) / 86400000);
 		} catch (Exception e) {
 			throw new RuntimeException(e);
 		}
 	}
 	
 	/**
 	 * 计算两个日期之间相差几小时
 	 *
 	 * @author  wangjf
 	 * @date    2015年7月2日 下午3:37:23
 	 * @param beginDate
 	 * @param endDate
 	 * @return
 	 */
 	public static int hourPhaseDiffer(Date beginDate, Date endDate) {
 		try {
 			return (int) ((beginDate.getTime() - endDate.getTime()) / 3600000);
 		} catch (Exception e) {
 			throw new RuntimeException(e);
 		}
 	}
 	
 	/**
 	 * 计算两个日期之间相差几分钟
 	 *
 	 * @author  wangjf
 	 * @date    2015年7月2日 下午3:37:26
 	 * @param beginDate
 	 * @param endDate
 	 * @return
 	 */
 	public static int minutePhaseDiffer(Date beginDate, Date endDate) {
 		try {
 			return (int) ((beginDate.getTime() - endDate.getTime()) / 60000);
 		} catch (Exception e) {
 			throw new RuntimeException(e);
 		}
 	}
 	
 	/**
 	 * 计算两个日期之间相差几秒
 	 *
 	 * @author  wangjf
 	 * @date    2015年7月2日 下午3:37:53
 	 * @param beginDate
 	 * @param endDate
 	 * @return
 	 */
 	public static int secondPhaseDiffer(Date beginDate, Date endDate) {
 		try {
 			return (int) ((beginDate.getTime() - endDate.getTime()) / 1000);
 		} catch (Exception e) {
 			throw new RuntimeException(e);
 		}
 	}
 	
 	/**
	 * 在指定日期上加上指定天数
	 * @return Date
	 * @author zhoudl
	 */
	public static Date getAppointDateAddDayDate(Date date,int day){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	
	 /**
 	 * 页面显示的日期型 显示的样式是yyyyMMdd
 	 * @param date
 	 * @return 
 	 * @author zhoudl
 	 */
 	public static String showDateString(Date date){
 		if (null == date ){
 			throw new RuntimeException("日期不能为空");
 		}
 		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
 		return simpleDateFormat.format(date);
 	}
	
	/**
	 * 获取现在时间
	 * @author HuangXiaodong ,2015-04-21
	 * @param format
	 *            ps: yyyyMMdd or yyyyMMdd HH:mm:ss or yyyy-MM-dd
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort(String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	  * 字符串转换成日期
	  * @param dateString
	  * @param formatStyle
	  * @return
	  * @throws ParseException
	  */
	 public static Date parseStringToDate( String dateString,String formatStyle ){
		 try { 
			 DateFormat dateFormat = new SimpleDateFormat(formatStyle);
			 return dateFormat.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("你输入的日期不合法，请重新输入");
		}
	 }

	/**
	 * 获取某天起始时间
	 *
	 * @author  wangjf
	 * @date    2015年4月30日 下午2:26:36
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date){
		String dateString = DateUtils.formatDate(date, "yyyy-MM-dd") + " 00:00:00";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(dateString, pos);
	}
	
	/**
	 * 获取某天结束时间
	 *
	 * @author  wangjf
	 * @date    2015年4月30日 下午2:26:48
	 * @param date
	 * @return
	 */
	public static Date getEndDate(Date date){
		String dateString = DateUtils.formatDate(date, "yyyy-MM-dd") + " 23:59:59";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(dateString, pos);
	}
	
	/**
	 * 获取当月第一天起始时间
	 *
	 * @author  wangjf
	 * @date    2015年4月30日 下午3:45:24
	 * @param date
	 * @return
	 */
	public static Date getMonthStartDate(Date date){
		String dateString = DateUtils.formatDate(date, "yyyy-MM") + "-01 00:00:00";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(dateString, pos);
	}
	
	/**
	 * 将yyyy-MM-dd格式的字符串转为日期格式
	 *
	 * @author  wangjf
	 * @date    2015年5月1日 下午3:40:42
	 * @param dateStr
	 * @return
	 */
	public static Date parseStandardDate(String dateStr){
		return DateUtils.parseDate(dateStr, "yyyy-MM-dd");
	}
	
	/**
	 * 获取两日期相差天数 DATE：2014/07/04
	 * 
	 * @author zhoudl
	 * **/
	public static int getPhaseDiffer(Date oneDate, Date twoDate){
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			oneDate = simpleDateFormat.parse(simpleDateFormat.format(oneDate));
			twoDate = simpleDateFormat.parse(simpleDateFormat.format(twoDate));
			long oneTime = oneDate.getTime();
			long twoTime = twoDate.getTime();
			long day = (twoTime - oneTime) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(day));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 日期Date转换为Timestamp
	 * **/
	public static Timestamp getDateToTimeStamp(Date paramDate){
		if(paramDate==null){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(paramDate);
		Timestamp timeStamp=new Timestamp(cal.getTimeInMillis());
		return timeStamp;
	}
	
	/**
	 * 获取上个月的今天(Timestamp)
	 * **/
	public static Timestamp getLastMonthTimeStamp(Date currDate){
		if(currDate==null){
			throw new RuntimeException("日期对象不能为空");
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);		
		Timestamp timeStamp=new Timestamp(cal.getTimeInMillis());
		return timeStamp;
	}
	
	/**
	 * 指定日期之前的某个固定时间
	 * **/
	public static Timestamp getTimeStamp(Date currDate,int num){
		if(currDate==null){
			throw new RuntimeException("日期对象不能为空");
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -num);		
		Timestamp timeStamp=new Timestamp(cal.getTimeInMillis());
		return timeStamp;
	}
	
	
	/**
	 * 获取两日期之间相差月数
	 * **/
	public static int getSpaceMonths(Timestamp startDate,Timestamp endDate){
		 Calendar startCalendar = Calendar.getInstance();
	     Calendar endCalendar = Calendar.getInstance();
	     startCalendar.setTime(startDate);
	     endCalendar.setTime(endDate);
	     
	     int startYear=startCalendar.get(Calendar.YEAR);
	     int endYear=endCalendar.get(Calendar.YEAR);
	     int startMonth=startCalendar.get(Calendar.MONTH);
	     int endMonth=endCalendar.get(Calendar.MONTH);
	     
	     int months=Math.abs(endYear-startYear)*12+(endMonth-startMonth);
	     return months;
	}
	
	/**
	 * 获取两日期之间相差月数
	 * **/
	public static int getSpaceMonths(Date startDate,Date endDate){
		 Calendar startCalendar = Calendar.getInstance();
	     Calendar endCalendar = Calendar.getInstance();
	     startCalendar.setTime(startDate);
	     endCalendar.setTime(endDate);
	     
	     int startYear=startCalendar.get(Calendar.YEAR);
	     int endYear=endCalendar.get(Calendar.YEAR);
	     int startMonth=startCalendar.get(Calendar.MONTH);
	     int endMonth=endCalendar.get(Calendar.MONTH);
	     
	     int months=Math.abs(endYear-startYear)*12+(endMonth-startMonth);
	     return months;
	}
	
	
	/**
	 * 获取上个月的今天(Date)
	 * **/
	public static Date getLastMonthDate(Date date){
		if(date==null){
			throw new RuntimeException("日期对象不能为空");
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return getSevenDaysDate(cal.getTime());
	}
	
	@SuppressWarnings("deprecation")
	public static Timestamp getTimestampYMD(Timestamp timestamp){
		if(timestamp==null){
			throw new RuntimeException("日期对象不能为空");
		}
		timestamp.setHours(0);
		timestamp.setMinutes(0);
		timestamp.setSeconds(0);
		timestamp.setNanos(0);
		return timestamp;
	}
	
	/***
	 * 获取带有时区的日期字符
	 * @author zhangzs
	 * @param _timeZone
	 * @return
	 */
	public static String getFormatedDateString(String _timeZone){  
	    if(StringUtils.isEmpty(_timeZone))  
	    	_timeZone = DEFAULT_TIME_ZONE;
	    TimeZone timeZone = TimeZone.getTimeZone(_timeZone);  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
	    sdf.setTimeZone(timeZone);  
	    TimeZone.setDefault(timeZone);  
	    return sdf.format(new Date());  
	  }  
	
	public static void main(String[] args) {
		System.out.println(getStringDateShort("yyyy-MM-dd HH:mm:ss"));
	}
	
}

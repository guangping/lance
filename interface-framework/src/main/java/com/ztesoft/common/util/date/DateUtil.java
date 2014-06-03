package com.ztesoft.common.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ztesoft.common.util.StringUtils;

public class DateUtil {
	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_TIMESTAMP = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_TIME = "yyyyMMddHHmmss";
	public static final String PATTERN_DATE = "yyyyMMdd";
	public static final int TRUNCTO_YEAR = 0;
	public static final int TRUNCTO_MONTH = 1;
	public static final int TRUNCTO_DAY = 2;
	public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_MONTH = "yyyyMM";
	public static final String PATTERN_SHORT = "yyyyMMdd";

	// 系统默认日期格式
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	// 系统默认日期时间格式
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	// 8位日期格式
	public static final String DATE_FORMAT_8 = "yyyyMMdd";
	// 14位日期时间格式
	public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";
	// 12位日期格式
	public static final String DATE_TIME_FORMAT_12 = "yyMMddHHmmss";
	// 默认失效时间
	public static final String DEFAULT_EXPIRED_DATE = "2030-1-1 00:00:00";

	public static final String DATE_FORMAT_MONTH = "yyyyMM";

	public static Date current() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	public static Timestamp currentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp addDay(Timestamp time, int days) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time.getTime());
		c.add(6, days);
		return new Timestamp(c.getTimeInMillis());
	}

	public static Timestamp addDay(Date time, int days) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time.getTime());
		c.add(6, days);
		return new Timestamp(c.getTimeInMillis());
	}

	public static Timestamp trunc(Timestamp time, int truncTo) {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time.getTime());
		int year = 0, month = 0, date = 1, hour = 0, minute = 0, second = 0;
		if (truncTo >= TRUNCTO_YEAR) {
			year = c.get(Calendar.YEAR);
		}
		if (truncTo >= TRUNCTO_MONTH) {
			month = c.get(Calendar.MONTH);
		}
		if (truncTo >= TRUNCTO_DAY) {
			date = c.get(Calendar.DAY_OF_MONTH);
		}
		c.set(year, month, date, hour, minute, second);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());
	}

	public static void main(String[] args) {
		String day = DateUtil.current("dd");
		System.out.println(day);
	}

	public static java.sql.Date addDay(java.sql.Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(6, days);
		return new java.sql.Date(c.getTimeInMillis());
	}

	public static Date add(Date date, int filed, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(filed, amount);
		return new Date(c.getTimeInMillis());
	}

	public static Calendar toCalander(Timestamp timestamp) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timestamp.getTime());
		return c;
	}

	public static Calendar toCalander(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		return c;
	}

	/**
	 * format date use default pattern: yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, defaultPattern);
	}

	public static String formatCurDate(String pattern) {
		Date currentDate = getCurrnetDate();
		return formatDate(currentDate, pattern);
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return null;
		if (pattern == null) {
			throw new IllegalArgumentException("pattern is null");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			return formatter.format(date);
		}
	}

	public static Date parse(String dateStr, Class resultClass)
			throws ParseException {
		return parse(dateStr, defaultPattern, resultClass);
	}

	public static Date parseAsDate(String dateStr) {
		return parse(dateStr, defaultPattern, java.sql.Date.class);
	}

	public static Date parseAsDate(String dateStr, String pattern) {
		return parse(dateStr, pattern, java.sql.Date.class);
	}

	public static Date parseAsTimestamp(String dateStr) {
		return parse(dateStr, defaultPattern, java.sql.Timestamp.class);
	}

	public static Date parse(String dateStr, String pattern, Class resultClass) {
		if (dateStr == null)
			throw new IllegalArgumentException("dateStr is null");
		if (pattern == null) {
			throw new IllegalArgumentException("pattern is null");
		} else {

			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			Date date;
			try {
				date = formatter.parse(dateStr);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			if (resultClass == null)
				return date;
			else if (resultClass.equals(java.sql.Date.class)) {
				return new java.sql.Date(date.getTime());
			} else if (resultClass.equals(java.sql.Timestamp.class)) {
				return new java.sql.Timestamp(date.getTime());
			} else {
				throw new IllegalArgumentException(
						"result class must be java.sql.Date or java.sql.Timestamp");
			}
		}
	}

	public static Date addMonth(int amount) {
		return add(currentTime(), Calendar.MONTH, amount);
	}

	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	public static int getDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	public static String current(String pattern) {
		return formatDate(new Date(), pattern);
	}

	/**
	 * 返回yyyyMMddHHmmssSSS类型的日期字符串
	 * 
	 * @time : 2011-12-21 上午11:48:18<br>
	 */
	public static String currentTimstamp() {
		return formatDate(new Date(), PATTERN_TIMESTAMP);
	}

	public static String currentDate() {
		return formatDate(new Date(), PATTERN_SHORT);
	}

	public static String currentDateTime() {
		return formatDate(new Date(), PATTERN_DEFAULT);
	}

	public static Date nextMonth(Date currentDate, int value) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.MONTH, value);// 在月份上加1
		return cal.getTime();
	}

	public static Date nextDay(Date currentDate, int value) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.DATE, value);//
		return cal.getTime();
	}

	public static String invokeDate(int value) {
		Date currentDate = getCurrnetDate();
		Date dt = nextDay(currentDate, value);
		return getDate(dt);
	}

	public static String invokeDateTime(int value) {
		Date currentDate = getCurrnetDate();
		Date dt = nextDay(currentDate, value);
		return getDateTime(dt);
	}

	public static Date getCurrnetDate() {
		return new Date();
	}

	public static String getDateTime(Date dt) {
		return formatDate(dt, PATTERN_DEFAULT);
	}

	public static String getDate(Date dt) {
		return formatDate(dt, PATTERN_SHORT);
	}

	public static String getYearMonth(Date dt) {
		return formatDate(dt, PATTERN_MONTH);
	}

	public static String invokeYearMonth(int value) {
		Date currentDate = getCurrnetDate();
		Date dt = nextMonth(currentDate, value);
		return getYearMonth(dt);
	}

	public static String invokeYearMonth(Date currentDate, int value) {
		Date dt = nextMonth(currentDate, value);
		return getYearMonth(dt);
	}

	public static String currentMonth() {
		return formatDate(new Date(), PATTERN_MONTH);
	}

	public static String getCurrentTime(String def_value, int pos) {
		if (StringUtils.isEmpty(def_value)) {
			return "";
		}

		if (def_value.equalsIgnoreCase("today")) {
			return DateUtil.currentDate();
		}

		if (def_value.indexOf("-") > 0) {
			try {
				String[] str = def_value.split("-");
				if (str[0].equalsIgnoreCase("today")) {

					int num = -(Integer.parseInt(str[1]));
					return DateUtil.invokeDate(num);
				}

			} catch (Exception ex) {

			}
		}
		//
		if (def_value.indexOf("+") > 0) {
			try {
				String[] str = def_value.split("+");
				if (str[0].equalsIgnoreCase("today")) {
					int num = (Integer.parseInt(str[1]));
					return DateUtil.invokeDate(num);
				}

			} catch (Exception ex) {

			}
		}
		if (def_value.indexOf(",") > 0) {
			String[] arr = def_value.split(",");
			if (pos == 1) {
				return arr[0];
			} else {
				return arr[1];
			}
		}

		return def_value;

	}

	/**
	 * 
	 * @param def_value
	 * @param pos
	 * @return
	 */
	public static String getCurrentDate(String def_value, int pos) {
		if (StringUtils.isEmpty(def_value)) {
			return "";
		}

		if (def_value.equalsIgnoreCase("today")) {
			return DateUtil.currentDate();
		}

		if (def_value.indexOf("-") > 0) {
			try {
				String[] str = def_value.split("-");
				if (str[0].equalsIgnoreCase("today")) {

					int num = -(Integer.parseInt(str[1]));
					return DateUtil.invokeDate(num);
				}

			} catch (Exception ex) {

			}
		}
		//
		if (def_value.indexOf("+") > 0) {
			try {
				String[] str = def_value.split("+");
				if (str[0].equalsIgnoreCase("today")) {
					int num = (Integer.parseInt(str[1]));
					return DateUtil.invokeDate(num);
				}

			} catch (Exception ex) {

			}
		}
		if (def_value.indexOf(",") > 0) {
			String[] arr = def_value.split(",");
			if (pos == 1) {
				return arr[0];
			} else {
				return arr[1];
			}
		}

		return def_value;

	}

	public static String getCurrentMonth(String def_value, int pos) {
		if (StringUtils.isEmpty(def_value)) {
			return "";
		}

		if (def_value.equalsIgnoreCase("today")) {
			return DateUtil.currentMonth();
		}

		if (def_value.indexOf("-") > 0) {
			try {
				String[] str = def_value.split("-");
				if (str[0].equalsIgnoreCase("today")) {

					int num = -(Integer.parseInt(str[1]));
					return DateUtil.invokeYearMonth(num);
				}

			} catch (Exception ex) {

			}
		}
		//
		if (def_value.indexOf("+") > 0) {
			try {
				String[] str = def_value.split("+");
				if (str[0].equalsIgnoreCase("today")) {
					int num = (Integer.parseInt(str[1]));
					return DateUtil.invokeYearMonth(num);
				}

			} catch (Exception ex) {

			}
		}
		if (def_value.indexOf(",") > 0) {
			String[] arr = def_value.split(",");
			if (pos == 1) {
				return arr[0];
			} else {
				return arr[1];
			}
		}

		return def_value;

	}

	public static String getCurrentDateTime(String def_value, int pos) {
		if (StringUtils.isEmpty(def_value)) {
			return "";
		}

		if (def_value.equalsIgnoreCase("today")) {
			return DateUtil.currentDateTime();
		}

		if (def_value.indexOf("-") > 0) {
			try {
				String[] str = def_value.split("-");
				if (str[0].equalsIgnoreCase("today")) {

					int num = -(Integer.parseInt(str[1]));
					return DateUtil.invokeDateTime(num);
				}

			} catch (Exception ex) {

			}
		}
		//
		if (def_value.indexOf("+") > 0) {
			try {
				String[] str = def_value.split("+");
				if (str[0].equalsIgnoreCase("today")) {
					int num = (Integer.parseInt(str[1]));
					return DateUtil.invokeDateTime(num);
				}

			} catch (Exception ex) {

			}
		}
		if (def_value.indexOf(",") > 0) {
			String[] arr = def_value.split(",");
			if (pos == 1) {
				return arr[0];
			} else {
				return arr[1];
			}
		}

		return def_value;

	}

}

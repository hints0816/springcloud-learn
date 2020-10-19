package com.hints.authserver.Util;

import java.text.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

/**
 * Created by 180026 on 2017/11/30.
 */

public class DateTime {

	final private static List<Locale> locales = new ArrayList<Locale>();

	private static Object parsedValue;

	static {
		locales.add(Locale.CANADA);
		locales.add(Locale.CANADA_FRENCH);
		locales.add(Locale.CHINA);
		locales.add(Locale.CHINESE);
		locales.add(Locale.ENGLISH);
		locales.add(Locale.FRANCE);
		locales.add(Locale.FRENCH);
		locales.add(Locale.GERMAN);
		locales.add(Locale.GERMANY);
		locales.add(Locale.ITALIAN);
		locales.add(Locale.ITALY);
		locales.add(Locale.JAPAN);
		locales.add(Locale.JAPANESE);
		locales.add(Locale.KOREA);
		locales.add(Locale.KOREAN);
		locales.add(Locale.PRC);
		locales.add(Locale.ROOT);
		locales.add(Locale.SIMPLIFIED_CHINESE);
		locales.add(Locale.TAIWAN);
		locales.add(Locale.TRADITIONAL_CHINESE);
		locales.add(Locale.UK);
		locales.add(Locale.US);
	}

	private DateTime() {
	}

	public static Date getDateNowTime() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static String getStringNowTime(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		String date = df.format(cal.getTime());
		return date;
	}

	public static Date ValueOfTime(String dateStr) {
		if (isDate(dateStr) && DateTime.parsedValue != null) {
			return new Date(((Date) DateTime.parsedValue).getTime());
		} else {
			return null;
		}
	}

	public static Date ValueOfTime(String dateStr, String format) {
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			date = df.parse(dateStr);
		} catch (ParseException ex) {
			ex.printStackTrace();
			date = null;
		}
		return date;
	}

	public static String getDateToString(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String dat = df.format(date);
		return dat;
	}

	public static Date addYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + year);
		return cal.getTime();
	}

	public static Date addMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + month);
		return cal.getTime();
	}

	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + day);
		return cal.getTime();
	}

	public static Date addHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + hour);
		return cal.getTime();
	}

	public static Date addMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minute);
		return cal.getTime();
	}

	public static Date addSecond(Date date, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + second);
		return cal.getTime();
	}

	public static Date addMilliSecond(Date date, int millisecond) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND)
				+ millisecond);
		return cal.getTime();
	}

	private static boolean isDate(String str, String p) {
		ParsePosition pos = new ParsePosition(0);
		for (Locale l : locales) {
			DateFormatSymbols symbols = new DateFormatSymbols(l);
			SimpleDateFormat sdf = new SimpleDateFormat(p, symbols);
			sdf.setLenient(false);
			Object parsedValue = sdf.parseObject(str, pos);
			if (!(pos.getErrorIndex() > -1) && parsedValue != null) {
				DateTime.parsedValue = parsedValue;
				return true;
			}
		}
		DateTime.parsedValue = null;
		return false;
	}

	public static boolean isDate(String str) {
		if (StringUtils.isNotBlank(str)) {
			if (!isDate(str, "yyyyMMdd") && !isDate(str, "MMddyyyy")
					&& !isDate(str, "ddMMyyyy") && !isDate(str, "yyyy��MM��dd��")
					&& !isDate(str, "MM��dd��yyyy��")
					&& !isDate(str, "dd��MM��yyyy��")) {
				for (Locale l : locales) {
					ParsePosition pos = new ParsePosition(0);
					DateFormat df = DateFormat.getDateInstance(3, l);
					df.setLenient(false);
					Object parsedValue = df.parseObject(str, pos);
					if (!(pos.getErrorIndex() > -1) && parsedValue != null) {
						DateTime.parsedValue = parsedValue;
						return true;
					}
				}
				DateTime.parsedValue = null;
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

}
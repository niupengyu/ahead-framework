/**  
 * 文件名: DateUtil.java
 * 包路径: com.github.niupengyu.core.util
 * 创建描述  
 *        @createPerson：牛鹏宇 
 *        @createDate：2017年11月14日 下午3:54:49
 *        内容描述：
 * 修改描述  
 *        @updatePerson：牛鹏宇
 *        @updateDate：2017年11月14日 下午3:54:49 
 *        修改内容:
 * 版本: V1.0   
 */
package com.github.niupengyu.core.util;

import com.github.niupengyu.core.util.data.NumberUtil;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

public class DateUtil {
	
	private DateUtil() {
	}

	public static final String FORMAT="yyyy-MM-dd HH:mm:ss";

	public static final String STAMP_FORMAT="yyyy-MM-dd HH:mm:ss.SSS";

	public static final String DATE_FORMAT="yyyy-MM-dd";

	public static final String TIME_FORMAT="HH:mm:ss";

	public static final String FIRST_TIME=" 00:00:00";

	public static final String LAST_TIME=" 23:59:59";

	public static final String FIRST_TIME1="00:00:00";

	public static final String LAST_TIME1="23:59:59";

	public static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	public static long toMilli(LocalDateTime ldt){
		return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static long toMilli(){
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	public static long toSecond(){
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
	}

	public static long toSecond(LocalDateTime ldt){
		return ldt.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
	}

	//public static long toMilli(LocalDate ld){
	//	return ld.getLong().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	//}
	
	public static String dateFormat(LocalDateTime dateTime){
		return dateTime.format(DateTimeFormatter.ofPattern(FORMAT));
	}
	
	public static String dateFormat(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT));
	}

	public static String timeStampFormat(LocalDateTime dateTime){
		return dateTime.format(DateTimeFormatter.ofPattern(STAMP_FORMAT));
	}

	public static String timeStampFormat(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(STAMP_FORMAT));
	}

	public static String dateFormatDate(){
		return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
	}

	public static String dateFormatDate(int i){
		return LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
	}

	public static String dateFormat(LocalDateTime dateTime,String format){
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}
	
	public static String dateFormat(String format){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}

	public static String dateFormat(LocalDate date, String format) {
		return date.format(DateTimeFormatter.ofPattern(format));
	}

	public static String dateFormat(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
	}

	public static LocalDateTime getFirstTime(){
		return LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
	}

	public static LocalDateTime getEndTime(){
		return LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
	}

	public static LocalDateTime getFirstTime(LocalDate localDate){
		return LocalDateTime.of(localDate, LocalTime.of(0,0,0));
	}

	public static LocalDate getLocalDate(int day){
		return LocalDate.now().minusDays(day);
	}

	public static LocalDateTime getEndTime(LocalDate localDate){
		return LocalDateTime.of(localDate, LocalTime.of(23,59,59));
	}


	public static LocalDateTime getFirstTime(int day){
		return LocalDateTime.of(LocalDate.now().minusDays(day), LocalTime.of(0,0,0));
	}

	public static LocalDateTime getEndTime(int day){
		return LocalDateTime.of(LocalDate.now().minusDays(day), LocalTime.of(23,59,59));
	}

	public static LocalTime getFirstLocalTime(){
		return  LocalTime.of(0,0,0);
	}

	public static LocalTime getEndLocalTime(){
		return LocalTime.of(23,59,59);
	}

	public static LocalDateTime getLocalDateTime(String date,String format){
		return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(format));
	}

	public static LocalDate getLocalDate(String date,String format){
		return LocalDate.parse(date,DateTimeFormatter.ofPattern(format));
	}

	public static LocalTime getLocalTime(String date,String format){
		return LocalTime.parse(date,DateTimeFormatter.ofPattern(format));
	}


	public static LocalDateTime getLocalDateTime(String date){
		return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(FORMAT));
	}

	public static LocalDateTime getLocalDateTimeStamp(String date){
		return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(STAMP_FORMAT));
	}

	public static LocalDate getLocalDate(String date){
		return LocalDate.parse(date,DateTimeFormatter.ofPattern(DATE_FORMAT));
	}

	public static LocalTime getLocalTime(String date){
		return LocalTime.parse(date,DateTimeFormatter.ofPattern(TIME_FORMAT));
	}


	public static LocalDateTime dateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
	public static Date localDateTimeTodate(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}

	public static String getTimeDes(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		long day = ms / dd;
		long temp=ms-day*dd;
		long hour = temp/ hh;
		long hourTemp=temp - hour * hh;
		long minute = (hourTemp) / mi;
		long second = (hourTemp - minute * mi) / ss;
		long milliSecond = hourTemp - minute * mi - second * ss;
		StringBuilder str = new StringBuilder();
		if(day>0){
			str.append(day).append("天");
		}
		if(hour>0){
			str.append(hour).append("小时");
		}
		if(minute>0){
			str.append(minute).append("分钟");
		}
		if(second>0||milliSecond>0){
			str.append(second).append(".");
			str.append(NumberUtil.format(milliSecond,3)).append("秒");
		}
		return str.toString();
	}

	public static LocalDateTime getLocalDateTime(long times) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(times),ZoneId.systemDefault());
	}

    public static String mysqlFormat(String date) {
		return "DATE_FORMAT("+date+",'%Y-%m-%d') as "+date;
    }

	public static String dateFormat(String format,Date time){
		if(StringUtil.isNull(time)){
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(time);
	}

	public static String dateFormat(Date time){
		if(StringUtil.isNull(time)){
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(time);
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.getTimeDes(163238345));
	}
}


